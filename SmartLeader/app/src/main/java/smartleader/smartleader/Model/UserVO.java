package smartleader.smartleader.Model;

import java.io.Serializable;

public class UserVO implements Serializable {
    private String id;
    private String password;
    private String company;
    private String name;




    public UserVO(String id,String password,String company,String name){

        this.id = id;
        this.password = password;
        this.company = company;
        this.name = name;
    }

    public UserVO() {
    }

    public UserVO(String id) {
        this.id = id;
    }

    public UserVO(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public void setCompany(String company) {
        this.company = company;
    }




    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }
}
