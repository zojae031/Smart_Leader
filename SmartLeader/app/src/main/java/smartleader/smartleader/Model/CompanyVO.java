package smartleader.smartleader.Model;

import java.io.Serializable;

public class CompanyVO implements Serializable {
    private String company;
    private String major;
    private String minor;
    private String uuid;
    public CompanyVO(String company, String major ,String minor,String uuid){
        this.company = company;
        this.major = major;
        this.minor = minor;
        this.uuid = uuid;
    }
    public void setCompany(String company) { this.company = company; }

    public void setMajor(String major) { this.major= major; }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompany() {
        return company;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public String getUuid() {
        return uuid;
    }
}
