package smartleader.smartleader.Activity;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.R;
import smartleader.smartleader.Server.ServerLogin;
import smartleader.smartleader.ServerThreadHandler.ServerHandler;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText id;
    private EditText password;

    private EditText ip;
    private Button ipBtn;
    private Handler handler;

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 서버에서 로그인상태 체킹 후 Main으로 넘어갈것인지 이 상태를 유지 할 것인지 + 내부 데이터베이스 만들어야 할 필요성 존재
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkingPermission();
        findView();
        HandlerSetting();
    }

    private void findView() {
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);

        //추후 삭제 부분 + AppManager
        ip = findViewById(R.id.ip);
        ipBtn = findViewById(R.id.ipbtn);
        ipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ServerIp = ip.getText().toString();
                AppManager.getInstance().setServerIp(ServerIp);
            }
        });
        //추후 삭제 부분 <-> ServerConnection
    }

    private void HandlerSetting() {
        handler = new ServerHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.sign_in_btn:
                sign_in();
                break;
        }
    }

    void login() {

        if (id.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "ID를 입력하시오.", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "PW를 입력하시오.", Toast.LENGTH_SHORT).show();
        } else {
            new ServerLogin(handler).start();
        }
    }

    void sign_in() {

    }

    private void checkingPermission() {
        //위치 권한 동의 설정
        //위치정보 허가받기 (RunTime Permission Check)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

}
