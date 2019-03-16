package com.wz.Enter;

import javax.swing.*;

import com.wz.Myswing.LogFrame;
import com.wz.util.ClientUtil;

public class clientEntrance {

	public static void main(String[] args) {
		ClientUtil.client.connect();
		LogFrame frM=new LogFrame();
		new Thread(new Runnable() {
			public void run() {
				ClientUtil.client.server();
			}
		}).start();
	}

}
