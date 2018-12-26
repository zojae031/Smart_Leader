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

import smartleader.smartleader.AppManager;

/*
 * 서버 연결을 위한 최 상위클래스 ServerConnection Thread
 * 서버에 대한 기본 연결과 Handler 를 정의한다.
 */
public abstract class ServerConnection extends Thread {
    //Basic Error Code
    public static final int SERVER_CONNECT_ERROR = 999;

    //Server Information
    private String SERVER_ID = "192.168.0.10";
    private int port = 5050;

    //Server Connect
    private Socket socket;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    //Thread Handling
    protected Handler handler;
    protected Message msg;

    //Data Holder
    protected JSONObject jsonObject;
    protected String Result;

    public ServerConnection(Handler handler) {
        //서버 IP 유동적 변경을 위한 부분 (입력받은 IP가 있다면 그것으로 변경한다)
        if(!AppManager.getInstance().getServerIp().equals("")){
            SERVER_ID = AppManager.getInstance().getServerIp();
        }
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
