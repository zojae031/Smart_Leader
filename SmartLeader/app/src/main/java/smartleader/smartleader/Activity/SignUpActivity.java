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

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonObject;

import java.util.ArrayList;

import java.util.regex.Pattern;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.Model.CompanyVO;
import smartleader.smartleader.Model.UserVO;
import smartleader.smartleader.R;
import smartleader.smartleader.Server.ServerCompanyCheck;
import smartleader.smartleader.Server.Server_Id_Duplicate;
import smartleader.smartleader.Server.Server_Sign_Up;

public class SignUpActivity extends AppCompatActivity {

    private EditText id, pw, pw_2, name;
    private String  user_pw, user_pw2;
    private TextView company, pw_signal;

    public static final int REQUEST_CODE = 100;

    private boolean CONFIRM_ID_OK = false;
    private boolean CONFIRM_PW_OK = false;
    private boolean CONFIRM_NAME_OK = false;
    private boolean CONFIRM_COMPANY_OK = false;
    private boolean CHECK_ASYNCTASK = true;

    Intent intent;
    private long backKeyPressedTime = 0;

    private String companyName="";

    private UserVO userVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userVO = new UserVO();
        setContentView(R.layout.activity_sign_up);
        AppManager.getInstance().setSignUpActivity(this);
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
            companyName = data.getStringExtra("companyName");
            company.setText(companyName);
        }
    }

    private void checkUserName() {
        userVO.setName(name.getText().toString());

        if (userVO.getName() != null) {
            CONFIRM_NAME_OK = true;
        }
    }

    private void checkCompName() {
        /*
         * TODO company 정보를 다중클릭으로 받아 userVO에 ArrayList로 저장하기
         */
//        userVO.setCompany(company.getText().toString());


        if(userVO.getCompany().size()==0){
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

            new Server_Sign_Up(AppManager.getInstance().getHandler(),userVO);

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
        userVO.setId(id.getText().toString());

        if (userVO.getId().length() < 4 || userVO.getId().length() > 16)
            Toast.makeText(getApplicationContext(), "아이디를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
        else {
            new Server_Id_Duplicate(AppManager.getInstance().getHandler(),new UserVO(userVO.getId()),CONFIRM_ID_OK).start();
        }
    }

    public void startSelectPopup() {
        new ServerCompanyCheck(AppManager.getInstance().getHandler()).start();
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
            userVO.setPassword(user_pw);
            pw_signal.setTextColor(Color.parseColor("#99FF99"));
        } else {
            pw_signal.setText("비밀번호가 일치하지 않습니다.");
            pw_signal.setTextColor(Color.parseColor("#FF8888"));
        }
    }
}
