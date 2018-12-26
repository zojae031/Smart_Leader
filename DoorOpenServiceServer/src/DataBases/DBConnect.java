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

public class DBConnect extends DBConst{
	protected String address = "jdbc:mariadb://localhost:3306/doorOpenService";
	protected String user = "root";
	protected String password = "920821";
	
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
	}
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
	}
}
