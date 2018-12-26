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
			return_value = LOGOUT_FAIL;
		}
		return_value = LOGOUT_OK;
		stat.close();
		closeConnection();
		return return_value;
	}//�α׾ƿ��ǰ�� �α����� �ϰ� �� ���� �α׾ƿ��̹Ƿ� üũ�� ���� �� �ʿ䰡 ����.
}
