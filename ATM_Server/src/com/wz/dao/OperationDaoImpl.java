package com.wz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wz.tool.dbPool;

public class OperationDaoImpl implements com.wz.Interface.OperationDao 
{
	//此工具类的操作，总是与UseDao工具类里的操作相关联,处理好UseDao里的线程安全，这里也就没必要再处理了
	
	private dbPool db=new dbPool(); //获取数据库连接池
	//这个方法在注册的时候调用，会为账号初始化一个operation
	public void InitOperation(String username)
	{
		Connection con=db.getConenction();
		PreparedStatement ps=null;
		try {
			ps=con.prepareStatement("insert into user_operation values(?,null)");
			ps.setString(1, username);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//将操作信息写入operation表中
	public void OperationUpdate(String username, String OpeMessage) {
		Connection con=db.getConenction();
		PreparedStatement ps=null;
		try {
			ps=con.prepareStatement("update user_operation set operation=concat_ws(';#;#',operation,?) where username=?");
			ps.setString(2, username);
			ps.setString(1, OpeMessage);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
		
}		
