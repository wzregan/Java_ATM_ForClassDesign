package com.wz.Enter;
import java.sql.SQLException;
import com.wz.network.NetServer;

public class serviceEntrance {
	public static void main(String[] args) throws SQLException {
		NetServer netServer=new NetServer();
		netServer.Accept();
		netServer.server();

	}
}
