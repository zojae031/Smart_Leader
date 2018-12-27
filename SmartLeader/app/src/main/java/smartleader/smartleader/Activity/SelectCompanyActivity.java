package smartleader.smartleader.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.R;

public class SelectCompanyActivity extends Activity {
    private Button finishBtn;
    private RadioGroup radioGroup;
    private String companyName = "";
    private String originCompName="";
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
        intent.putExtra("originCompName",originCompName);
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
        Intent dataIntent = getIntent();
        final String jsonArray = dataIntent.getStringExtra("data");

        try{
            final JSONArray array = new JSONArray(jsonArray);

            for (int i = 0; i < array.length(); i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                RadioButton radioButton = new RadioButton(getApplicationContext());
                radioButton.setId(i);
                radioButton.setLayoutParams(params);

                String compName = array.get(i).toString().replace("{\"company\":\"","");
                compName = compName.replace("\"}","");

                radioButton.setText(getStringResourceByName(compName));
                radioButton.setTextColor(Color.parseColor("#FFFFFF"));
                radioGroup.addView(radioButton);
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    try {
                        originCompName = array.get(checkedId).toString();
                        companyName = array.get(checkedId).toString().replace("{\"company\":\"","");
                        companyName = companyName.replace("\"}","");
                        companyName = getStringResourceByName(companyName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        //서버에서 리스트 뿌려주는 작업
    }

    private String getStringResourceByName(String string){
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(string,"string",packageName);
        return getString(resId);
    }
}