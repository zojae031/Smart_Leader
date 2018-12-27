package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
//ADMIN ���� �α��������� �����ϴ� ����
//�ȵ���̵忡�� ADMIN �� �Ǻ��ؼ� ���� DISPLAY�� �ٸ���.

public class Admin extends DBConnect implements DBConnectionInterface {

	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		// TODO Auto-generated method stub
		int return_value;
		if(insertvalue(data))//insert�� ����������.
		{
			return_value = SIGNUP_SUCCESS;
		}
		else//insert�� �ȉ�����, ��ǻ� �ȵǴ� ������ ����. (company.compay == company �ΰ�� �����ϰ� �ȴ�.)
		{
			return_value = SIGNUP_FAIL;
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
		if(res.next())//���� company.company �� ������ �����Ѵٸ� update�� �����Ѵ�.
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
		}// ���ٸ� insert ����
		stat.executeQuery();
		res.close();
		stat.close();
		closeConnection();
		return true;
	}

}
