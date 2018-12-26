
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
import DataBases.DBFactory;
import Server.UserServer;

public class Main {
	public static void main(String[] args) {
		
		UserServer userServer = new UserServer();//UserServer 모듈 실행
		try {
			userServer.ServerOpen();//Server Open 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// open 이 안되면 실행하는 오류처리
		}
	}
}
