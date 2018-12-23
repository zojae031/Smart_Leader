package smartleader.smartleader.ServerThreadHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import smartleader.smartleader.Server.ServerConnection;
import smartleader.smartleader.Server.ServerLogin;
import smartleader.smartleader.Server.Server_State_Check;

public class ServerHandler extends Handler {
    Context context;

    public ServerHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                Toast.makeText(context, "미지의 에러 확인 요함", Toast.LENGTH_SHORT).show();
                break;
            case ServerConnection.SERVER_CONNECT_ERROR:
                Toast.makeText(context, "서버가 닫혀있습니다.", Toast.LENGTH_SHORT).show();
                break;
            case ServerLogin.LOGIN_OK:
                break;
            case ServerLogin.LOGIN_ALREADY_CONNECT:
                break;
            case ServerLogin.LOGIN_FAIL:
                break;
            case Server_State_Check.STATE_LOGIN:
                break;
            case Server_State_Check.STATE_LOGOUT:
                break;
        }
    }
}
