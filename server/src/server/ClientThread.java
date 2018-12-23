package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	BufferedReader reader;
	PrintWriter writer;
	Socket client;
	String result;
	public static final int LOGIN_OK = 1;
	public static final int LOGIN_FAIL = 0;
	
	public ClientThread(Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
		try {
			reader = new BufferedReader (new InputStreamReader(client.getInputStream(),"UTF-8"));
			result = reader.readLine();
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8")),true);
			
			if(result.equals("1")) {
				writer.println(LOGIN_OK);	
			}
			else {
				writer.println(LOGIN_FAIL);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
			closeSocket();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void closeSocket()throws Exception{
		if(reader!=null) {
			reader.close();
		}
		if(writer !=null) {
			writer.close();
		}
		if(client!=null) {
			client.close();
		}
	}
}
