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
import DataBases.DBConst;
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
			reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));//�ѱ��� �ޱ����� UTF-8 ����
			JsonParser parser = new JsonParser();
			JsonObject data = (JsonObject) parser.parse(reader.readLine());
			i_db = db.factory(data);
			writer = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8")), true);
			System.out.println(data);
			writer.println(i_db.excute(data));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				closeSocket();//������ �����ʴ°�� ���μ��� ��ü�� ���� �ʴ´�.(������ �۾��� ���ؼ� �۾��ϱ� ����)
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
