
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
import DataBases.DBFactory;
import Server.ArduinoServer;
import Server.UserServer;

public class Main {
	public static void main(String[] args) {
		UserServer userServer = new UserServer(5050);//UserServer ��� ����
		ArduinoServer arduino = new ArduinoServer(5060);
		Thread server[] = new Thread[2];
		server[0] = new Thread(userServer);
		server[1] = new Thread(arduino);
		for(Thread p : server)
		{
			p.start();
		}
	}
}
