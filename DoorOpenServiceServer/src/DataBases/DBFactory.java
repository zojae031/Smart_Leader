package DataBases;


import com.google.gson.JsonObject;

import ClientJob.Admin;
import ClientJob.CompanyAll;
import ClientJob.CompanySend;
import ClientJob.DuplicateID;
import ClientJob.Login;
import ClientJob.Logout;
import ClientJob.SignUp;

public class DBFactory {

	
	public DBConnectionInterface factory(JsonObject data)
	{
		int key;
		key = Integer.parseInt(data.get("key").toString());
		switch(key)
		{
		case 1:
			return new Login();
		case 2:
			return new Logout();
		case 3:
			return new CompanySend();
		case 4:
			return new DuplicateID();
		case 5:
			return new SignUp();
		case 6:
			return new Admin();
		case 7:
			return new CompanyAll();
		}
		return null;
	}//안드로이드에서 key 값을 넘겨준 값에 해당하는 작업을 return 한다. 각 클래스를 new 를 통해서 return 하는 이유는 interface를 통해 작업을 수행하기 때문이다.
}
