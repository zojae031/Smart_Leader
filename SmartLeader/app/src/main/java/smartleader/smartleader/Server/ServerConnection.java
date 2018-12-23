package smartleader.smartleader.Server;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.security.auth.callback.Callback;

public abstract class ServerConnection extends Thread{
    private String SERVER_ID = "172.30.1.22";
    private int port = 5050;

    private Socket socket;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    protected Handler handler;

    private Callback callback;

    public ServerConnection(Handler handler){

        this.handler = handler;

    }
    @Override
    public void run() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_ID,port),2000);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
//            ((IServerCallback)callback).ConnectionError();
        }
    }
    abstract void sendData();
    abstract void receiveData();
}
