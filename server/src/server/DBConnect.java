package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	protected String address = "jdbc:mariadb://localhost:3306/smartleader";
	protected String user = "root";
	protected String password = "920821";
	
	protected static Connection conn;
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
		}//모든 작업에 선행
	
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
	}//모든 작업에 후행
}
