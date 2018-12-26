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
		check=login(data);//id를 로그인을 수행했을때, 정상적으로 수행 되었는지 확인하기 위한 boolean값.
		if(!check)
		{
			return_value = checkerror(data);//정상 수행 되지 않았을때, 어떤 문제로 정상 수행되지 않았는지 확인하기 위한 method
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
			if(res.getInt("flag")== 1)//이미 로그인이 되어있는경우
			{
				System.out.println("login already");
				return_value = LOGIN_FAIL;
			}
			else//비밀번호가 틀린경우
			{
				System.out.println("password error");
				return_value = NO_DATA;
			}
		}
		else//id가 존재하지 않는경우
		{
			return_value = NO_DATA;
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
		if(stat.executeUpdate()==0)//executeUpdate() 메소드의 경우 변경된 row의 값을 리턴한다. 이것이 0 이라면, 변경된 값이 없으므로, login fales;
		{
			return_value = false;
		}
		else
			return_value = true;
		stat.close();
		return return_value;
	}
}
