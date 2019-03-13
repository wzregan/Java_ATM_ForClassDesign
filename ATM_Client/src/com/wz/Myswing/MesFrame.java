package com.wz.Myswing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wz.bean.User;
import com.wz.util.FrameHolder;

public class MesFrame extends JFrame{
	public static void main(String[] args) {
		new MesFrame(User.CreateUser("admin", "woainima..", "王正", 8523.3));
	}
	
	private User user; //需要展示的用户
	private MessagePanel mespanel; //展示信息面板
	private buttonPanel fp; //功能按钮面板
	private MouseAdapter mouseadapate; //鼠标适配器
	public MesFrame(User user)
	{
		FrameHolder.mesFrame=this; //将该对象加入hoder
		this.user=user; 
		mespanel=new MessagePanel();
		fp=new buttonPanel();
		setSize(500, 300);
		InitMouseAdapter();
		init();
	}
	
	//初始化方法
	private void init()
	{
		setTitle("尊敬的"+user.getRealname()+",欢迎您使用本系统");
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的大小
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //让窗口放置在屏幕中央
		setDefaultCloseOperation(EXIT_ON_CLOSE); //设置默认关闭模式
		setLayout(new GridLayout(1, 2));
		add(mespanel);
		add(fp);

		//将信息进行展示
		mespanel.username.setText(user.getUsername());
		mespanel.realname.setText(user.getRealname());
		mespanel.balance.setText(user.getBalance()+"");
		
		//添加监听器
		fp.savemoney.addMouseListener(mouseadapate);
		fp.drawmoney.addMouseListener(mouseadapate);
		fp.transfer.addMouseListener(mouseadapate);
		fp.querry.addMouseListener(mouseadapate);
	}
	
	
	
	
	//设置暂时面板的余额
	public void setuserBalance(double money)
	{
		mespanel.balance.setText(money+"");
	}
	
	//初始化鼠标适配器的方法
	private void InitMouseAdapter() 
	{
		 mouseadapate=new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JLabel lable=(JLabel) e.getSource();
				System.out.println(lable.toString());
				switch(lable.getText())
				{
					case "存钱":{
						saveMoney();
						break;
					}
					case "查询":{
						querryHistory();
						break;
					}
					case "取钱":{
						drawMoney();
					}
					case "转账":{
						transfer();
						break;
					}
					
				}
			};
		}; 
	}
	
	//发送存钱请求的方法
	private void saveMoney()
	{
		if(FrameHolder.sMoneyFrame==null){
			new saveMoneyFrame(user).setVisible(true);;
		}else
		{
			FrameHolder.sMoneyFrame.setVisible(true);
		}
	}
	
	//发送查看历史记录请求的方法
	private void querryHistory()
	{
		
	}
	
	//发送取钱请求的方法
	private void drawMoney()
	{
		
	}
	
	//发送转账请求的方法
	private void transfer()
	{
		
	}
	
}

class MessagePanel extends JPanel{
	public JLabel username=new JLabel();
	public JLabel realname=new JLabel();
	public JLabel balance=new JLabel();
		
	public MessagePanel()
	{	
		setLayout(new GridLayout(7,1));
		JPanel p1=new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
		p1.add(new JLabel());
		p1.add(new JLabel("用户名："));
		p1.add(username);
		
		JPanel p2=new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
		p2.add(new JLabel());
		p2.add(new JLabel("姓    名："));
		p2.add(realname);
		
		JPanel p3=new JPanel(new FlowLayout(FlowLayout.LEFT,10, 3));
		p3.add(new JLabel());
		p3.add(new JLabel("余    额："));
		p3.add(balance);
		add(new JLabel());
		add(p1);
		add(p2);
		add(p3);
		setBorder(new EtchedBorder(Color.black, Color.black));
		
	}	
}	

class buttonPanel extends JPanel{
	public JLabel savemoney=new JLabel("存钱");
	public JLabel drawmoney=new JLabel("取钱");
	public JLabel transfer=new JLabel("转账");
	public JLabel querry=new JLabel("查询");
	public buttonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
		add(savemoney);
		add(drawmoney);
		add(transfer);
		add(querry);
		MouseListener listener=new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				JLabel lable=(JLabel) e.getSource();
				lable.setForeground(Color.red);
				lable.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				JLabel lable=(JLabel) e.getSource();
				lable.setForeground(new Color(51, 51, 51));
			}
		};
		savemoney.addMouseListener(listener);
		drawmoney.addMouseListener(listener);
		transfer.addMouseListener(listener);
		querry.addMouseListener(listener);
	}
}
