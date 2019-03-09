package com.wz.Myswing;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wz.network.NetClient;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.MessageUtil;


//登陆界面
public class LogFrame extends JFrame implements ActionListener{
	private userPanel userpanle;
	private passwdPanel passwaPanel;
	private funcPanel funcpanel;
	public LogFrame()
	{
		userpanle=new userPanel();
		passwaPanel=new passwdPanel();
		funcpanel=new funcPanel();
		setTitle("登陆"); //设置标题
		setSize(300, 300);  //设置窗口大小
		setLayout(new GridLayout(7, 0));
		addBlankLine(2);
		init();
		setVisible(true);//设置为可见
	}
	
	private void init()//先进行一些最基础的初始化
	{
		FrameHolder.logFrame=this;
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的大小
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //让窗口放置在屏幕中央
		setDefaultCloseOperation(EXIT_ON_CLOSE); //设置默认关闭模式
		getContentPane().add(userpanle);
		getContentPane().add( passwaPanel);
		getContentPane().add(funcpanel);
		funcpanel.logbutton.addActionListener(this);
		funcpanel.registerbutton.addActionListener(this);
		passwaPanel.passwdField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode()==10) {
					login();
				}
			}
		});
	}
	
	public void setPassFiled(String str)
	{
		passwaPanel.passwdField.setText(str);
	}
	
	//为网格布局填充空行
	public  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}
	
	//向服务器发送登陆请求
	public void login()
	{
		String username=userpanle.nameField.getText();
		String passwd=passwaPanel.passwdField.getText();
		if(username.equals("") || passwd.equals(""))
		{
			JOptionPane.showMessageDialog(this,"账号或者密码不能为空", "提示", JOptionPane.OK_OPTION);
			return;
		}
		ClientUtil.client.sendMessage(MessageUtil.loginSgin(username, passwd));
	}
	
	//打开注册界面
	public void register()
	{
		setVisible(false);
		new RegisterFrame().setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		Button button=(Button) e.getSource();
		String name=button.getName();
		if(name.equals(funcpanel.logbutton.getName()))
		{
			login();
		}
		
		if(name.equals(funcpanel.registerbutton.getName()))
		{
			register();
		}
	}
	
}


class userPanel extends Panel
{
	public JTextField nameField;
	public userPanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		nameField=new JTextField(12);
		add(new Label("账号："));
		add(nameField);
		
	}
}

class passwdPanel extends Panel
{
	public JPasswordField passwdField;
	public passwdPanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		passwdField=new JPasswordField(12);
		add(new Label("密码："));
		add(passwdField);
	}
}

class funcPanel extends Panel
{
	public Button logbutton;
	public Button registerbutton;
	public funcPanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		logbutton=new Button("登陆");
		registerbutton=new Button("注册");
		add(logbutton);
		add(registerbutton);
	}
}
