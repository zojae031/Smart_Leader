package ClientJob;

import java.sql.SQLException;

import com.google.gson.JsonObject;

import DataBases.DBConnect;
import DataBases.DBConnectionInterface;
import DataPackage.MachinData;
import DataPackage.MachinDataStruct;

public class ReceiveShack extends DBConnect implements DBConnectionInterface {

	@Override
	public Object excute(JsonObject data) throws SQLException {
		MachinData queue;
		queue = MachinData.getMuchinData();
		//queue.getmuchin().get();
		MachinDataStruct temp = new MachinDataStruct();
		temp.id = data.get("id").toString().replaceAll("\"", "");
		
		temp.uuid = data.get("uuid").toString().replaceAll("\"", "");
		temp.major = data.get("major").getAsInt();
		temp.minor = data.get("minor").getAsInt();
		
		System.out.println(temp.major);
		System.out.println(temp.minor);
		System.out.println(temp.id);
		System.out.println(temp.uuid);
		queue.find_uuid(temp.uuid, temp.major, temp.minor).add(temp);
		queue.toString();
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
			System.out.println("5초 경과");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BEACON_SUCCESS;
	}
}
