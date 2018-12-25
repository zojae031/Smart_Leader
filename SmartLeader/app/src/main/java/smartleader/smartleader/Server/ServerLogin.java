package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

public class ServerLogin extends ServerConnection {
    public static final int LOGIN = 100;
    public static final int LOGIN_OK = 101;
    public static final int LOGIN_ALREADY_CONNECT = 102;
    public static final int LOGIN_FAIL = 103;


    public ServerLogin(Handler handler) {
        super(handler);
    }


    @Override
    void sendData() throws JSONException {

        jsonObject.put("key", LOGIN);
        /*
         * Json에 정보 담는 작업 필요 -> 생성자로 정보 보내주기 ^_^
         */

        PrintWriter out = new PrintWriter(writer, true);
        out.println(jsonObject);

    }

    @Override
    void receiveData() {
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Result = reader.readLine();
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                switch (Integer.parseInt(Result)) {
                    case LOGIN_OK:
                        msg.what = LOGIN_OK;
                        break;
                    case LOGIN_ALREADY_CONNECT:
                        msg.what = LOGIN_ALREADY_CONNECT;
                        break;
                    case LOGIN_FAIL:
                        msg.what = LOGIN_FAIL;
                        break;
                }

                handler.sendMessage(msg);

            }
        });
        receiveThread.start();
    }

}
