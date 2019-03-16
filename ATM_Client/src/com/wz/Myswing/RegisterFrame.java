package com.wz.Myswing;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.MessageUtil;

public class RegisterFrame extends JFrame implements ActionListener {
	private JFrame last;
	private userRegister userp=new userRegister();
	private passwdRegister passwd=new passwdRegister();
	private passwd2Register passwd2=new passwd2Register();
	private realnamePanel realnamep=new realnamePanel();
	private registerFunc funcp=new registerFunc();
	
	public RegisterFrame() {
		setSize(300, 300);  //设置窗口大小
		setResizable(false);
		setTitle("登陆"); //设置标题
		setLayout(new GridLayout(7, 0));
		init();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				last.setVisible(true);
			}
		});
		
		
		this.last=FrameHolder.logFrame;
	}
	
	private void init()
	{
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的大小
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //让窗口放置在屏幕中央
		addBlankLine(1);
		FrameHolder.RegisFrame=this;
		getContentPane().add(userp);
		getContentPane().add(passwd);
		getContentPane().add(passwd2);
		getContentPane().add(realnamep);
		getContentPane().add(funcp);
		funcp.register.addActionListener(this);
	}
	
	
	
	public  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}

	public void actionPerformed(ActionEvent e) {
		Button button=(Button)e.getSource(); //得到事件源按钮
		
		if(button.getName().equals(funcp.register.getName()))
		{
			register();
		}
	}
	
	public void register()
	{
		String name=userp.nameField.getText();
		String password=passwd.passwdField.getText();
		String realname=realnamep.realnameField.getText();
		String password2=passwd2.passwd2Field.getText();
		
		//先校验输入框内容是否合乎规范
		if(name.equals("") || passwd.equals("") || realname.equals("") || password.equals("") || passwd2.equals(""))
		{
			JOptionPane.showMessageDialog(this, "请确保信息填写正确", "提示", JOptionPane.OK_OPTION);
			return;
		}
		if(!password.equals(password2))
		{
			JOptionPane.showMessageDialog(this, "两次密码输入不相同，请重新输入", "提示", JOptionPane.OK_OPTION);
			passwd2.passwd2Field.setText("");
			return;
		}
		if(name.length()<6)
		{
			JOptionPane.showMessageDialog(this, "用户名不合法，用户名长度至少六位", "提示", JOptionPane.OK_OPTION);
			userp.nameField.setText("");
			return;
		}else if(name.indexOf(':')!=-1 && name.indexOf('#')!=-1) {
			JOptionPane.showMessageDialog(this, "用户名不合法，不得包含‘:’，‘#’等特殊字符", "提示", JOptionPane.OK_OPTION);
			userp.nameField.setText("");
			return;
		}
		if(password.length()<6)
		{
			JOptionPane.showMessageDialog(this, "密码不合乎规范，密码长度至少六位", "提示", JOptionPane.OK_OPTION);
			passwd.passwdField.setText("");
			return;
		}else if(name.indexOf(':')!=-1 && name.indexOf('#')!=-1) {
			JOptionPane.showMessageDialog(this, "密码不合法，不得包含‘:’，‘#’等特殊字符", "提示", JOptionPane.OK_OPTION);
			passwd.passwdField.setText("");
			return;
		}
		
		
		//如果满足条件则进行注册
		ClientUtil.client.sendMessage(MessageUtil.registerSign(name, password, realname, 0));
	}
	

	
}

class userRegister extends Panel
{
	public JTextField nameField;
	public userRegister()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		nameField=new JTextField(12);
		add(new Label("账       号："));
		add(nameField);
	}
}

class passwdRegister extends Panel
{
	public JPasswordField passwdField;
	public passwdRegister()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		passwdField=new JPasswordField(12);
		add(new Label("密       码："));
		add(passwdField);
	}
}

class passwd2Register extends Panel
{
	public JPasswordField passwd2Field;
	public passwd2Register()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		passwd2Field=new JPasswordField(12);
		add(new Label("确定密码："));
		add(passwd2Field);
	}
}

class realnamePanel extends Panel
{
	public JTextField realnameField;
	public realnamePanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		realnameField=new JTextField(12);
		add(new Label("姓       名："));
		add(realnameField);
	}
}

class registerFunc extends Panel
{
	public Button register;
	public registerFunc()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		register=new Button("确定注册");
		add(register);
	}
}


