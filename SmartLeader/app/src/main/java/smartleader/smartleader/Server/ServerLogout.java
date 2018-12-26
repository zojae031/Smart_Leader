package smartleader.smartleader.Server;


import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Model.UserVO;

public class ServerLogout extends ServerConnection {
    public static final int LOGOUT = 200;
    public static final int LOGOUT_OK = 201;
    public static final int LOGOUT_FAIL = 202;

    private UserVO userVO;
    public ServerLogout(Handler handler,UserVO userVO) {
        super(handler);
        this.userVO = userVO;
    }

    @Override
    void sendData() throws JSONException {
        jsonObject.put("key", LOGOUT);
        jsonObject.put("id",userVO.getId());
        PrintWriter out = new PrintWriter(writer, true);
        out.println(jsonObject);
    }

    @Override
    void receiveData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Result = reader.readLine();
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (Integer.parseInt(Result)) {
                    case LOGOUT_OK:
                        msg.what = LOGOUT_OK;
                        break;
                    case LOGOUT_FAIL:
                        msg.what = LOGOUT_FAIL;
                        break;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }
}
