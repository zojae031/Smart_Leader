package smartleader.smartleader.Server;

import android.os.Handler;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.Model.UserVO;

public class Server_Send_Beacon_Information extends ServerConnection {
    UserVO userVO;
    public Server_Send_Beacon_Information(Handler handler, UserVO userVO) {
        super(handler);
        this.userVO = userVO;
    }
    public static final int BEACON_INFO = 600;
    public static final int BEACON_SUCCESS = 601;

    @Override
    void sendData() throws JSONException {
        jsonObject.put("key",BEACON_INFO);
        jsonObject.put("id",userVO.getId());
        jsonObject.put("uuid",AppManager.getInstance().getUUID());
        jsonObject.put("major",AppManager.getInstance().getMajor());
        jsonObject.put("minor",AppManager.getInstance().getMinor());
        PrintWriter out = new PrintWriter(writer,true);
        out.println(jsonObject);
    }

    @Override
    void receiveData() throws NumberFormatException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Result = reader.readLine();
                    closeSocket();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Result!=null) {
                    switch (Integer.parseInt(Result)) {
                        case BEACON_SUCCESS:
                            msg.what = BEACON_SUCCESS;
                            break;
                    }
                }
                handler.sendMessage(msg);
            }

        }).start();
    }
}
