package Server;

import java.net.InetAddress;
import java.net.ServerSocket;
public class ParentServer {
			//InetAddress local;
			//protected static String ip;
			protected static String ip = "192.168.1.101";
			protected ServerSocket socket;
			protected int port;
			public ParentServer(int port)
			{
				this.port = port;
				try {
					//local = InetAddress.getLocalHost();
					socket = new ServerSocket(port);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ip = local.getHostAddress();
			}
			
}
