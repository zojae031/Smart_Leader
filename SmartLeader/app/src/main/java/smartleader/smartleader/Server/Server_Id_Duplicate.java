package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Model.UserVO;

public class Server_Id_Duplicate extends ServerConnection {
    public static final int ID_DUPLICATE = 500;
    public static final int DUPLICATE=501;
    public static final int NOT_DUPLICATE = 502;
    UserVO userVO;
    boolean CONFIRM_ID_OK = false;

    public Server_Id_Duplicate(Handler handler,UserVO userVO,boolean CONFIRM_ID_OK) {
        super(handler);
        this.userVO = userVO;

        this.CONFIRM_ID_OK = CONFIRM_ID_OK;
    }

    @Override
    void sendData() throws JSONException {
        jsonObject.put("key",ID_DUPLICATE);
        jsonObject.put("id",userVO.getId());
        PrintWriter out = new PrintWriter(writer,true);
        out.println(jsonObject);
    }

    @Override
    void receiveData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Result = reader.readLine();
                    closeSocket();
                }catch (IOException e){
                    e.printStackTrace();
                }
                switch (Integer.parseInt(Result)){
                    case DUPLICATE :
                        msg.what = DUPLICATE;
                        CONFIRM_ID_OK = false;
                        break;
                    case NOT_DUPLICATE :
                        msg.what = NOT_DUPLICATE;
                        CONFIRM_ID_OK = true;
                        break;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }
}
