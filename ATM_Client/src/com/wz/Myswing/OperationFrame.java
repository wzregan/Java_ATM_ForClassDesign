package com.wz.Myswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.wz.util.FrameHolder;

public class OperationFrame extends JFrame{
	private JTable table; //表格
	private DefaultTableModel model; //存储表格数据的表格模型
	
	
	public static void main(String[] args) {
		new OperationFrame().setVisible(true);
	}
	public OperationFrame()
	{
		FrameHolder.operationframe=this;
		setResizable(false);
		init();
	}
	public void init()
	{
		/*整个Frame框架的初始化*/
		setLayout(null);
		setSize(500,410);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的大小
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //让窗口放置在屏幕中央
		/*表格的初始化*/
		model=new DefaultTableModel() {	public boolean isCellEditable(int row, int column) {return false;} }; //初始化表格模型
		table=new JTable(model); //初始化表格
		JScrollPane tablePanel=new JScrollPane(table); //将表格加入到scrollpanel中
		tablePanel.setBounds(10, 10, 465, 340); //设置位置以及大小
		getContentPane().add(tablePanel);  //将表格添加到容器中
	}
	public void initTable(String[][] message)
	{
		model.setDataVector(message, new String[] {"历史记录"});;
		table.setModel(model);
	}
}
