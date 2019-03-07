package com.wz.Interface;

public interface OperationDao {
	public void OperationUpdate(String username,String OpeMessage); //主要用于更新操作记录表
	public void InitOperation(String username); //用于用户刚刚注册时候初始化数据库表
}
