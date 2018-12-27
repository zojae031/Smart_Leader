package DataBases;

public class DBConst {
	protected static final int LOGIN_OK = 101;
	protected static final int LOGIN_ALREADY_CONNECT = 102;
	protected static final int LOGIN_FAIL = 103;
	protected static final int LOGIN_NO_DATA = 104;
	protected static final int LOGOUT_OK = 201;
	protected static final int LOGOUT_FAIL = 202;
	protected static final int STATE_LOGIN = 301;
	protected static final int STATE_NOT_LOGIN =302;
	protected static final int SIGNUP_DUPLICATE_ID = 501;
	protected static final int NOT_DUPLICATE_ID = 502;
	
	protected static final int SIGNUP_SUCCESS = 401;
	protected static final int SIGNUP_FAIL = 403;
	protected static final int BEACON_SUCCESS = 601;
	protected static final int LOGIN = 100;
	protected static final int LOGOUT =200;
	protected static final int STATE_CHECK =300;
	protected static final int SIGN_UP = 400;
	protected static final int ID_DUPLICATE = 500;
	protected static final int BEACON_INFO = 600;
	protected static final int COMPANY_ALL = 700;
	protected  static final String SIGNUPSQL = "insert into member (id,password,name) values (?,?,?)";
	protected  static final String SIGNINSQL = "update member set flag = 1 where id =? and password = ? and flag = 0";
	protected  static final String LOGOUTSQL = "update member set flag = 0 where id = ?";
	protected  static final String COMPANYSQL = "Select * from company where company.company in (Select company from connect_company where id = ?)";
	protected  static final String CONNECTSQL = "insert into connect_company values(?,?)";
	protected  static final String DUPLICATESQL = "Select id from member where id = ?";
	protected  static final String ERRORCHECKSQL = "select id,password,flag from member where id = ?";
	protected  static final String COMPANYRETURN = "Select company.company from company";
	protected  static final String ADMININSERT = "insert into company values (?,?,?,?)";
	protected  static final String ADMINUPDATE = "update company set latitude = ?, longitude=? , scope = ? where company.company = ?";
	protected  static final String STATECHECK = "Select flag from member where id = ?";
	protected  static final String CHECKADMIN = "select company.company from company where company.company = ?";
	protected static final String GETMUCHIN = "select * from machin";
}
