package smartleader.smartleader.ServerThreadHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import smartleader.smartleader.Activity.MainActivity;
import smartleader.smartleader.AppManager;
import smartleader.smartleader.BeaconService.BeaconService;
import smartleader.smartleader.Server.ServerConnection;
import smartleader.smartleader.Server.ServerLogin;
import smartleader.smartleader.Server.ServerLogout;
import smartleader.smartleader.Server.Server_State_Check;

public class ServerHandler extends Handler {
    Context context;//Application Context

    public ServerHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                Toast.makeText(context, "서버 수신 에러", Toast.LENGTH_SHORT).show();
                break;
            //999
            case ServerConnection.SERVER_CONNECT_ERROR:
                Toast.makeText(context, "서버가 닫혀있습니다.", Toast.LENGTH_SHORT).show();
                break;
            //100 LOGIN
            case ServerLogin.LOGIN_OK:
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
                break;
            case ServerLogin.LOGIN_ALREADY_CONNECT:
                break;
            case ServerLogin.LOGIN_FAIL:
                break;
            //200 LOGOUT
            case ServerLogout.LOGOUT_OK:
                context.stopService(new Intent(context,BeaconService.class));
                AppManager.getInstance().getMainActivity().finish();
                break;
            case ServerLogout.LOGOUT_FAIL:
                Toast.makeText(context,"로그아웃에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                break;
            //300 STATE_CHECK
            case Server_State_Check.STATE_LOGIN:
                break;
            case Server_State_Check.STATE_LOGOUT:
                break;
        }
    }
}
