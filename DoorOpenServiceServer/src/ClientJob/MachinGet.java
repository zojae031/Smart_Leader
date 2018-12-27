package ClientJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
import DataPackage.MachinDataStruct;

public class MachinGet extends DBConnect implements DBConnectionInterface {
	@Override
	public Object excute(JsonObject data) throws SQLException {
		ResultSet res;
		PreparedStatement stat;
		ArrayList<MachinDataStruct> muchin= null;
		if(!connection())
			return null;
		stat = conn.prepareStatement(GETMUCHIN);
		res=stat.executeQuery();
		muchin = new ArrayList<MachinDataStruct>();
		while(res.next())
		{
			MachinDataStruct temp;
			temp = new MachinDataStruct();
			temp.uuid = res.getString("UUID");
			temp.major= res.getInt("major");
			temp.minor = res.getInt("minor");
			temp.id = null;
			muchin.add(temp);
		}
		return muchin;
	}	
}
