# ATM管理系统
----------
## Server/服务器端
### 一. 数据库的构造


	1. 表名:user
				
		id|username|passwd|realname|balance
		
		记录每个用户账户密码以及余额
		
		创建方法:create table user(id int primary key auto_increment,username varchar(20),passwd varchar(50),realname varchar(20),balance double);		
		
	2. 表名:user_operation
		
		username|operation|
		
		记录每个用户存钱取钱操作，以及操作时间
	
		创建方法:create table user_operation(username varchar(20),operation text);

----
>为某个字段插入添加后续内容的Mysql语法    
>主要需要使用到两个函数
>
>1. concat(string1,string2,…) --->string1,string2代表字符串,concat函数在连接字符串的时候，只要其中一个是NULL,那么将返回NULL
>
>2. concat_ws(分隔符,str1,str2,.....)--->string1,string2代表字符串,concat_ws 代表 concat with separator,第一个参数是其它参数的分隔符。分隔符的位置放在要连接的两个字符串之间。分隔符可以是一个字符串，也可以是其它参数。如果分隔符为 NULL，则结果为 NULL。函数会忽略任何分隔符参数后的 NULL 值。
	
	
>	`update 表名 set 字段名=concat(字段名,string)`
>	
>	`update 表名 set 字段名=concat_ws(分割民,字段名,string)`

### 二、数据操作层接口说明

	public interface UserDao {
		public void insert(String username,String passwd,String realname,double balance); //注册账号时使用
		public void updatePasswd(String username,String newpasswd);  //修改密码时使用						  
		public void updatebalance(String username,double balance);	 //更新余额时使用
		public User querryMessage(String username,String passwd);    //查询时候使用
		public boolean userIsExist(String username);//注册时使用，判断该账号是否已经存在
		public double queryBalanceByname(String name); //通过账号查询余额
		public String ToRealName(String username); //通过账号查询真实名字
	}

---------------

	public interface OperationDao {
		public void OperationUpdate(String username,String OpeMessage); //主要用于更新操作记录表
		public void InitOperation(String username); //用于用户刚刚注册时候初始化数据库表
	}

### 三、业务逻辑层接口说明

	public interface UserServer {
		public boolean register(User user); //注册账号，如果注册成功返回true,如果注册失败,返回false
		public User querry(String username,String passwd); //查询
		public boolean drawMoney(User user,double money); //取钱，如果余额足够则取钱成功，如果余额不足取钱，则返回false
		public boolean transfer(User from,String to,double money); //转账，如果转账成功则取返回true，如果失败则返回false
		public void saveMoney(User user,double money); //存钱
	}

### 四、工具类说明
	1. dbPool -->数据库连接池，提高效率
	2. dbUtil -->封装了jdbc代码，主要用在dbPool里面
	3. Md5Util -->封装了MD5算法，用于将密码转换为非明文存放在数据库中
	4. timeUtil -->主要用于获取与时间有关的参数

### 五、异常说明
	1. OutOfRangeException -->用于数据库连接池,当需要填充的连接大于连接池最大数量时抛出
	
-----------
#服务器的网络连接部分