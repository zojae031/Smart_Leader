package smartleader.smartleader;

import android.app.Activity;

import smartleader.smartleader.ServerThreadHandler.ServerHandler;

public class AppManager {
    private static AppManager instance;
    private String ServerIp="";
    private ServerHandler handler;
    private Activity LoginActivity;
    private Activity MainActivity;
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

    public void setLoginActivity(Activity loginActivity) {
        LoginActivity = loginActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        MainActivity = mainActivity;
    }

    public Activity getLoginActivity() {
        return LoginActivity;
    }

    public Activity getMainActivity() {
        return MainActivity;
    }
}
