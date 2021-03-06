package smartleader.smartleader.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.Model.UserVO;
import smartleader.smartleader.R;
import smartleader.smartleader.Server.ServerLogin;
import smartleader.smartleader.Server.Server_State_Check;
import smartleader.smartleader.ServerThreadHandler.ServerHandler;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText id;
    private EditText password;
    SharedPreferences preferences;
    private long lastTimePressed = 0;

    @Override
    protected void onResume() {
        super.onResume();

        //TODO 서버에서 로그인상태 체킹 후 Main으로 넘어갈것인지 이 상태를 유지 할 것인지 + 내부 데이터베이스 만들어야 할 필요성 존재
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppManager.getInstance().setLoginActivity(this);
        checkingPermission();
        findView();
        HandlerSetting();
        preferences = getSharedPreferences("data",MODE_PRIVATE);
        String id = preferences.getString("id","");
        new Server_State_Check(AppManager.getInstance().getHandler(),new UserVO(id)).start();
    }

    private void findView() {
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        //id.setFilters(new InputFilter[]{filter});
        password.setFilters(new InputFilter[]{filter});
    }

    private void HandlerSetting() {
        AppManager.getInstance().setHandler(new ServerHandler(getApplicationContext()));
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
            case R.id.copy :
                if (System.currentTimeMillis() - lastTimePressed < 2000)
                {
                    String ServerIp = id.getText().toString();
                    AppManager.getInstance().setServerIp(ServerIp);
                    Toast.makeText(getApplicationContext(),"IP 재설정 완료",Toast.LENGTH_SHORT).show();
                }
                //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
                lastTimePressed = System.currentTimeMillis();


                break;
        }
    }

    void login() {
        if (id.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "ID를 입력하시오.", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "PW를 입력하시오.", Toast.LENGTH_SHORT).show();
        } else {
            new ServerLogin(AppManager.getInstance().getHandler(),new UserVO(id.getText().toString(),password.getText().toString())).start();
        }
    }

    void sign_in() {
        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void checkingPermission() {
        //위치 권한 동의 설정
        //위치정보 허가받기 (RunTime Permission Check)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    protected InputFilter filter = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
}
