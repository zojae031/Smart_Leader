package Bluetooth;
import javax.bluetooth.*;
public class PBluetooth {
	LocalDevice local = null;
	public PBluetooth() {
	try {
		System.out.print(LocalDevice.getLocalDevice());
		local = LocalDevice.getLocalDevice();
	} catch (BluetoothStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
