package DataBases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DBConnect{	
	//��� ������ ���̽� �۾��� �� Ŭ������ ����ؾ� �Ѵ�.
	//�����ͺ��̽��� ���� ������ �� Ŭ������ ����Ѵ�.	
	
	protected String address = "jdbc:mariadb://localhost:3306/dooropenservice";
	protected String user = "root";
	protected String password = "920821";
	public final int LOGIN_OK = 1;
	public final int NO_DATA = 2;
	public final int LOGIN_FAIL = 3;
	public final int DUPLICATE_ID = 4;
	public final int SUCCESS = 5;
	
	// SQL���� ���� �߰��� ������� protected final�� ���� Ŭ���� ��� ���谡 �ƴ� �̻� Ȯ�� �� �� ���� �Ѵ�.
	protected final String SIGNUPSQL = "insert into member (id,password,name) values (?,?,?)";
	protected final String SIGNINSQL = "update member set flag = 1 where id =? and password = ? and flag = 0";
	protected final String LOGOUTSQL = "update member set flag = 0 where id = ?";
	protected final String COMPANYSQL = "Select * from company where company.company in (Select company from connect_company where id = ?)";
	protected final String CONNECTSQL = "insert into connect_company values(?,?)";
	protected final String DUPLICATESQL = "Select id from member where id = ?";
	protected final String ERRORCHECKSQL = "select id,password,flag from member where id = ?";
	protected final String COMPANYRETURN = "Select company.company from company";
	protected final String ADMININSERT = "insert into company values (?,?,?,?)";
	protected final String ADMINUPDATE = "update company set latitude = ?, longitude=? , scope = ? where company.company = ?";
	
	protected final String CHECKADMIN = "select company.company from company where company.company = ?";
	
	protected static Connection conn;
	
	public DBConnect() {
	}
	
	protected boolean connection() {
	// TODO Auto-generated method stub
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(address, user,new String(password));
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}//��� �۾��� ����
	protected boolean closeConnection()  {
		if (conn != null)
			try {
				conn.close();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}//��� �۾��� ����
}
