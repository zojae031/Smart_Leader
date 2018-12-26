package ClientJob;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;

public class SignUp  extends DBConnect implements DBConnectionInterface{

	public SignUp()
	{
		super();
	}
	@Override
	public Object excute(JsonObject data) throws SQLException {
		// TODO Auto-generated method stub
		if(!connection())
			return LOGIN_FAIL;
		int return_value = 0;
				System.out.println("signin start >> "+data.get("id").toString());
				PreparedStatement stat;
				stat = conn.prepareStatement(SIGNUPSQL);
				stat.setString(1, data.get("id").toString().replace("\"",""));
				stat.setString(2, data.get("password").toString().replace("\"",""));
				stat.setString(3, data.get("name").toString().replace("\"",""));
				System.out.println(data.get("name").toString().replace("\"",""));
				try{
						stat.executeUpdate();//id�� �����ϸ� try_catch������ ����. (SQL_ERROR);
					}catch(SQLException e)
					{
						System.out.println("exist name");//id�� �����ϸ� return _ �ߺ�
						return_value = DUPLICATE_ID;
						return return_value;
					}
				stat = conn.prepareStatement(CONNECTSQL);
				stat.setString(1, data.get("id").toString().replace("\"", ""));
				stat.setString(2, data.get("company").toString().replace("\"", ""));
				stat.executeUpdate();//ȸ�簡 �������� �ʴ°��� ������ �� ����. -> ���� : ȸ�����Խ� �����ִ� ������ ��� ȸ�� ������ ��Ȯ�ϴ�.
				stat.close();
				if(return_value == 0)
					return_value = SUCCESS;
				System.out.println("signin end");
				closeConnection();
				return return_value;
	}
	private boolean signup(JsonObject data)
	{
		
		return true;
	}//transaction ����*����
	// transaction���� �������� �ʴ°�� signup�� ������������, id�� �Է��̵Ǵ� �⹦�� ���°� ������ connect_company�� ���� ������ ���� ����.
}
