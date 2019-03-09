package com.wz.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;

import com.wz.handle.ErrorHandle;
import com.wz.handle.KeyHandle;
import com.wz.util.MessageUtil;


public class NetClient implements Runnable {
	private SocketChannel socketChannel; //连接通道
	private Selector selector;//选择器
	private int PORT; //端口号
	private String HOST; // 主机名
	private Properties pro; //配置文件加载器
	private KeyHandle keyhandle; //SelectionKey处理器
	public NetClient(){
		try {
			pro=new Properties(); //初始化资源加载器
			pro.load(new FileInputStream(new File("netconfig.properties"))); //加载配置文件
			PORT=Integer.parseInt(pro.getProperty("net.port")); //从配置文件中读取端口号
			HOST=pro.getProperty("net.host"); //从配置文件中读取主机名
			keyhandle=new KeyHandle();	//初始化SelectionKey处理器
			socketChannel=SocketChannel.open(); //获得一个NIO通道
			selector=Selector.open(); //初始化选择器
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//服务方法，需要在一个子线程里进行
	public void server()
	{
		while(true)
		{
			try {
				if(selector.select()>0)
				{
					Iterator<SelectionKey> iterator=selector.selectedKeys().iterator(); //获得存储SelectionKey对象的迭代器
					while(iterator.hasNext()) //对迭代器进行遍历
					{
						SelectionKey key=iterator.next();
						keyhandle.handle(key); //对selectionKey进行处理
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//开启线程连接
	public void connect()
	{
		new Thread(this).start();
	}
	
	
	//因为连接是是阻塞式的，所以要开启子线程进行连接
	public void run() {
			try {
				socketChannel.connect(new InetSocketAddress(HOST, PORT)); //阻塞式进行连接
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);//对通道进行注册
			} catch (IOException e) {
				ErrorHandle.sendMessage(ErrorHandle.ConnectError); //处理服务器连接失败问题
			}//对通道设置为非阻塞
	}
	
	
	//向服务器发送信号
	public void sendMessage(String sign)
	{
		try {
			socketChannel.write(ByteBuffer.wrap(sign.getBytes())); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
