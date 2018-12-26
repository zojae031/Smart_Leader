package ClientJob;

import com.google.gson.JsonObject;

public class CompanyVO{
	JsonObject company;
	
	public CompanyVO(String company,float latitude,float longitude,float scope)
	{
		this.company = new JsonObject();
		this.company.addProperty("company", company);
		this.company.addProperty("latitude",Float.toString(latitude));
		this.company.addProperty("longitude",Float.toString(longitude));
		this.company.addProperty("scope", Float.toString(scope));
	}

	public CompanyVO(String company) {
		this.company = new JsonObject();
		this.company.addProperty("company",company);
	}
}//JsonArray에 Json Object 를 넣기 위한 구조체
