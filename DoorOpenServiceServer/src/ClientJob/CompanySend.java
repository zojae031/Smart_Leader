package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class CompanySend extends DBConnect implements DBConnectionInterface{
	
	public CompanySend()
	{
		super();
	}
	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		JsonArray return_value;
		return_value = makeList(data.get("id").toString().replace("\"",""));
		if(return_value == null)
			return LOGIN_FAIL;
		return return_value;
	}//ȸ�������� id �� �ش��ϴ� ȸ�翡 ���� ������ �������ش�.
	private JsonArray makeList(String id) throws SQLException
	{
		if(!connection())
		{
			return null;
		}
		JsonArray return_value;
		PreparedStatement stat;
		stat = conn.prepareStatement(COMPANYSQL);
		stat.setString(1, id);
		ResultSet res;
		res = stat.executeQuery();
		return_value = new JsonArray();
		while(res.next())
		{
			CompanyVO object;
			object = new CompanyVO(res.getString("company"),res.getFloat("latitude"),res.getFloat("longitude"),res.getFloat("scope"));
			return_value.add(object.company);
		}
		if(return_value.size() == 0)
		{
			return_value = null;
		}
		res.close();
		stat.close();
		closeConnection();
		return return_value;
	}//company�� ���� ������ return �ؼ� �����ش�. ( �̶� ��� table = connect_company, member, company)
}
