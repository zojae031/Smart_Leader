package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class CompanyAll extends DBConnect implements DBConnectionInterface{

	@Override
	public Object excute(JsonObject data) throws SQLException {
		JsonArray return_value;
		return_value = makeList();//��� ȸ�� ������ �ȵ���̵忡 �����ش�.
		if(return_value== null)
			return LOGIN_FAIL;
		return return_value;
	}
	private JsonArray makeList() throws SQLException
	{
		if(!connection())
		{
			return null;
		}
		JsonArray return_value = null;
		PreparedStatement stat;
		stat = conn.prepareStatement(COMPANYRETURN);//select name from company;
		ResultSet res;
		res = stat.executeQuery();
		return_value = new JsonArray();
		while(res.next())
		{
			CompanyVO object;
			object = new CompanyVO(res.getString("company"));
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
	}// ��� �����ͺ��̽��� �ִ� ȸ�������� JsonArray �� �����Ѵ�.

}
