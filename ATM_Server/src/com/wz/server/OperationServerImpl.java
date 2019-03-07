package com.wz.server;

import com.wz.Interface.OperationDao;
import com.wz.dao.OperationDaoImpl;

public class OperationServerImpl {
	public static int REGISTER_OPERATION=0;
	public static int DRAWMONEY_OPERATION=1;
	public static int TRANSFER_OPERATION=2;
	public static int SAVEMONEY_OPERATION=3;
	private static OperationDao dao=new OperationDaoImpl();
	public static void update(int Operation)
	{
		
	}
	
}
