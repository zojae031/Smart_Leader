package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class DuplicateID extends DBConnect implements DBConnectionInterface {
	public DuplicateID()
	{
		super();
	}
	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		int return_value = 0;
		if(!connection())
			return LOGIN_FAIL;
		if(!check_duplicate(data))
			return_value = SIGNUP_DUPLICATE_ID;
		closeConnection();
		if(return_value == 0)
			return_value = NOT_DUPLICATE_ID;
		return return_value;
	}//ȸ�������Ҷ� id�� �ߺ��Ǵ��� Ȯ���ϱ� ����.
	//���Ŀ� id�� �ߺ��ȴٸ� ���� ������, ���� �߰� ���� ������ �� �ֵ��� ����� ���� ���� �� ����.
	public boolean check_duplicate(JsonObject data) throws SQLException {
		PreparedStatement stat;
		stat = conn.prepareStatement(DUPLICATESQL);
		stat.setString(1, data.get("id").toString().replace("\"", ""));
		ResultSet res;
		res = stat.executeQuery();
		while(res.next())
		{
			return false;
		}
		res.close();
		stat.close();
		return true;		
	};
}
