package com.wz.Interface;

import com.wz.bean.User;

public interface UserServer {
	public boolean register(User user); //注册账号，如果注册成功返回true,如果注册失败,返回false
	public User querry(String username,String passwd); //查询
	public boolean drawMoney(User user,double money); //取钱，如果余额足够则取钱成功，如果余额不足取钱，则返回false
	public boolean transfer(User from,String to,double money); //转账，如果转账成功则取返回true，如果失败则返回false
	public void saveMoney(User user,double money); //存钱
}