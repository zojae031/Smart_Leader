package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DataBases.DBConnectionInterface;
import DataBases.DBFactory;

public class ClientThread extends Thread {
	BufferedReader reader; 
	PrintWriter writer; 
	Socket client;
	
	DBFactory db; 
	public ClientThread(Socket client) {
		this.client = client;
	}
	private void closeSocket() throws IOException{
		if (reader != null)
			reader.close();
		if (writer != null)
			writer.close();
		if (client != null)
			client.close();
	}
	@Override
	public void run() {
		DBConnectionInterface i_db;
		db = new DBFactory();
		try {
			reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));//한글을 받기위한 UTF-8 지정
			JsonParser parser = new JsonParser();
			JsonObject data = (JsonObject) parser.parse(reader.readLine());
			System.out.println("ID,PW >> : " + data.get("id") + data.get("password"));//확인을 위한 System.out 차후 이 작업을 LOG를 작성하는 Class 였으면 좋겠다.
			i_db = db.factory(data);
			writer = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8")), true);
			writer.println(i_db.excute(data));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				closeSocket();//소켓을 닫지않는경우 프로세스 자체가 죽지 않는다.(쓰레드 작업을 통해서 작업하기 때문)
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
