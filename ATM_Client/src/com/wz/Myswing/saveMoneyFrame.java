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
	private JFormattedTextField textField;
	private JPasswordField passwordField;
	private JButton button;
	private User user;
	
	public saveMoneyFrame(User user) {
		FrameHolder.sMoneyFrame=this;
		this.user=user;
		setSize(250, 300);
		init();
	}
	
	public void init()
	{
		textField= new JFormattedTextField(NumberFormat.getInstance());
		
		passwordField=new JPasswordField();
		button=new JButton("存入");
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的大小
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //让窗口放置在屏幕中央
		setLayout(new GridLayout(9, 1));
		addBlankLine(2);
		Box box=Box.createHorizontalBox();
		box.add(Box.createHorizontalStrut(20));
		box.add(new JLabel("金额："));
		box.add(textField);
		box.add(Box.createHorizontalStrut(50));
		add(box);
		addBlankLine(1);
		Box box2=Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(20));
		box2.add(new JLabel("密码："));
		box2.add(passwordField);
		box2.add(Box.createHorizontalStrut(50));
		add(box2);
		addBlankLine(1);
		Box box3=Box.createHorizontalBox();
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(button);
		add(panel);
		button.addActionListener(this);
	}
	
	public  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}

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
