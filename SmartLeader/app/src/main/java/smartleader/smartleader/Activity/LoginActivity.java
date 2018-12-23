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
import smartleader.smartleader.ServerThreadHandler.ServerHandler;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText id;
    private EditText password;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        HandlerSetting();
    }

    private void findView() {
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
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
}
