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
		System.out.println(sign);
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
			if(split[1].equals("YES")) {
				int i=JOptionPane.showConfirmDialog(FrameHolder.logFrame, "恭喜您，账号注册成功，是否直接登陆?", "提示", JOptionPane.CANCEL_OPTION);
				//YES=0 NO=2
				if(i==0)
				{
					FrameHolder.RegisFrame.setVisible(false);
					User user=User.CreateUser(split[2], split[3], split[4], Double.parseDouble(split[5]));
				}else if(i==2)
				{
					FrameHolder.RegisFrame.setVisible(false);
					FrameHolder.logFrame.setVisible(true);
					FrameHolder.logFrame.setUserFiled(split[2]);
				}
			}else if(split[1].equals("NO")) {
				JOptionPane.showMessageDialog(FrameHolder.logFrame, "用户名已经存在，请更换一个用户名再尝试注册", "提示", JOptionPane.OK_OPTION);
			}
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
