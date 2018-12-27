package DataBases;


import com.google.gson.JsonObject;

import ClientJob.Admin;
import ClientJob.CompanyAll;
import ClientJob.CompanySend;
import ClientJob.DuplicateID;
import ClientJob.Login;
import ClientJob.Logout;
import ClientJob.ReceiveShack;
import ClientJob.SignUp;
import ClientJob.StateCheck;

public class DBFactory {	
	public DBConnectionInterface factory(JsonObject data)
	{
		int key;
		key = Integer.parseInt(data.get("key").toString());
		switch(key)
		{
		case DBConst.LOGIN:
			return new Login();
		case DBConst.LOGOUT:
			return new Logout();
		case DBConnect.STATE_CHECK:
			return new StateCheck();
		case 3:
			return new CompanySend();
		case DBConst.ID_DUPLICATE:
			return new DuplicateID();
		case DBConst.SIGN_UP:
			return new SignUp();
		case 6:
			return new Admin();
		case DBConst.COMPANY_ALL:
			return new CompanyAll();
		case DBConst.BEACON_INFO :
			return new ReceiveShack();
		}
		return null;
	}
}
