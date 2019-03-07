import java.sql.Connection;
import java.sql.SQLException;

import com.wz.Exception.OutOfRangeException;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.dao.UserDaoImpl;
import com.wz.network.NetServer;
import com.wz.server.UserServerImpl;
import com.wz.tool.dbPool;
import com.wz.tool.dbUtil;

public class text {

	public static void main(String[] args) throws SQLException {
		System.out.println("准备连接");
		NetServer netServer=new NetServer();
		netServer.Accept();
		netServer.server();

	}
}
