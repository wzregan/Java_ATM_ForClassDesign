package com.wz.util;

public class MessageUtil {
	//client.sendMessage("REGISTER:#:#text03:#:#woainma..:#:#汪子衣:#:#95636");
	//client.sendMessage("LOGIN:#:#text0008:#:#woainima..");
	//client.sendMessage("SAVAMONEY:#:#text03:#:#85615");
	final public  static  String REGEX=":#:#"; //分隔符
	final public  static  String OERATION_REGEX=";#;#"; //分隔符
	
	public static String loginSgin(String username,String passwd) //得到登陆信号
	{
		return "LOGIN"+REGEX+username+REGEX+passwd;
	}
	
	public static String saveMoneySign(String username,double money)//得到存钱信号
	{
		return "SAVEMONEY"+REGEX+username+REGEX+money;
	}
	public static String registerSign(String username,String passwd,String realname,double balance)//得到注册信号
	{
		return "REGISTER"+REGEX+username+REGEX+passwd+REGEX+realname+REGEX+balance;
	}
	
	public static String transferSign(String from,String to,double money)//得到转账信号
	{
		return "TRANSFER" +REGEX+from+REGEX+to+REGEX+money;
	}
	
	public static String drawmoneySign(String username,double money)//得到取钱信号
	{
		return "DRAWMONEY" +REGEX+username+REGEX+money;
	}
	
	public static String updatepasswdSign(String username,String newpasswd)//得到修改密码信号
	{
		return "UPDATEPASSWD" +REGEX+username+REGEX+newpasswd;
	}
	
	public static String querryOperationSign(String username)//得到修改密码信号
	{
		return "OPERATION" +REGEX+username;
	}
	
}
