package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class Login extends DBConnect implements DBConnectionInterface {
	public Login()
	{
		super();
	}
	
	@Override
	public Object excute(JsonObject data) throws SQLException {
		if(!connection())
			return LOGIN_FAIL;		
		int return_value=0;
		boolean check;
		check=login(data);//id�� �α����� ����������, ���������� ���� �Ǿ����� Ȯ���ϱ� ���� boolean��.
		if(!check)
		{
			return_value = checkerror(data);//���� ���� ���� �ʾ�����, � ������ ���� ������� �ʾҴ��� Ȯ���ϱ� ���� method
		}
		else if(check)
			return_value = LOGIN_OK;
		closeConnection();
		return return_value;
	}
	private int checkerror(JsonObject data) throws SQLException{
		PreparedStatement stat;
		ResultSet res;
		int return_value=0;
		stat = conn.prepareStatement(ERRORCHECKSQL);
		stat.setString(1, data.get("id").toString().replace("\"",""));
		res = stat.executeQuery();
		if(res.next())
		{
			if(res.getInt("flag")== 1)//�̹� �α����� �Ǿ��ִ°��
			{
				System.out.println("login already");
				return_value = LOGIN_ALREADY_CONNECT;
			}
			else//��й�ȣ�� Ʋ�����
			{
				System.out.println("password error");
				return_value = LOGIN_FAIL;
			}
		}
		else//id�� �������� �ʴ°��
		{
			return_value = LOGIN_FAIL;
		}
		stat.close();
		res.close();
		return return_value;
	}
	
	private boolean login(JsonObject data) throws SQLException{
		boolean return_value;
		PreparedStatement stat;
		stat = conn.prepareStatement(SIGNINSQL);
		stat.setString(1, data.get("id").toString().replace("\"",""));
		stat.setString(2, data.get("password").toString().replace("\"",""));
		if(stat.executeUpdate()==0)//executeUpdate() �޼ҵ��� ��� ����� row�� ���� �����Ѵ�. �̰��� 0 �̶��, ����� ���� �����Ƿ�, login fales;
		{
			return_value = false;
		}
		else
			return_value = true;
		stat.close();
		return return_value;
	}
}
