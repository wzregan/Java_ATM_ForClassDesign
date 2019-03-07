package com.wz.Interface;

import com.wz.bean.User;

public interface UserDao {
	public void insert(String username,String passwd,String realname,double balance); //注册账号时使用
	public void updatePasswd(String username,String newpasswd);  //修改密码时使用						  
	public void updatebalance(String username,double balance);	 //更新余额时使用
	public User querryMessage(String username,String passwd);    //查询时候使用
	public boolean userIsExist(String username);//注册时使用，判断该账号是否已经存在
	public double queryBalanceByname(String name); //通过账号查询余额
	public String ToRealName(String username); //通过账号查询真实名字
	
}
