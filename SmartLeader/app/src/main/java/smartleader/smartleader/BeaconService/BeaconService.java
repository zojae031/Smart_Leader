package smartleader.smartleader.BeaconService;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.Model.BeaconVO;
import smartleader.smartleader.ServerThreadHandler.ServerHandler;
import smartleader.smartleader.ShakeAlgorithm.ShakeAlgorithm;

public class BeaconService extends Service {


    static final String TAG = "비콘";
    BeaconManager beaconManager;
    private Context context;
    private int min;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        AppManager.getInstance().setHandler(new ServerHandler(context));
        BeaconManagerSetting();
        setNotification();

        super.onCreate();
    }
    @Override
    public void onDestroy() {
        beaconManager.disconnect();
    }

    private void BeaconManagerSetting(){
        beaconManager = new BeaconManager(this);

        beaconManager.setBackgroundScanPeriod(5000,0);
        beaconManager.setForegroundScanPeriod(5000,0);

        //해당 위치에 들어왔을 때
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                if(list.size()!=0) {
                    Log.e(TAG, "비콘 감지" + list.get(0).getRssi());
                }

            }

            @Override
            public void onExitedRegion(Region region) {
                Log.e(TAG,"비콘벗어남");
                ShakeAlgorithm.getInstance(context).removeListener();
            }
        });
        //거리 탐색 설정

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                for(Beacon beacon : list){
                    min = Math.abs(beacon.getRssi());
                    if(AppManager.getInstance().getShakeFlag()) {
                        if(min >= Math.abs(beacon.getRssi())){
                            BeaconVO beaconVO = new BeaconVO(beacon.getProximityUUID().toString(), beacon.getMajor(), beacon.getMinor());
                            ShakeAlgorithm.getInstance(context).registerListener();
                            AppManager.getInstance().setBeaconVO(beaconVO);
                            Log.e(TAG, "RSSI" + beacon.getRssi());
                            Log.e(TAG, "UUID" + beacon.getProximityUUID());
                        }
                    }
                }
            }
        });
        //서비스 준비를 위한 콜백 설정
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Region region = new Region("monitored region",UUID.fromString("74278bda-b644-4520-8f0c-720eaf059935"),null,null);
                beaconManager.startMonitoring(region);
                beaconManager.startRanging(region);
            }
        });
    }
    //Notification을 추가함으로써 ForeGround에 Service가 실행되고있음을 보임으로써 Service 종료 방지
    private void setNotification() {
        startForeground(1, new Notification());
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
