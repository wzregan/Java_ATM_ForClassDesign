package com.wz.server;

import com.wz.Interface.UserServer;
import com.wz.bean.User;

public class UserServerImpl implements UserServer {

	public boolean register(User user) {
		return false;
	}

	public User querry(String username, String passwd) {
		return null;
	}

	public boolean drawMoney(User user) {
		return false;
	}

	public boolean transfer(User from, User to) {
		return false;
	}

	public void saveMoney(User user, double money) {
		
	}

}
