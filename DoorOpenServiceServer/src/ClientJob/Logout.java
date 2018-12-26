package ClientJob;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class Logout extends DBConnect implements DBConnectionInterface{

	public Logout()
	{
		super();
	}
	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		if(!connection())
			return LOGIN_FAIL;
		int return_value;
		PreparedStatement stat;
		stat = conn.prepareStatement(LOGOUTSQL);
		stat.setString(1, data.get("id").toString().replace("\"",""));
		if(stat.executeUpdate()==0)
		{
			return_value = LOGIN_FAIL;
		}
		return_value = LOGIN_OK;
		stat.close();
		closeConnection();
		return return_value;
	}//로그아웃의경우 로그인을 하고 난 이후 로그아웃이므로 체크를 따로 할 필요가 없다.
}
