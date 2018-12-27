package ClientJob;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class SignUp  extends DBConnect implements DBConnectionInterface{
	@Override
	public Object excute(JsonObject data) throws SQLException {
		// TODO Auto-generated method stub
		if(!connection())
			return LOGIN_FAIL;
		int return_value = 0;
		return_value = signup(data);
		System.out.println("signin start >> "+data.get("id").toString());
		closeConnection();
		return return_value;
	}
	private int signup(JsonObject data) throws SQLException
	{
		PreparedStatement stat;
		int return_value = 0;
		stat = conn.prepareStatement(SIGNUPSQL);
		stat.setString(1, data.get("id").toString().replace("\"",""));
		stat.setString(2, data.get("password").toString().replace("\"",""));
		stat.setString(3, data.get("name").toString().replace("\"",""));
		System.out.println(data.get("name").toString().replace("\"",""));
		try{
				stat.executeUpdate();//id�� �����ϸ� try_catch������ ����. (SQL_ERROR);
		}
		catch(SQLException e)
		{
				System.out.println("exist name");//id�� �����ϸ� return _ �ߺ�
				return_value = SIGNUP_DUPLICATE_ID;
				return return_value;
		}
		stat = conn.prepareStatement(CONNECTSQL);
		stat.setString(1, data.get("id").toString().replace("\"", ""));
		stat.setString(2, data.get("company").toString().replace("\"", ""));
		stat.executeUpdate();
		stat.close();
		if(return_value == 0)
			return_value = SIGNUP_SUCCESS;
		return return_value;
	}
}
