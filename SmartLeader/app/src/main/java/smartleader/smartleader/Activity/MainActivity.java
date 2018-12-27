package smartleader.smartleader.Activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.BeaconService.BeaconService;
import smartleader.smartleader.Model.UserVO;
import smartleader.smartleader.R;
import smartleader.smartleader.Server.ServerLogout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BluetoothAdapter bluetoothAdapter;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getInstance().setMainActivity(this);
        CheckingBluetoothState();
        startService(new Intent(getApplicationContext(),BeaconService.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void CheckingBluetoothState() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish(); //서비스 종료
        }
        //블루투스 사용상태가 아닐경우 블루투스 사용상태 Dialog 출력
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout : logout();
                break;

        }
    }
    private void logout(){
        preferences = getSharedPreferences("data",MODE_PRIVATE);
        String id = preferences.getString("id","");
        new ServerLogout(AppManager.getInstance().getHandler(),new UserVO(id)).start();
    }
}
