package com.wz.dao;

import com.wz.Interface.UserDao;
import com.wz.bean.User;

public class UserDaoImpl implements UserDao {

	public void insert(String username, String passwd, String realname, double balance) {

	}
	public void updatePasswd(String username, String newpasswd) {

	}

	public void updatebalance(String username, double balance) {

	}

	public User querryMessage(String username, String passwd) {
		return null;
	}

	public boolean userIsExist(String passwd) {
		return false;
	}

}
