package smartleader.smartleader.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.Model.UserVO;
import smartleader.smartleader.R;
import smartleader.smartleader.Server.Server_Id_Duplicate;

public class SignUpActivity extends AppCompatActivity {

    private EditText id, pw, pw_2, name;
    private String user_id, user_pw, user_pw2, user_name;
    private TextView company, pw_signal;

    private final int REQUEST_CODE = 100;

    private boolean CONFIRM_ID_OK = false;
    private boolean CONFIRM_PW_OK = false;
    private boolean CONFIRM_NAME_OK = false;
    private boolean CONFIRM_COMPANY_OK = false;
    private boolean CHECK_ASYNCTASK = true;

    Intent intent;
    private long backKeyPressedTime = 0;

    private String companyName = "";//서버로 보낼 company이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SetContents();

        StartASyncTask();
    }

    //영문,숫자만 입력(한글 필터링)
    protected InputFilter filter = new InputFilter() {

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    private void SetContents() {
        id = (EditText) findViewById(R.id.textView_id);
        id.setFilters(new InputFilter[]{filter});

        pw = (EditText) findViewById(R.id.editText_pw);
        pw.setFilters(new InputFilter[]{filter});

        pw_2 = (EditText) findViewById(R.id.editText_pw2);
        pw_2.setFilters(new InputFilter[]{filter});

        name = (EditText) findViewById(R.id.editText_name);

        company = (TextView) findViewById(R.id.company);
        pw_signal = (TextView) findViewById(R.id.pw_signal);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:   //ID 확인하는 과정
                getUserId();
                break;

            case R.id.selectComp:
                startSelectPopup();
                break;

            case R.id.finishBtn:
                finishSignUp();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            companyName = data.getStringExtra("compName");
            String textCompany = data.getStringExtra("textCompany");
            company.setText(textCompany);
        }
    }

    private void checkUserName() {
        user_name = name.getText().toString();

        if (user_name != null) {
            new Server_Id_Duplicate(AppManager.getInstance().getHandler(),new UserVO(user_name)).start();

            CONFIRM_NAME_OK = true;
        }
    }

    private void checkCompName() {
        company.getText().toString();

        if(company.equals("")){
            CONFIRM_COMPANY_OK = false;
        }
        else{
            CONFIRM_COMPANY_OK = true;
        }
    }

    private boolean checkSignUp() {
        checkUserName();
        checkCompName();

        if (CONFIRM_NAME_OK && CONFIRM_ID_OK && CONFIRM_PW_OK && CONFIRM_COMPANY_OK) {
            CHECK_ASYNCTASK = false;
            /*서버로 넘어가는 부분*/
            return true;
        } else
            return false;


    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "회원가입을 취소하려면 \'뒤로\'버튼을 한번 더 눌러주세요.", Toast.LENGTH_LONG).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        CHECK_ASYNCTASK = false;
        super.onDestroy();
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... progress) {
            while (CHECK_ASYNCTASK) {
                try {
                    publishProgress();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
            getUserPassword();
        }
    }

    private void StartASyncTask() {
        MyAsyncTask mTask = new MyAsyncTask();
        mTask.execute();
    }



    public void getUserId() {
        user_id = id.getText().toString();

        if (user_id.length() < 4 || user_id.length() > 16)
            Toast.makeText(getApplicationContext(), "아이디를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
        else {
            //서버에서 ID 중복확인 받는 코드 , RSULT_CODE 받아야됨
        }
    }

    public void startSelectPopup() {
        intent = new Intent(this, SelectCompanyActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void finishSignUp() {
        if (checkSignUp()) {
            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "입력정보를 확인해주세요", Toast.LENGTH_LONG).show();
        }
    }

    public void getUserPassword() {
        user_pw = pw.getText().toString();
        user_pw2 = pw_2.getText().toString();

        if (user_pw.length() < 6 || user_pw.length() > 16) {
            pw_signal.setText("형식이 맞지 않습니다.");
            pw_signal.setTextColor(Color.parseColor("#FF8888"));
        } else if (user_pw.equals(user_pw2)) {
            CONFIRM_PW_OK = true;
            pw_signal.setText("사용가능");
            pw_signal.setTextColor(Color.parseColor("#99FF99"));
        } else {
            pw_signal.setText("비밀번호가 일치하지 않습니다.");
            pw_signal.setTextColor(Color.parseColor("#FF8888"));
        }
    }
}