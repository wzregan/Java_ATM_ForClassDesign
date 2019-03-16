package com.wz.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;

import com.wz.handle.KeyHandle;
import com.wz.tool.timeUtil;

//直接让父类继承Runable接口，待会要在run方法里实现accpet
public class NetServer implements Runnable{
	private KeyHandle keyhandle; //SelectionKey处理器
	private Properties pro=new Properties(); //配置文件读取器
	private ServerSocketChannel serversocketchannel; //服务器通道
	private ServerSocket serverSocket; //与服务器通道关联的serverSocket
	private Selector selector; //选择器
	private String host; //主机名
	private int PORT; //端口号
		
	public NetServer() {
		try {
//首先进行初始化操作--->>>
			pro.load(new FileInputStream(new File("netconfig.properties"))); //加载配置文件
			PORT=Integer.parseInt(pro.getProperty("net.port")); //从配置文件中取出端口号
			host=pro.getProperty("net.host"); //从配置文件中取出主机地址
			keyhandle=new KeyHandle();	//创建key的处理器
			serversocketchannel=ServerSocketChannel.open(); //创建一个通道
			serversocketchannel.socket().bind(new InetSocketAddress(host, PORT)); //对通道所关联的socket进行绑定端口
			serversocketchannel.configureBlocking(true); //将连接通道设置为阻塞模式
			selector=Selector.open(); //创建一个选择器
			System.out.println("***********服务器已经开启************");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// 用来监听各个客服端的事件方法
	public void server()
	{
		SelectionKey key=null;
		while(true)
		{
			try {
				synchronized(this) {}
				if(selector.select()>0)
				{
					Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
					while(iterator.hasNext())
					{
						key=iterator.next();
						keyhandle.handle(key); //处理SelectionKey
						iterator.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void Accept()
	{
		new Thread(this).start();
	}

//接收客户端连接部分的方法 ---->
	public void run() {
		while(true)
		{
			try {
				SocketChannel socketchannel=serversocketchannel.accept(); //等待客服端连接，如果没有会阻塞，
				socketchannel.configureBlocking(false); //将得到的连接通道设置为非阻塞模式
				
				//这里的同步块，与server中的同步块相对应
				synchronized(this) {
					selector.wakeup(); //此处需要唤醒server()方法中的select()，否则的话可能会发生死锁
					socketchannel.register(selector,SelectionKey.OP_READ); //对这个通道注册读和写事件
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}



