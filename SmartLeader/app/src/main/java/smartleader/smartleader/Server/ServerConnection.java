package smartleader.smartleader.Server;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
 * 서버 연결을 위한 최 상위클래스 ServerConnection Thread
 * 서버에 대한 기본 연결과 Handler 를 정의한다.
 */
public abstract class ServerConnection extends Thread {
    //Basic Error Code
    public static final int SERVER_CONNECT_ERROR = 999;

    //Server Information
    private String SERVER_ID = "172.30.1.22";
    private int port = 5050;

    //Server Connect
    private Socket socket;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    //Thread Handling
    protected Handler handler;
    protected Message msg;

    //Data Holder
    JSONObject jsonObject;
    String Result;

    public ServerConnection(Handler handler) {
        //Constructor basic handler setting
        this.handler = handler;
        msg = handler.obtainMessage();
        jsonObject = new JSONObject();
    }

    @Override
    public void run() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_ID, port), 2000);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendData();
            receiveData();
            closeSocket();

        } catch (IOException e) {
            e.printStackTrace();
            msg.what = SERVER_CONNECT_ERROR;
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    abstract void sendData() throws JSONException;

    abstract void receiveData();

    void closeSocket() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
