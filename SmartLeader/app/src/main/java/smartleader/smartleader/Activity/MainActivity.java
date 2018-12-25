package smartleader.smartleader.Activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import smartleader.smartleader.BeaconService.BeaconService;
import smartleader.smartleader.R;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckingBluetoothState();
        startService(new Intent(getApplicationContext(),BeaconService.class));
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
}
