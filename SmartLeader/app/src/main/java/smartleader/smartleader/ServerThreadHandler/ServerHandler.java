package smartleader.smartleader.ServerThreadHandler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import smartleader.smartleader.Activity.MainActivity;
import smartleader.smartleader.AppManager;
import smartleader.smartleader.BeaconService.BeaconService;
import smartleader.smartleader.Model.UserVO;
import smartleader.smartleader.Server.ServerConnection;
import smartleader.smartleader.Server.ServerLogin;
import smartleader.smartleader.Server.ServerLogout;
import smartleader.smartleader.Server.Server_Id_Duplicate;
import smartleader.smartleader.Server.Server_Sign_Up;
import smartleader.smartleader.Server.Server_State_Check;

public class ServerHandler extends Handler {
    Context context;//Application Context
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

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
                login_ok(msg);
                startMainAct();
                break;
            case ServerLogin.LOGIN_ALREADY_CONNECT:
                Toast.makeText(context, "이미 로그인 중 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case ServerLogin.LOGIN_FAIL:
                Toast.makeText(context, "정보가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                break;
            case ServerLogin.LOGIN_NO_DATA:
                Toast.makeText(context, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                break;
            //200 LOGOUT
            case ServerLogout.LOGOUT_OK:
                logout_ok();
                break;
            case ServerLogout.LOGOUT_FAIL:
                Toast.makeText(context, "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                break;
            //300 STATE_CHECK
            case Server_State_Check.STATE_LOGIN:
                startMainAct();
                break;
            case Server_State_Check.STATE_NOT_LOGIN:
                //DO NOTHING
                break;
            //400 SIGN_UP
            case Server_Sign_Up
                    .SIGN_UP_SUCCESS:
                break;
            //500 ID_DUPLICATE
            case Server_Id_Duplicate
                    .DUPLICATE:
                Toast.makeText(context,"아이디가 중복됩니다.",Toast.LENGTH_SHORT).show();
                break;
            case Server_Id_Duplicate.NOT_DUPLICATE:
                Toast.makeText(context,"아이디가 중복되지 않습니다.",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void startMainAct() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private void login_ok(Message msg) {
        preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("id", ((UserVO) msg.obj).getId());
        editor.commit();
    }

    private void logout_ok() {
        preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove("id");
        editor.commit();
        context.stopService(new Intent(context, BeaconService.class));
        AppManager.getInstance().getMainActivity().finish();
    }
}
