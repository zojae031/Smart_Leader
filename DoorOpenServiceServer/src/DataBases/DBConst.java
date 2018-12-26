package DataBases;

public class DBConst {
	public static final int LOGIN_OK = 101;
	public static final int LOGIN_ALREADY_CONNECT = 102;
	public static final int LOGIN_FAIL = 103;
	public static final int LOGIN_NO_DATA = 104;
	public static final int LOGOUT_OK = 201;
	public static final int LOGOUT_FAIL = 202;
	public static final int STATE_LOGIN = 301;
	public static final int STATE_NOT_LOGIN =302;
	public static final int DUPLICATE_ID = 4;
	public static final int SUCCESS = 5;
	public static final int LOGIN = 100;
	public static final int LOGOUT =200;
	public static final int STATE_CHECK =300;
	protected static final String SIGNUPSQL = "insert into member (id,password,name) values (?,?,?)";
	protected static final String SIGNINSQL = "update member set flag = 1 where id =? and password = ? and flag = 0";
	protected static final String LOGOUTSQL = "update member set flag = 0 where id = ?";
	protected static final String COMPANYSQL = "Select * from company where company.company in (Select company from connect_company where id = ?)";
	protected static final String CONNECTSQL = "insert into connect_company values(?,?)";
	protected static final String DUPLICATESQL = "Select id from member where id = ?";
	protected static final String ERRORCHECKSQL = "select id,password,flag from member where id = ?";
	protected static final String COMPANYRETURN = "Select company.company from company";
	protected static final String ADMININSERT = "insert into company values (?,?,?,?)";
	protected static final String ADMINUPDATE = "update company set latitude = ?, longitude=? , scope = ? where company.company = ?";
	protected static final String STATECHECK = "Select flag from member where id = ?";
	protected static final String CHECKADMIN = "select company.company from company where company.company = ?";
}
