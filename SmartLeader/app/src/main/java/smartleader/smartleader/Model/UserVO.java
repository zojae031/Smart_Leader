package smartleader.smartleader.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserVO implements Serializable {
    private String id;
    private String password;
    private ArrayList<CompanyVO> companyV0ArrayList;
    private String name;
    public UserVO(String id,String password,ArrayList<CompanyVO> companyV0ArrayList,String name){
        this.id = id;
        this.password = password;
        this.companyV0ArrayList = companyV0ArrayList;
        this.name = name;
    }
    public UserVO(String id){
        this.id = id;
    }
    public UserVO(String id,String password){
        this.id=id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public ArrayList<CompanyVO> getCompanyVOArrayList() {
        return companyV0ArrayList;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setCompanyVOArrayList(ArrayList<CompanyVO> companyVOArrayList) { this.companyV0ArrayList = companyVOArrayList; }

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
