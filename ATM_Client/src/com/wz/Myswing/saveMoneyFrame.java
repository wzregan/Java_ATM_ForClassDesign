package com.wz.Myswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.text.MaskFormatter;

import com.wz.bean.User;
import com.wz.network.NetClient;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.Md5Util;
import com.wz.util.MessageUtil;

public class saveMoneyFrame extends JFrame implements ActionListener{
	private JFormattedTextField textField; //输入金额的格式框
	private JPasswordField passwordField;	//输入密码的密码框
	private JButton button; //确定按钮
	private User user; //当前用户
	
	public saveMoneyFrame(User user) {
		FrameHolder.sMoneyFrame=this; //添加到FrameHolder中
		this.user=user;  //初始化user

		init();
	}
	
	private void init()
	{
		textField= new JFormattedTextField(NumberFormat.getInstance()); //用Nub初始化格式化框
		passwordField=new JPasswordField(); //初始化密码框
		button=new JButton("存入"); //初始化按钮
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的大小
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //让窗口放置在屏幕中央
		setLayout(new GridLayout(9, 1)); //设置为网格布局
		setSize(250, 300);
		setTitle("取钱");
		
		addBlankLine(2); //增加两个空行
		
		Box box=Box.createHorizontalBox(); //创建横向的箱式布局
		box.add(Box.createHorizontalStrut(20)); //创建横向的填充物
		box.add(new JLabel("金额：")); //添加标签
		box.add(textField); //加入输入框
		box.add(Box.createHorizontalStrut(50)); //创建横向的填充物
		add(box); //添加这个容器
		addBlankLine(1); //中间添加一行
		Box box2=Box.createHorizontalBox(); //创建横向的布局
		box2.add(Box.createHorizontalStrut(20)); //创建横向的填充物
		box2.add(new JLabel("密码：")); //添加标签
		box2.add(passwordField); //添加密码框
		box2.add(Box.createHorizontalStrut(50)); //添加填充物
		add(box2);//将这个容器添加进网格布局中
		addBlankLine(1); //添加空行

		//在把按钮放到普通的面板中
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(button);
		add(panel);
		button.addActionListener(this);
	}
	
	//添加空行的方法
	private  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}

	//监听器
	public void actionPerformed(ActionEvent e) {
		JButton button=(JButton)e.getSource();

		
		if(button.getText().equals("存入"))
		{	
			if(!Md5Util.ToMd5code(passwordField.getText()).equals(user.getPasswd())){
				JOptionPane.showMessageDialog(this, "您输入的密码错误，请重新输入", "提示", JOptionPane.OK_OPTION);
				passwordField.setText("");
				return;
			}
			System.out.println(textField.getValue());
			ClientUtil.client.sendMessage(MessageUtil.saveMoneySign(user.getUsername(),Double.parseDouble(textField.getValue()+"")));
			
		}
	}
	public void clear()
	{
		textField.setText("");
		passwordField.setText("");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	
