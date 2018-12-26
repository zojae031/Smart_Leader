package DataBases;
import java.sql.SQLException;

import com.google.gson.JsonObject;
public interface DBConnectionInterface {
	public Object excute(JsonObject data) throws SQLException;
}// 모든 db작업에 있어 Interface를 통해서만 작업이 가능하게끔, 작업한다.
