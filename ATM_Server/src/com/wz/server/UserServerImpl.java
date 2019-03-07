package com.wz.server;

import com.wz.Interface.UserDao;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.dao.UserDaoImpl;

public class UserServerImpl implements UserServer {
	UserDao dao=new UserDaoImpl();
	public boolean register(User user) {
		if(dao.userIsExist(user.getUsername()))
		{
			return false;
		}else
		{
			//向数据库中插入数据
			dao.insert(user.getUsername(), user.getPasswd(), user.getRealname(), user.getBalance());
			//更新操作记录表
			OperationServerImpl.update(user, OperationServerImpl.REGISTER_OPERATION, 
											 null, null, null);
			return true;
		}
	}

	public User querry(String username, String passwd) {
			//如果返回空，则说明账号或者用户名不存在
			return dao.querryMessage(username, passwd);
	}

	
	public boolean drawMoney(User user,double hmoney) {
		//先获取当前余额
		double current=user.getBalance();
		//如果当前余额小于用户余额,直接返回false
		if(current<hmoney)
		{
			return false;
		}else {
			//如果满足上述条件，就重新设置以下用户余额
			user.setBalance(current-hmoney);
			//并且更新user表
			dao.updatebalance(user.getUsername(),user.getBalance());
			//并且更新operation记录表
			OperationServerImpl.update(user, OperationServerImpl.DRAWMONEY_OPERATION, 
											 hmoney, null, null);
		}
		return true;
	}

	public boolean transfer(User from, String to,double hmoney) {
		//得到当前余额
		double current=from.getBalance();
		//进行判断，如果当前余额小于转账余额或者被转账的账号不存在或者被转账的账号就是当前账号，那么就直接返回false
		if(current<hmoney || !dao.userIsExist(to) || from.getUsername().equals(to))
		{
			return false;
		}else {
			//如果上述条件均满足
			
			//更新当前user的余额
			from.setBalance(current-hmoney);
			//更新user表
			dao.updatebalance(from.getUsername(),from.getBalance());
			
			dao.updatebalance(to, dao.queryBalanceByname(to)+hmoney);
			//更新operation记录表，这里使用了updatebalance的重载方法，因为无法获得账号to的User对象
			OperationServerImpl.update(from, OperationServerImpl.TRANSFER_OPERATION, 
											 hmoney, to, null); //此处是更新当前user对象的operation记录
			
			OperationServerImpl.update(to, OperationServerImpl.GETMONEY_OPERATION, 
					 hmoney, null, from.getUsername()); //此处是更新被转账的user的operation记录
			return true;
		}
		
	}

	public void saveMoney(User user, double money) {
		if(dao.userIsExist(user.getUsername()))
		{	
			//更新user的余额
			user.setBalance(user.getBalance()+money);
			//更新user表
			dao.updatebalance(user.getUsername(), user.getBalance());
			//更新operation记录表
			OperationServerImpl.update(user, OperationServerImpl.SAVEMONEY_OPERATION, user.getBalance(), null, null);
		}
		
	
		
	}

	public boolean updatePasswd(User user, String newpasswd) {
		if(dao.userIsExist(user.getUsername()))
		{
			dao.updatePasswd(user.getUsername(), newpasswd);
			return true;
		}
		return false;
	}

	
}
