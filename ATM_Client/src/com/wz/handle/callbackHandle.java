package com.wz.handle;

import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.wz.Myswing.MesFrame;
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
				FrameHolder.logFrame.setVisible(false);
				new MesFrame(user).setVisible(true);
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
					new MesFrame(user).setVisible(true);
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
			if(split[1].equals("OK")){
				FrameHolder.sMoneyFrame.setVisible(false);
				FrameHolder.sMoneyFrame.clear();
				FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
				JOptionPane.showMessageDialog(FrameHolder.sMoneyFrame, "存钱成功！", "提示", JOptionPane.OK_OPTION);
			}else {
				JOptionPane.showMessageDialog(FrameHolder.sMoneyFrame, "存钱失败，请检查网络连接", "提示", JOptionPane.OK_OPTION);
			}
			
			break;
		case "DRAWMONEY":
			if(split[1].equals("OK")){
				FrameHolder.drawmoneyframe.setVisible(false);
				FrameHolder.drawmoneyframe.clear();
				FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
				JOptionPane.showMessageDialog(FrameHolder.sMoneyFrame, "存钱成功！", "提示", JOptionPane.OK_OPTION);
			}else {
				JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "取钱失败，请确定银行账户余额是否充足", "提示", JOptionPane.OK_OPTION);
			}
			break;
		case "TRANSFER":
			if(split[1].equals("OK")){
				FrameHolder.transferframe.setVisible(false);
				FrameHolder.transferframe.clear();
				FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
				JOptionPane.showMessageDialog(FrameHolder.sMoneyFrame, "转账成功！", "提示", JOptionPane.OK_OPTION);
			}else {
				JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "转账失败，请确定用户姓名是否正确或您的账户余额是否充足", "提示", JOptionPane.OK_OPTION);
			}
			break;
		case "UPDATEPASSWD": 
			break;
		}
	}

}
