package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new mServer().serverOpen();
	}

}
class mServer{
	ArrayList<Socket> ThreadList ;
	public static final int ServerPort = 5050;
	ServerSocket serverSocket =null;
	InetAddress local;
	public mServer() {
		ThreadList = new ArrayList<Socket>();
	}
	public void serverOpen() {
		try {
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();
			System.out.println(ip);
			serverSocket = new ServerSocket(ServerPort);
			while(true) {
				Socket client = serverSocket.accept();
				ClientThread clientThread = new ClientThread(client);
				clientThread.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
