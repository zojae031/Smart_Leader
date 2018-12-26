package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class StateCheck extends DBConnect implements DBConnectionInterface {

	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		if(!connection())
			return LOGIN_FAIL;		
		int return_value=0;
		return_value=check(data);
		closeConnection();
		return return_value;
		
	}
	private int check(JsonObject data) throws SQLException
	{
		PreparedStatement stat;
		ResultSet res;
		int return_value=0;
		stat = conn.prepareStatement(STATECHECK);
		
		stat.setString(1, data.get("id").toString().replace("\"",""));
		res = stat.executeQuery();
		if(res.next())
		{
			if(res.getInt("flag")== 1)//�̹� �α����� �Ǿ��ִ°��
			{
				System.out.println("login already");
				return_value = STATE_LOGIN;
			}
			else
			{
				return_value = STATE_NOT_LOGIN;
			}
		}
		else
			return_value = STATE_NOT_LOGIN;
		stat.close();
		res.close();
		return return_value;
	}
}
