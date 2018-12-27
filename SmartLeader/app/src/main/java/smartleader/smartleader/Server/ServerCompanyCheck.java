package smartleader.smartleader.Server;

import android.os.Handler;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonArray;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonParser;

import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;

public class ServerCompanyCheck extends ServerConnection {
    public static final int COMPANY_ALL = 700;
    public static final int COMPANY_SUCCESS = 701;

    public ServerCompanyCheck(Handler handler) {
        super(handler);
    }

    @Override
    void sendData() throws JSONException {
        jsonObject.put("key",COMPANY_ALL);
        PrintWriter out = new PrintWriter(writer,true);
        out.println(jsonObject);
    }

    @Override
    void receiveData() throws NumberFormatException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = (JsonArray) parser.parse(reader.readLine());
                    msg.obj = jsonArray;
                    if(jsonArray.size()!=0){
                        msg.what = COMPANY_SUCCESS;
                    }
                    closeSocket();
                }catch (IOException e){
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }

        }).start();
    }
}
