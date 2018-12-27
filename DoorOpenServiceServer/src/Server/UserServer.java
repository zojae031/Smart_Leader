package Server;

import java.net.Socket;


public class UserServer extends ParentServer implements Runnable{
	
	public UserServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}
	public void ServerOpen() throws Exception {
		try {
			System.out.println("Server Open");
			while (true) {
				Socket client = socket.accept();
				ClientThread clientThread = new ClientThread(client);
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
