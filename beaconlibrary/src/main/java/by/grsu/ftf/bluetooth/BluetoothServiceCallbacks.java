package by.grsu.ftf.bluetooth;

import by.grsu.ftf.beacon.Beacon;

public interface BluetoothServiceCallbacks {
    void onReceivingBeacon(Beacon beacon, boolean flagBluetoothEnable);
}
