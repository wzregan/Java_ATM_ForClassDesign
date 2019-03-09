package com.wz.handle;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.wz.util.FrameHolder;

public class ErrorHandle {
	public static final String ConnectError="CONNECTERROR";
	public static void sendMessage(String errorType) {
		switch (errorType) {
		case ConnectError:
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "连接服务器失败");
			System.exit(0);
			break;
		default:
			break;
		}
	}

}
