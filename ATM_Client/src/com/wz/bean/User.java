package com.wz.bean;

public class User {
	private  String username;
	private  String passwd;
	private  String realname;
	private  double balance;
	private User(String username, String passwd, String realname, double balance) {
		this.username = username;
		this.passwd = passwd;
		this.realname = realname;
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", passwd=" + passwd + ", realname=" + realname + ", balance=" + balance
				+ "]";
	}

	//工厂方法获得user对象
	public static User CreateUser(String username, String passwd, String realname, double balance)
	{
		return new User(username,passwd,realname,balance);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
