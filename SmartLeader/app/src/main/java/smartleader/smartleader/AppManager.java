package smartleader.smartleader;

import android.app.Activity;

import smartleader.smartleader.Model.BeaconVO;
import smartleader.smartleader.ServerThreadHandler.ServerHandler;

public class AppManager {
    private static AppManager instance;

    private String ServerIp="";
    private static ServerHandler handler;

    private Activity LoginActivity;
    private Activity MainActivity;
    private Activity SelectCompanyActivity;
    private Activity SignUpActivity;

    //BeaconVO
    BeaconVO beaconVO;


    private boolean shakeFlag=true;

    private AppManager(){

    }
    public static AppManager getInstance(){
        if(instance==null){
            instance = new AppManager();
        }
        return instance;
    }
    public String getServerIp(){
        return ServerIp;
    }
    public void setServerIp(String ip){
        ServerIp = ip;
    }
    public void setHandler(ServerHandler handler){
        this.handler = handler;
    }
    public ServerHandler getHandler(){
        return handler;
    }

    public void setSignUpActivity(Activity signUpActivity) {
        SignUpActivity = signUpActivity;
    }

    public Activity getSignUpActivity() {
        return SignUpActivity;
    }

    public void setLoginActivity(Activity loginActivity) {
        LoginActivity = loginActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        MainActivity = mainActivity;
    }

    public void setSelectCompanyActivity(Activity selectCompanyActivity) {
        SelectCompanyActivity = selectCompanyActivity;
    }

    public Activity getSelectCompanyActivity() {
        return SelectCompanyActivity;
    }

    public Activity getLoginActivity() {
        return LoginActivity;
    }

    public Activity getMainActivity() {
        return MainActivity;
    }

    public String getUUID() {
        return beaconVO.getUUID();
    }

    public void setUUID(String UUID) {
        beaconVO.setUUID(UUID);
    }

    public int getMajor() {
        return beaconVO.getMajor();
    }
    public void setMajor(int major){
        beaconVO.setMajor(major);
    }
    public void setMinor(int minor) {
        beaconVO.setMinor(minor);
    }

    public int getMinor(){
        return beaconVO.getMinor();
    }

    public BeaconVO getBeaconVO() {
        return beaconVO;
    }
    public void setBeaconVO(BeaconVO beaconVO){
        this.beaconVO = beaconVO;
    }
    public void setShakeFlag(boolean shakeFlag) {
        this.shakeFlag = shakeFlag;
    }
    public boolean getShakeFlag(){return shakeFlag;};
}
