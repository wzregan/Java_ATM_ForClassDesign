package com.wz.handle;

import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.wz.bean.User;
import com.wz.util.FrameHolder;
import com.wz.util.MessageUtil;

public class callbackHandle {
	public callbackHandle() {
		
	}

	public void handle(String sign) {
		String[] split=sign.split(MessageUtil.REGEX);
		switch(split[0])
		{
		case "LOGIN":
			if(split[1].equals("NO")) {
				JOptionPane.showMessageDialog(FrameHolder.logFrame, "账号或者密码错误", "提示", JOptionPane.OK_OPTION);
				FrameHolder.logFrame.setPassFiled("");
			}else if(split[1].equals("YES")){
				JOptionPane.showMessageDialog(FrameHolder.logFrame, "登陆成功", "提示", JOptionPane.OK_OPTION);
				User user=User.CreateUser(split[2], split[3], split[4], Double.parseDouble(split[5]));
			}
			break;
		case "REGISTER":
			break;
		case "SAVEMONEY": 
			break;
		case "TRANSFER":
			break;
		case "DRAWMONEY":
			break;
		case "UPDATEPASSWD": 
			break;
		}
	}

}
