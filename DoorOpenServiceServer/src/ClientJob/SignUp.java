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
						stat.executeUpdate();//id가 존재하면 try_catch문으로 들어간다. (SQL_ERROR);
					}catch(SQLException e)
					{
						System.out.println("exist name");//id가 존재하면 return _ 중복
						return_value = DUPLICATE_ID;
						return return_value;
					}
				stat = conn.prepareStatement(CONNECTSQL);
				stat.setString(1, data.get("id").toString().replace("\"", ""));
				stat.setString(2, data.get("company").toString().replace("\"", ""));
				stat.executeUpdate();//회사가 존재하지 않는경우는 존재할 수 없다. -> 이유 : 회원가입시 보여주는 정보는 모두 회사 정보가 정확하다.
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
	}//transaction 구현*차후
	// transaction으로 구현하지 않는경우 signup이 실패했을때도, id는 입력이되는 기묘한 사태가 벌어짐 connect_company에 값이 연결이 되지 않음.
}
