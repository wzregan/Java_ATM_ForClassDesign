package com.wz.handle;

import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.wz.Myswing.MesFrame;
import com.wz.Myswing.OperationFrame;
import com.wz.bean.User;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.Md5Util;
import com.wz.util.MessageUtil;

public class callbackHandle {
	public callbackHandle() {
		
	}

	public void handle(String sign) {
		String[] split=sign.split(MessageUtil.REGEX);
		switch(split[0])
		{
		case "LOGIN": //登陆事件
			Login(split);
			break;
		case "REGISTER"://注册事件
			regetster(split);
			break;
		case "SAVEMONEY": //存钱事件
			savemoney(split);
			break;
		case "DRAWMONEY"://取钱事件
			draomoney(split);
			break;
		case "TRANSFER"://转账事件
			transfer(split);
			break;
		case "UPDATEPASSWD": //修改密码事件，暂未增加此功能
			break;
		case "OPERATION": //查询历史记录事件
			operation(split);
			break;
		case "GETMONEY"://收到转账事件
			getMoney(split);
			break;
		}
	}

	private void getMoney(String[] split) {
		JOptionPane.showMessageDialog(FrameHolder.mesFrame, "您收到一笔转账", "提示", JOptionPane.OK_OPTION);
		double balance=Double.parseDouble(split[1]);
		FrameHolder.mesFrame.setuserBalance(balance);
	}

	//查询历史记录事件处理
	private void operation(String[] split) {
		if(split[1].equals("OK")){
			String[] message=split[2].split(MessageUtil.OERATION_REGEX);
			String[][] mess=new String[message.length][1];
			for(int i=0;i<mess.length;i++)
			{
				mess[i][0]=message[i];
				
			}
			if (FrameHolder.operationframe==null) {
				OperationFrame operationFrame=new OperationFrame();
				operationFrame.initTable(mess);
				operationFrame.setVisible(true);
			}else {
				FrameHolder.operationframe.initTable(mess);
				FrameHolder.operationframe.setVisible(true);
			}
			
		}else {
			JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "转账失败，请确定用户姓名是否正确或您的账户余额是否充足", "提示", JOptionPane.OK_OPTION);
		}
	}
	//发起转账请求事件处理
	private void transfer(String[] split) {
		if(split[1].equals("OK")){
			FrameHolder.transferframe.setVisible(false);
			FrameHolder.transferframe.clear();
			FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
			JOptionPane.showMessageDialog(FrameHolder.mesFrame, "转账成功！", "提示", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "转账失败，请确定用户姓名是否正确或您的账户余额是否充足", "提示", JOptionPane.OK_OPTION);
			
		}
	}
	//发起取钱请求事件处理
	private void draomoney(String[] split) {
		if(split[1].equals("OK")){
			FrameHolder.drawmoneyframe.setVisible(false);
			FrameHolder.drawmoneyframe.clear();
			FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
			JOptionPane.showMessageDialog(FrameHolder.mesFrame, "取钱成功！", "提示", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "取钱失败，请确定银行账户余额是否充足", "提示", JOptionPane.OK_OPTION);
		}
	}
	//发起存钱请求事件处理
	private void savemoney(String[] split) {
		if(split[1].equals("OK")){
			FrameHolder.sMoneyFrame.setVisible(false);
			FrameHolder.sMoneyFrame.clear();
			FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
			JOptionPane.showMessageDialog(FrameHolder.mesFrame, "存钱成功！", "提示", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(FrameHolder.sMoneyFrame, "存钱失败，请检查网络连接", "提示", JOptionPane.OK_OPTION);
		}
	}
	//发起注册请求事件处理
	private void regetster(String[] split) {
		if(split[1].equals("YES")) {
			int i=JOptionPane.showConfirmDialog(FrameHolder.RegisFrame, "恭喜您，账号注册成功，是否直接登陆?", "提示", JOptionPane.CANCEL_OPTION);
			//YES=0 NO=2
			if(i==0)
			{
				FrameHolder.RegisFrame.setVisible(false);
				ClientUtil.client.sendMessage(MessageUtil.loginSgin(split[2], split[3]));
			}else if(i==2)
			{
				FrameHolder.RegisFrame.setVisible(false);
				FrameHolder.logFrame.setVisible(true);
				FrameHolder.logFrame.setUserFiled(split[2]);
			}
		}else if(split[1].equals("NO")) {
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "用户名已经存在，请更换一个用户名再尝试注册", "提示", JOptionPane.OK_OPTION);
		}
	}

	//发起登陆请求事件处理
	private void Login(String[] split) {
		if(split[1].equals("NO"))
		{
			if(split.length>=3&&split[2].equals("ISONLINE"))
			{
				JOptionPane.showMessageDialog(FrameHolder.logFrame, "账号已经在别处登陆！", "提示", JOptionPane.OK_OPTION);
				return;
			}
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "账号或密码错误!", "提示", JOptionPane.OK_OPTION);
			FrameHolder.logFrame.setPassFiled("");
		}
		else if(split[1].equals("YES")){
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "登陆成功", "提示", JOptionPane.OK_OPTION);
			User user=User.CreateUser(split[2], split[3], split[4], Double.parseDouble(split[5]));
			FrameHolder.logFrame.setVisible(false);
			new MesFrame(user).setVisible(true);
		}
	}

}
