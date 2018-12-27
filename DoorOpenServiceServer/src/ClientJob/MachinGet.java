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
		ArrayList<ArrayList<MachinDataStruct>> machin = null;
		ArrayList<MachinDataStruct> temp2= null;
		
		if(!connection())
			return null;
		stat = conn.prepareStatement(GETMUCHIN);
		res=stat.executeQuery();
		machin = new ArrayList<ArrayList<MachinDataStruct>>();
		while(res.next())
		{
			MachinDataStruct temp;
			temp2 = new ArrayList<MachinDataStruct>();
			temp = new MachinDataStruct();
			temp.uuid = res.getString("UUID");
			temp.major= res.getInt("major");
			temp.minor = res.getInt("minor");
			temp.id = null;
			temp2.add(temp);
			machin.add(temp2);
		}
		return machin;
	}	
}
