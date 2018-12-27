package Server;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class UserServer implements Runnable{
	public final int ServerPort = 5050;
	ServerSocket serverSocket = null;  
	InetAddress local;

	public void ServerOpen() throws Exception {
		try {
			local = InetAddress.getLocalHost();
			//String ip = local.getHostAddress();//������ ip
			String ip = "172.16.31.155";
			System.out.println("IP : " + ip);
			serverSocket = new ServerSocket(ServerPort);//������ ������ port
			System.out.println("Server Open");
			while (true) {
				Socket client = serverSocket.accept();//accept���¿����� block system�̴�. ���� ���� ���� block�ȴ�.
				ClientThread clientThread = new ClientThread(client);//�����忡 ����� ������ �ִ´�.
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
			
				serverSocket.close();
			}//������ ����������, ���μ����� ���� �ʴ´�.
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerOpen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
