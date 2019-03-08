package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.wz.network.NetServer;

/*SelectionKey处理器*/
public class KeyHandle
{
	private EventHandle eventHandle; //信号事件处理器
	public KeyHandle()
	{
		eventHandle=new EventHandle();
	}
	
	public void handle(SelectionKey key) {
		if(key.isReadable())
		{
			SocketChannel socketchannel=(SocketChannel)key.channel();
			ByteBuffer bf=ByteBuffer.allocate(1024*10);
			try 
			{
					int	len;
					while((len=socketchannel.read(bf))>0)
					{
						String sign=new String(bf.array(),0,len); //读取从客户端发送来的信号
						bf.clear();
						eventHandle.sendSign(sign,key); //将信号进行处理
					}
			} 
			catch (IOException e) 
			{
				if(key!=null)
				{
					try 
					{
						key.cancel();
						key.channel().close();
					} 
					
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					
				}
			}
		}
	}
}