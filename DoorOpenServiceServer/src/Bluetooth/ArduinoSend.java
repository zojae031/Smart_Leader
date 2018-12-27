package Bluetooth;

import java.io.IOException;
import java.util.ArrayList;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class ArduinoSend extends PBluetooth {
	protected static ArrayList<RemoteDevice> remoteDevice;
	private BluetoothListener listener = new BluetoothListener();
	public ArduinoSend() {
		super();		
	}
	public void search_bluetooth()
	{
		DiscoveryAgent agent = local.getDiscoveryAgent();
		try {
			agent.startInquiry(DiscoveryAgent.GIAC, listener);
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		for(RemoteDevice a : remoteDevice)
		{
			try {
				System.out.printf("%d ADD>>%s  %s\n ",i++, a.getBluetoothAddress(),a.getFriendlyName(true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
class BluetoothListener implements DiscoveryListener{

	@Override
	public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
		// TODO Auto-generated method stub
		ArduinoSend.remoteDevice.add(arg0);
	}

	@Override
	public void inquiryCompleted(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
		// TODO Auto-generated method stub
		
	}
	
}