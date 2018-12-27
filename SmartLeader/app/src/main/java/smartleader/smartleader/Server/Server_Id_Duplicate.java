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
    public Server_Id_Duplicate(Handler handler,UserVO userVO) {
        super(handler);
        this.userVO = userVO;
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
                        break;
                    case NOT_DUPLICATE :
                        msg.what = NOT_DUPLICATE;
                        break;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }
}
