package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
//ADMIN 으로 로그인했을때 실행하는 로직
//안드로이드에서 ADMIN 을 판별해서 띄우는 DISPLAY가 다르다.

public class Admin extends DBConnect implements DBConnectionInterface {

	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		// TODO Auto-generated method stub
		int return_value;
		if(insertvalue(data))//insert가 성공했을때.
		{
			return_value = SUCCESS;
		}
		else//insert가 안됬을때, 사실상 안되는 이유는 없다. (company.compay == company 인경우 변경하게 된다.)
		{
			return_value = LOGIN_FAIL;
		}
		return return_value;
	}
	@SuppressWarnings("resource")
	private boolean insertvalue(JsonObject data) throws SQLException
	{
		if(!connection())
		{
			return false;
		}

		PreparedStatement stat;
		ResultSet res;
		
		stat = conn.prepareStatement(CHECKADMIN);
		stat.setString(1,data.get("company").toString().replace("\"",""));
		res = stat.executeQuery();
		if(res.next())//만약 company.company 가 같은게 존재한다면 update를 수행한다.
		{
			stat = conn.prepareStatement(ADMINUPDATE);		
			stat.setString(4,data.get("company").toString().replace("\"",""));
			stat.setFloat(1,data.get("latitude").getAsFloat());
			stat.setFloat(2,data.get("longitude").getAsFloat());
			stat.setFloat(3,data.get("scope").getAsFloat());
		}	
		else {
			stat = conn.prepareStatement(ADMININSERT);
			stat.setString(1,data.get("company").toString().replace("\"",""));
			stat.setFloat(2,data.get("latitude").getAsFloat());
			stat.setFloat(3,data.get("longitude").getAsFloat());
			stat.setFloat(4,data.get("scope").getAsFloat());
		}// 없다면 insert 수행
		stat.executeQuery();
		res.close();
		stat.close();
		closeConnection();
		return true;
	}

}
