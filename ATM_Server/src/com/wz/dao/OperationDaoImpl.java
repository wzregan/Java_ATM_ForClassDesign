package com.wz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wz.tool.dbPool;

public class OperationDaoImpl implements com.wz.Interface.OperationDao {
	public static void main(String[] args) {
		new OperationDaoImpl().OperationUpdate("admin", "save money");
	}

	private dbPool db=new dbPool();
	public void OperationUpdate(String username, String OpeMessage) {
		Connection con=db.getConenction();
		PreparedStatement ps=null;
		try {
			ps=con.prepareStatement("update user_operation set operation=concat_ws('|',operation,?) where username=?");
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
