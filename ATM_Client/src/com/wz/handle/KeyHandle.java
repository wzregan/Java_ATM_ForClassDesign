package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


import com.wz.util.FrameHolder;

public class KeyHandle
{
	private callbackHandle callHandler=new callbackHandle();
	public KeyHandle()
	{
		
	}
	
	public void handle(SelectionKey key) {
		if(key.isReadable())
		{
			int len;
			ByteBuffer bf=ByteBuffer.allocate(1024*10);
			SocketChannel socketChannel=(SocketChannel)key.channel();
			try {
				while((len=socketChannel.read(bf))>0)
				{
					String sign=new String(bf.array(),0,len);
					System.out.println("收到消息了");
					callHandler.handle(sign);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}