package smartleader.smartleader;

public class AppManager {
    private static AppManager instance;
    private String ServerIp="";
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
}
