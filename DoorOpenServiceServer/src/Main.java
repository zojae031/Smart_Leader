
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
import DataBases.DBFactory;
import Server.UserServer;

public class Main {
	public static void main(String[] args) {
		
		UserServer userServer = new UserServer();//UserServer ��� ����
		try {
			userServer.ServerOpen();//Server Open 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// open �� �ȵǸ� �����ϴ� ����ó��
		}
	}
}
