package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Model.UserVO;

public class ServerLogin extends ServerConnection {
    public static final int LOGIN = 100;
    public static final int LOGIN_OK = 101;
    public static final int LOGIN_ALREADY_CONNECT = 102;
    public static final int LOGIN_FAIL = 103;
    public static final int LOGIN_NO_DATA = 104;

    private UserVO userVO;
    public ServerLogin(Handler handler, UserVO userVO) {
        super(handler);
        this.userVO = userVO;
    }


    @Override
    void sendData() throws JSONException {

        jsonObject.put("key", LOGIN);
        jsonObject.put("id",userVO.getId());
        jsonObject.put("password",userVO.getPassword());

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
                    case LOGIN_OK:
                        msg.what = LOGIN_OK;
                        msg.obj = userVO;
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
        }).start();

    }

}
