package smartleader.smartleader.ShakeAlgorithm;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import smartleader.smartleader.AppManager;
import smartleader.smartleader.Model.UserVO;
import smartleader.smartleader.Server.Server_Send_Beacon_Information;

public class ShakeAlgorithm implements SensorEventListener, IShakeCallback {

    //객체
    private LocationHolder locationHolder; //좌표정보를 저장,관리하는 클래스
    Vibrator vibrator; //알고리즘이 제대로 동작하였을시 작동하는 vibrator 객체
    static SensorManager sensorManager; //Shake 정보를 얻어올 Manager 객체
    SharedPreferences preferences;

    //변수
    private float speed; //가속 스피드를 저장할 변수
    private static long lastTime = 0;// 시간차를 계산하기위한 변수
    private static final int SHAKE_POWER = 1200; //비교 할 Shake 의 강도 (높을수록 강하게 둔함, 낮을수록 예민)
    private static ShakeAlgorithm instance = null;
    //앱 정보
    Context context;


    public static ShakeAlgorithm getInstance(Context context) {
        if (instance == null) {
            instance = new ShakeAlgorithm(context);
        }
        return instance;
    }

    private ShakeAlgorithm(Context context) {
        this.context = context;

        //Shake정보를 담을 홀더클래스 생성
        locationHolder = new LocationHolder();

        //센서 리스너 등록

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE); //SeosorManager가져오기

        registerListener();

        //ShakeService가 시작됨을 알리는 바이브레이터
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


    }

    //센서리스너가 등록되어있는지 아닌지 확인하는 메소드 (Implement IShakeCallback)
    @Override
    public boolean isListenerSet() {
        if (sensorManager == null) return false;
        else return true;
    }

    //센서리스너 등록 메소드 (Implement IShakeCallback)
    @Override
    public void registerListener() {
        //registerListener(Listener객체,센서타입,센서속도);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    //센서리스너 제거 메소드 (Implement IShakeCallback)
    @Override
    public void removeListener() {
        sensorManager.unregisterListener(this);//한번 감지가 되면 리스너를 제거해버림
    }


    //SensorEvenetListener를 위해 구현된 메소드
    @Override
    public void onSensorChanged(SensorEvent event) {
        final int ShakeTerm = 100; //Shake를 감지하는 시간 (초기 300)

        //센서의 값이 바뀔때 호출되는 메소드
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis(); // 현재시간 불러오기
            long gabOfTime = (currentTime - lastTime); //현재시간과 마지막시간의 차이

            if (gabOfTime > ShakeTerm) {
                lastTime = currentTime;
                locationHolder.setCurrentLocation(event.values[0], event.values[1], event.values[2]); //현재 좌표정보 저장
                /*
                x = values[0] : 디바이스 가로
                y = values[1] : 디바이스 뚫고나오는 방향
                z = values[2] : 디바이스 세로
                 */
                speed = Math.abs(locationHolder.calculateSubtraction()) / gabOfTime * 10000; // 현재좌표와 이전좌표의 차이를 계산

                if (speed > SHAKE_POWER) {

                    vibrator.vibrate(500);


                    removeListener();//블루투스 송신을 위해 리스너 제거
                    AppManager.getInstance().setShakeFlag(false);
                    preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
                    String id = preferences.getString("id","");

                    new Server_Send_Beacon_Information(AppManager.getInstance().getHandler(),new UserVO(id)).start();
                }

                locationHolder.setLastLocation(event.values[0], event.values[1], event.values[2]);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //센서의 정확도 값이 바뀔 때 호출
    }


}
