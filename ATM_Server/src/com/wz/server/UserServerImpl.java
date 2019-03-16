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
			OperationServerImpl.update(user.getUsername(), OperationServerImpl.REGISTER_OPERATION, 
											 null, null, null);
			return true;
		}
	}

	public User querry(String username, String passwd) {
			//如果返回空，则说明账号或者用户名不存在
			return dao.querryMessage(username, passwd);
	}

	
	public boolean drawMoney(String username,double hmoney) {
		//先获取当前余额
		double current=dao.queryBalanceByname(username);
		//如果当前余额小于用户余额,直接返回false
		if(current<hmoney)
		{
			return false;
		}else {
			//并且更新user表
			dao.updatebalance(username,current-hmoney);
			//并且更新operation记录表
			OperationServerImpl.update(username, OperationServerImpl.DRAWMONEY_OPERATION, 
											 hmoney, null, null);
		}
		return true;
	}

	public boolean transfer(String username, String to,double hmoney) {
		//得到当前余额
		double current=dao.queryBalanceByname(username);
		//进行判断，如果当前余额小于转账余额或者被转账的账号不存在或者被转账的账号就是当前账号，那么就直接返回false
		if(current<hmoney || !dao.userIsExist(to) || username.equals(to))
		{
			return false;
		}else {
			//如果上述条件均满足
			
			//更新user表
			dao.updatebalance(username,current-hmoney);
			
			dao.updatebalance(to, dao.queryBalanceByname(to)+hmoney);
			//更新operation记录表，因为无法获得账号to的User对象
			OperationServerImpl.update(username, OperationServerImpl.TRANSFER_OPERATION, 
											 hmoney, to, null); //此处是更新当前user对象的operation记录
			
			OperationServerImpl.update(to, OperationServerImpl.GETMONEY_OPERATION, 
					 hmoney, null,username); //此处是更新被转账的user的operation记录
			return true;
		}
		
	}

	public void saveMoney(String username, double money) {
		if(dao.userIsExist(username))
		{	
			//更新user表
			dao.updatebalance(username, dao.queryBalanceByname(username)+money);
			//更新operation记录表
			OperationServerImpl.update(username, OperationServerImpl.SAVEMONEY_OPERATION, money, null, null);
		}
		
	
		
	}

	public boolean updatePasswd(String username, String newpasswd) {
		if(dao.userIsExist(username))
		{
			dao.updatePasswd(username, newpasswd);
			return true;
		}
		return false;
	}

	public double querryBalance(String name) {
		
		return dao.queryBalanceByname(name);
	}

	public String getOperation(String username) {
		return dao.getOperation(username);
	}

	
}
