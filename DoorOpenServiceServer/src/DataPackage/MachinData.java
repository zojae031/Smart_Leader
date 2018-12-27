package DataPackage;

import java.sql.SQLException;
import java.util.ArrayList;

import ClientJob.MachinGet;
import ClientJob.ReceiveShack;

public class MachinData {
	private static MachinData data = null;
	private ArrayList<ArrayList<MachinDataStruct>> muchin;
	
	@SuppressWarnings("unchecked")
	private MachinData()
	{
			muchin = new ArrayList<ArrayList<MachinDataStruct>>();
			ArrayList<MachinDataStruct> temp = null;	
			try {
				temp = (ArrayList<MachinDataStruct>) new MachinGet().excute(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(MachinDataStruct a : temp)
			{
				System.out.printf("%s, %s, %d %d\n", a.id,a.uuid,a.major,a.minor);
			}
			//for(MachinDataStruct a : temp)
			muchin.add(temp);
			System.out.println(1);
	}
	public static MachinData getMuchinData()
	{
		if(data == null)
		{
			data = new MachinData();
		}
		return data;
	}
	public ArrayList<ArrayList<MachinDataStruct>> getmuchin()
	{
		return muchin;		
	}
	public ArrayList<MachinDataStruct> find_uuid(String uuid,int major,int minor)
	{
		ArrayList<MachinDataStruct> temp= null;
		for(ArrayList<MachinDataStruct> a : muchin)
		{
			for(MachinDataStruct b : a)
			{
				System.out.printf("%s, %s, %d %d\n", b.id,b.uuid,b.major,b.minor);		
				if(b.uuid.equals(uuid))
				{
					temp = a;
					break;
				}
			}
		}
		return temp;		
	}
	public String toString()
	{
		int i=0;
		for(ArrayList<MachinDataStruct> a :muchin)
		{
			for(MachinDataStruct b: a)
			{
				System.out.println("index>>"+i);
				System.out.print("출력 :");
				System.out.printf("%s, %s, %d %d\n", b.id,b.uuid,b.major,b.minor);		
				i++;
			}
		}
		return null;
	}
}
