package Server;

import java.net.Socket;

public class ArduinoServer extends ParentServer implements Runnable {
	public ArduinoServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}
	public void ServerOpen() throws Exception {
		try {
			System.out.println("IP : " + ip);
			System.out.println("Server Open");
			while (true) {
				Socket client = socket.accept();//accept���¿����� block system�̴�. ���� ���� ���� block�ȴ�.
				ClientThread clientThread = new ClientThread(client);//�����忡 ����� ������ �ִ´�.
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
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
