package Server;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class UserServer {
	ArrayList<Socket> ThreadList ;
	public static final int ServerPort = 5050;
	ServerSocket serverSocket = null;  
	InetAddress local;//서버 자신의 주소를 가져오기 위한 java library;

	public UserServer() {
		ThreadList = new ArrayList<Socket>();
	}

	public void ServerOpen() throws Exception {
		try {
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();//서버의 ip
			System.out.println("IP : " + ip);
			serverSocket = new ServerSocket(ServerPort);//서버의 정해진 port
			System.out.println("Server Open");
			while (true) {
				Socket client = serverSocket.accept();//accept상태에서는 block system이다. 서버 오픈 이후 block된다.
				ClientThread clientThread = new ClientThread(client);//쓰레드에 통신할 소켓을 넣는다.
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				serverSocket.close();
			}//서버를 닫지않으면, 프로세스가 죽지 않는다.
		}
	}
}
