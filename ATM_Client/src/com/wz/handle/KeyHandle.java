package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class KeyHandle
{
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
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}