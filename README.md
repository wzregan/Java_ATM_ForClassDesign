# ATM管理系统

### 一. 数据库的构造


	1. 表名:user
				
		id|username|passwd|realname|balance
		
		记录每个用户账户密码以及余额
		
		创建方法:create table user(id int primary key auto_increment,username varchar(20),passwd varchar(20),balance double);		
		
	2. 表名:user_operation
		
		username|operation|
		
		记录每个用户存钱取钱操作，以及操作时间
	
		创建方法:create table user_operation(username varchar(20),operation text);	