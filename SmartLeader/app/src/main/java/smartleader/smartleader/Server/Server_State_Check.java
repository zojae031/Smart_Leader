package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Model.UserVO;

public class Server_State_Check extends ServerConnection {
    public static final int STATE = 300;
    public static final int STATE_LOGIN = 301;
    public static final int STATE_NOT_LOGIN = 302;
    UserVO userVO;
    public Server_State_Check(Handler handler, UserVO userVO) {
        super(handler);
        this.userVO = userVO;
    }


    @Override
    void sendData() throws JSONException {

        jsonObject.put("key", STATE);
        jsonObject.put("id",userVO.getId());

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
                    case STATE_NOT_LOGIN:
                        msg.what = STATE_NOT_LOGIN;
                        break;
                }
                handler.sendMessage(msg);
            }
        });
        receiveData.start();
    }
}
