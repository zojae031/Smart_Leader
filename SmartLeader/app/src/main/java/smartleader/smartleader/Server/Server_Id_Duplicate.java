package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.Activity.SignUpActivity;
import smartleader.smartleader.Model.UserVO;

public class Server_Id_Duplicate extends ServerConnection {
    public static final int ID_DUPLICATE = 500;
    public static final int DUPLICATE=501;
    public static final int NOT_DUPLICATE = 502;
    UserVO userVO;
    SignUpActivity.FlagHolder flagHolder;

    public Server_Id_Duplicate(Handler handler,UserVO userVO,SignUpActivity.FlagHolder flagHolder) {
        super(handler);
        this.userVO = userVO;




        this.flagHolder = flagHolder;

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
                        flagHolder.ID_OK = false;
                        break;
                    case NOT_DUPLICATE :
                        msg.what = NOT_DUPLICATE;
                        flagHolder.ID_OK = true;
                        break;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }
}
