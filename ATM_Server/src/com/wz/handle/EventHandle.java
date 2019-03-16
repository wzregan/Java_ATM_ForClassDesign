package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Timer;

import com.sun.corba.se.impl.legacy.connection.SocketFactoryConnectionImpl;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.network.NetServer;
import com.wz.server.OperationServerImpl;
import com.wz.server.UserServerImpl;
import com.wz.tool.MyHolder;
import com.wz.tool.timeUtil;

/*事件处理器类*/
public class EventHandle {
	private UserServer userServer;
	private OperationServerImpl operationserver=new OperationServerImpl();
	private final String EVENT_REGISTER="REGISTER";
	private final String EVENT_LOGIN="LOGIN";
	private final String EVENT_SAVEMONEY="SAVEMONEY";
	private final String EVENT_TRANSFER="TRANSFER";
	private final String EVENT_UPDATEPASSWD="UPDATEPASSWD";
	private final String EVENT_DRAWMONEY="DRAWMONEY";
	private final String EVENT_OPERATION="OPERATION";
	private final String REGEX=":#:#";
	public EventHandle()
	{
		userServer=new UserServerImpl();
	}
	public void sendSign(String sign,SelectionKey key)
	{
		String callback=null; //初始化反馈值
		String split[]=sign.split(REGEX); //将信号进行处理
		System.out.println(timeUtil.getTime()+"     接收请求:"+sign);
		switch(split[0])
		{
			case EVENT_REGISTER://注册事件
				callback=Register(split[1],split[2],split[3],Double.parseDouble(split[4]));
				CallBack(key, callback);
				break;
			case EVENT_LOGIN://登陆事件
				key.attach(split[1]);
				callback=login(split[1],split[2],key);
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
			case EVENT_OPERATION://取钱事件
				callback=querryOperation(split[1]);
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
			return "DRAWMONEY"+REGEX+"OK"+REGEX+userServer.querryBalance(username);
		}else {
			return "DRAWMONEY"+REGEX+"NO";
		}
	}
	//注册事件
	public String Register(String username, String passwd, String realname,double balance)
	{
		User user=User.CreateUser(username, passwd, realname, balance);
		boolean result=userServer.register(user);
		if(result)
		{
			return "REGISTER"+REGEX+"YES"+REGEX+user.getUsername()+REGEX+user.getPasswd()+REGEX+user.getRealname()+REGEX+user.getBalance();
		}else {
			return "REGISTER"+REGEX+"NO";
		}
	}
	//登陆事件
	public String login(String username,String passwd,SelectionKey key)
	{

		User user=userServer.querry(username, passwd);
		if(user!=null)
		{
			if (MyHolder.getKeyList().IsOnline(username))
			{
				return "LOGIN"+REGEX+"NO"+REGEX+"ISONLINE";
			}else
			{
				MyHolder.getKeyList().add(key);
			}
			
			System.out.println(timeUtil.getTime()+"     有用户登入系统,当前连接数:"+MyHolder.getKeyList().size()); //测试代码
			return "LOGIN"+REGEX+"YES"+REGEX+user.getUsername()+REGEX+user.getPasswd()+REGEX+user.getRealname()
			+REGEX+user.getBalance();
		}
		return "LOGIN"+REGEX+"NO";
	}
	
	//存钱事件
	public String save(String username,double money)
	{
		userServer.saveMoney(username, money);
		return "SAVEMONEY"+REGEX+"OK"+REGEX+userServer.querryBalance(username);
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
			if (MyHolder.getKeyList().IsOnline(to)) {
				NotifyUser(to);
			}
			return "TRANSFER"+REGEX+"OK"+REGEX+userServer.querryBalance(from);
		}else {
			return "TRANSFER"+REGEX+"NO";
		}
	}
	//查询操作记录事件
	public String querryOperation(String username)
	{
		String operaiton=userServer.getOperation(username);
		return "OPERATION"+REGEX+"OK"+REGEX+operaiton;
	}
	//如果用户在线，通知用户收到转账
	public void NotifyUser(String username)
	{
		SocketChannel socketChannel=(SocketChannel) MyHolder.getKeyList().getUserKey(username).channel();
		try {
			String newBalance=userServer.querryBalance(username)+"";
			socketChannel.write(ByteBuffer.wrap(("GETMONEY"+REGEX+newBalance).getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
