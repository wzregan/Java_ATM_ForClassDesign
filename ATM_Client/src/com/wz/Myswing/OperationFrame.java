package com.wz.Myswing;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class OperationFrame extends JFrame{
	private JTable table;
	private DefaultTableModel model;
	public static void main(String[] args) {
		new OperationFrame().setVisible(false);
	}
	public OperationFrame()
	{
		setLayout(null);
	}
	
	public void init()
	{
		model=new DefaultTableModel(0, 1) {
			public boolean isCellEditable(int row, int column) {
				return false;
						
			}
			
		};
		
		table=new JTable(model);
	}
	
	public void initTable(String[][] message)
	{
		model.setDataVector(message, );
		
	}
	
	
}
