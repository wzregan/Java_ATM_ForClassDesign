package com.wz.tool;

import com.wz.Interface.UserDao;
import com.wz.dao.UserDaoImpl;

public class MyHolder {
	private static KeyList list=new KeyList();
	private static UserDao dao=new UserDaoImpl();
	public static KeyList getKeyList()
	{
		return list;
	}
	public static UserDao getUserDao()
	{
		return dao;
	}

}
