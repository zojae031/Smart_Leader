package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

public class Server_State_Check extends ServerConnection {
    public static final int STATE = 300;
    public static final int STATE_LOGIN = 301;
    public static final int STATE_LOGOUT = 302;

    public Server_State_Check(Handler handler) {
        super(handler);
    }


    @Override
    void sendData() throws JSONException {

        jsonObject.put("key", STATE);


        PrintWriter out = new PrintWriter(writer, true);
        out.println(jsonObject);
    }

    @Override
    void receiveData() {
        Thread receiveData = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Result = reader.readLine();
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (Integer.parseInt(Result)) {
                    case STATE_LOGIN:
                        msg.what = STATE_LOGIN;
                        break;
                    case STATE_LOGOUT:
                        msg.what = STATE_LOGOUT;
                        break;
                }
                handler.sendMessage(msg);
            }
        });
        receiveData.start();
    }
}
