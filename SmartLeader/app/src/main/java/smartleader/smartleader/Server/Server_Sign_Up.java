package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Model.UserVO;

public class Server_Sign_Up extends ServerConnection {
    public static final int SIGN_UP=400;
    public static final int SIGN_UP_SUCCESS=401;

    UserVO userVO;
    public Server_Sign_Up(Handler handler, UserVO userVO) {
        super(handler);
        this.userVO = userVO;
    }

    @Override
    void sendData() throws JSONException {
        jsonObject.put("key",SIGN_UP);
        jsonObject.put("id", userVO.getId());
        jsonObject.put("password",userVO.getPassword());
        jsonObject.put("name",userVO.getName());
        jsonObject.put("company",userVO.getCompany());

        PrintWriter out = new PrintWriter(writer,true);
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
                }catch (IOException e){
                    e.printStackTrace();
                }
                switch (Integer.parseInt(Result)){
                    case SIGN_UP_SUCCESS :
                        msg.what = SIGN_UP_SUCCESS;
                        break;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }
}
