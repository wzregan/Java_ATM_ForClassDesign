package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.network.NetServer;
import com.wz.server.UserServerImpl;

/*事件处理器类*/
public class EventHandle {
	private UserServer userServer;
	private NetServer netServer;
	private final String EVENT_REGISTER="REGISTER";
	private final String EVENT_LOGIN="LOGIN";
	private final String EVENT_SAVEMONEY="SAVEMONEY";
	private final String EVENT_TRANSFER="TRANSFER";
	private final String EVENT_UPDATEPASSWD="UPDATEPASSWD";
	private final String EVENT_DRAWMONEY="DRAWMONEY";
	private final String REGEX=":#:#";
	public EventHandle()
	{
		userServer=new UserServerImpl();
	}
	public void sendSign(String sign,SelectionKey key)
	{
		String callback=null; //初始化反馈值
		String split[]=sign.split(REGEX); //将信号进行处理
		System.out.println(sign);
		switch(split[0])
		{
			case EVENT_REGISTER://注册事件
				callback=Register(split[1],split[2],split[3],Double.parseDouble(split[4]));
				CallBack(key, callback);
				break;
			case EVENT_LOGIN://登陆事件
				callback=login(split[1],split[2]);
				CallBack(key,callback);
				break;
			case EVENT_SAVEMONEY://存钱事件
				callback=save(split[1], Double.parseDouble(split[2]));
				CallBack(key, callback);
				break;
			case EVENT_UPDATEPASSWD://修改密码事件
				callback=updatePasswd(split[1], split[2]);
				CallBack(key,callback);
				break;
			case EVENT_TRANSFER://转账事件
				callback=transfer(split[1], split[2], Double.parseDouble(split[3]));
				CallBack(key,callback);
				break;
			case EVENT_DRAWMONEY://取钱事件
				callback=drawmoney(split[1],Double.parseDouble(split[2]));
				CallBack(key,callback);
				break;
		}
	}
	
	//取钱事件
	private String drawmoney(String username,  double money)
	{
		boolean result=userServer.drawMoney(username, money);
		if(result)
		{
			return "DRAWMONEY"+REGEX+"YES";
		}else {
			return "DRAWMONEY"+REGEX+"NO";
		}
	}
	//注册事件
	public String Register(String username, String passwd, String realname,double balance)
	{
		User user=User.CreateUser(username, passwd, realname, balance);
		if(user!=null)
		{
			userServer.register(user);
			return "REGISTER"+REGEX+"OK";
		}else {
			return "REGISTER"+REGEX+"NO";
		}
	}
	//登陆事件
	public String login(String username,String passwd)
	{
		User user=userServer.querry(username, passwd);
		if(user!=null)
		{
			return "LOGIN"+REGEX+"YES"+REGEX+user.getUsername()+REGEX+user.getPasswd()+REGEX+user.getRealname()
			+REGEX+user.getBalance();
		}
		return "LOGIN"+REGEX+"NO";
	}
	
	//存钱事件
	public String save(String username,double money)
	{
		userServer.saveMoney(username, money);
		return "SAVEMONEY"+REGEX+"OK";
	}
	
	//反馈信号方法
	public void CallBack(SelectionKey Key,String callback)
	{
		SocketChannel socketChannel=(SocketChannel)Key.channel();
		try {
			socketChannel.write(ByteBuffer.wrap(callback.getBytes()));
		} catch (IOException e) {
			if(Key!=null)
			{
				Key.cancel();
			}
		}
	}
	
	//修改密码事件
	public String updatePasswd(String username,String passwd)
	{
		boolean result=userServer.updatePasswd(username, passwd);
		if(result)
		{
			return "UPDATEPASSWD"+REGEX+"OK";
		}else {
			return "UPDATEPASSWD"+REGEX+"NO";
		}
		
		
	}
	
	//转账事件
	public String transfer(String from,String to,double money)
	{
		System.out.println(from +"转账给"+to +"  "+money+"元");
		boolean result=userServer.transfer(from, to, money);
		if(result)
		{
			return "TRANSFER"+REGEX+"OK";
		}else {
			return "TRANSFER"+REGEX+"NO";
		}
	}
	
	
	
}
