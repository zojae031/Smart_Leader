package smartleader.smartleader.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import smartleader.smartleader.R;
import smartleader.smartleader.Server.ServerLogin;

public class LoginActivity extends Activity implements View.OnClickListener {
    public static final int LOGIN_FAIL = 0;
    public static final int LOGIN_OK=1;
    private EditText id;
    private EditText password;
    private Button login_Btn;
    private Button sign_in_Btn;

    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        HandlerSetting();
    }
    private void findView(){
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        login_Btn = findViewById(R.id.login_btn);
        sign_in_Btn = findViewById(R.id.sign_in_btn);
    }
    public LoginActivity(){

    }
    private void HandlerSetting(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case LOGIN_FAIL:
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case LOGIN_OK:
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn :login();
                break;
            case R.id.sign_in_btn :
                break;
        }
    }
    void login(){
        if(id.getText().toString().equals("") && password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"정보를 입력하시오.",Toast.LENGTH_SHORT).show();
        }
        if(id.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"ID를 입력하시오.",Toast.LENGTH_SHORT).show();
        }
        if(password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"PW를 입력하시오.",Toast.LENGTH_SHORT).show();
        }
        if(!id.getText().toString().equals("") && !password.getText().toString().equals("")){
            ServerLogin serverLogin = new ServerLogin(handler);
            serverLogin.start();
        }
    }
}
