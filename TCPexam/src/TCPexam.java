import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPexam {
	public final int ServerPort = 5050;
	ServerSocket socket = null;
	BufferedReader reader;
	PrintWriter writer;
	InetAddress local;
	public void open() {
		try {
			local = InetAddress.getLocalHost();
			System.out.println("open"+local.getHostAddress());
			socket = new ServerSocket(ServerPort);
			while(true) {
				Socket client = socket.accept();
				System.out.println("accept");
				reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
				String data = reader.readLine();
				System.out.println(data);
				writer = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8")), true);
				Thread.sleep(3000);
				writer.println(data);
				System.out.println(data);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TCPexam().open();

	}

}
