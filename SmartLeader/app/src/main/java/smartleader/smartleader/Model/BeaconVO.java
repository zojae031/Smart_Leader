package smartleader.smartleader.Model;

public class BeaconVO {
    private String UUID;
    private int major;
    private int minor;
    public BeaconVO(String UUID,int major,int minor){
        this.UUID = UUID;
        this.major = major;
        this.minor = minor;
    }

    public String getUUID() {
        return UUID;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }
}
