package by.grsu.ftf.bluetooth;

import android.bluetooth.BluetoothAdapter;

public class BluetoothControl {

    public static void Bluetoothcheck(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter!=null)
        {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
    }
}
