package smartleader.smartleader.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.R;

public class SelectCompanyActivity extends Activity {
    private Button finishBtn;
    private RadioGroup radioGroup;
    private String companyName = "";
    private String textCompany = "";
    Intent intent;

    //서버에서 리스트 받아와야됨

    private final int RESULT_OK = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().setSelectCompanyActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_company);

        setContents();
        setList();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(companyName.equals(""))
                    Toast.makeText(getApplicationContext(),"장소를 선택해주세요.",Toast.LENGTH_SHORT).show();
                else {
                    exitOption();
                }
            }
        });
    }

    private void exitOption() {
        intent.putExtra("compName", companyName);
        intent.putExtra("textCompany", textCompany);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onBackPressed() {
        exitOption();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {     //바깥영역 터치 안되게
        if (e.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    private void setContents() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        finishBtn = (Button) findViewById(R.id.finishBtn);

        intent = new Intent();

    }

    public void setList() {
        Bundle bundle = getIntent().getExtras();
        JsonArray jsonArray = (JsonArray) bundle.get("data");

        //서버에서 리스트 뿌려주는 작업
    }
}