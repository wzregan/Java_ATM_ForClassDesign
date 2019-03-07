package com.wz.server;
import com.wz.Interface.OperationDao;
import com.wz.bean.User;
import com.wz.dao.OperationDaoImpl;
import com.wz.tool.timeUtil;

public class OperationServerImpl {
	public final static int REGISTER_OPERATION=0;
	public final static int DRAWMONEY_OPERATION=1;
	public final static int TRANSFER_OPERATION=2;
	public final static int SAVEMONEY_OPERATION=3;
	public final static int GETMONEY_OPERATION=4;
	private final static OperationDao dao=new OperationDaoImpl();
	
	//参数1:操作对象
	//参数2:更新选项 可选0 1 2 3 4
	//参数3:若发生钱的变化，则需要填写该参数
	//参数4:若转账，则需要填写转账给谁
	//参数4:若收到转账，则需要填写该参数
	public static void update(User user,Integer Operation,Double balance,String toname,String from)
	{
		String mes=null;
		switch(Operation)
		{
		case REGISTER_OPERATION:
			mes=timeUtil.getTime()+" : "+"您注册了账号，欢迎您使用本银行系统";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case DRAWMONEY_OPERATION:
			mes=timeUtil.getTime()+" : "+"您取出了"+balance+"元，余额还剩"+user.getBalance()+"元";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case TRANSFER_OPERATION:
			mes=timeUtil.getTime()+" : "+"您向"+toname+"转账了"+balance+"元"+"，余额还剩"+user.getBalance()+"元";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case SAVEMONEY_OPERATION:
			mes=timeUtil.getTime()+" : "+"您存入了"+balance+"元"+"，余额还剩"+user.getBalance()+"元";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case GETMONEY_OPERATION:
			mes=timeUtil.getTime()+" : "+from+"向您转账了"+balance+"元"+"，余额还剩"+user.getBalance()+"元";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		}
		
	}
	
}
