package DataPackage;

import java.sql.SQLException;
import java.util.ArrayList;

import ClientJob.MachinGet;
import ClientJob.ReceiveShack;

public class MachinData {
	private static MachinData data = null;
	private ArrayList<ArrayList<MachinDataStruct>> machin;
	
	@SuppressWarnings("unchecked")
	private MachinData()
	{
			try {
				machin = (ArrayList<ArrayList<MachinDataStruct>>) new MachinGet().excute(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static MachinData getmachinData()
	{
		if(data == null)
		{
			data = new MachinData();
		}
		return data;
	}
	public ArrayList<ArrayList<MachinDataStruct>> getmachin()
	{
		return machin;		
	}
	public ArrayList<MachinDataStruct> find_uuid(String uuid,int major,int minor)
	{
		ArrayList<MachinDataStruct> temp= null;
		for(ArrayList<MachinDataStruct> a : machin)
		{
			System.out.printf("%s, %s, %d %d\n", a.get(0).id,a.get(0).uuid,a.get(0).major,a.get(0).minor);		
			if(a.get(0).uuid.equals(uuid) && a.get(0).major == major && a.get(0).minor == minor)
				{
					temp = a;
					break;
				}
		}
		return temp;		
	}
	public String toString()
	{
		int i=0;
		for(ArrayList<MachinDataStruct> a :machin)
		{
			for(MachinDataStruct b: a)
			{
				System.out.print("출력 :");
				System.out.printf("%s, %s, %d %d\n", b.id,b.uuid,b.major,b.minor);		
			}
			System.out.println("index>>"+i++);
		}
		return null;
	}
	public boolean check_my_id(String uuid,int major,int minor,String id)
	{
		ArrayList<MachinDataStruct> temp;
		temp = find_uuid(uuid,major,minor);
		int i;
		for(i=1;i<temp.size();i++)
		{
			if(temp.get(i).id.equals(id))
			{
				return true;
			}
		}
		return false;
	}
	public void pop_machin(String uuid,int major,int minor)
	{
		ArrayList<MachinDataStruct> temp;
		temp = find_uuid(uuid,major,minor);
		temp.remove(1);
	}
}
