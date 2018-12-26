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
			return_value = DUPLICATE_ID;
		closeConnection();
		if(return_value == 0)
			return_value = SUCCESS;
		return return_value;
	}//회원가입할때 id가 중복되는지 확인하기 위함.
	//차후에 id가 중복된다면 정보 수정과, 정보 추가 등이 존재할 수 있도록 만드는 것이 좋을 것 같다.
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
