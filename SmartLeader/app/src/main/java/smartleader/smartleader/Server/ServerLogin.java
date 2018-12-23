package smartleader.smartleader.Server;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Activity.LoginActivity;

public class ServerLogin extends ServerConnection {
    String Result;

    public ServerLogin(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        super.run();
        if(writer!=null){
            sendData();
        }
        if(reader!=null){
            receiveData();
        }
    }

    @Override
    void sendData() {
        PrintWriter out = new PrintWriter(writer,true);
        out.println(LoginActivity.LOGIN_OK);
    }

    @Override
    void receiveData() {
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {

                Message msg = handler.obtainMessage();
                try {
                    Result = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(Result.equals("2")){
                    msg.what = LoginActivity.LOGIN_FAIL;
                }
                if(Result.equals("1")){
                    msg.what = LoginActivity.LOGIN_OK;
                }
                handler.sendMessage(msg);
            }
        });
        receiveThread.start();
    }
}
