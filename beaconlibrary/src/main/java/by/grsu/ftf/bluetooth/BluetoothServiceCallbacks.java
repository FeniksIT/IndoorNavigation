package by.grsu.ftf.bluetooth;

import by.grsu.ftf.beacon.Beacon;

public interface BluetoothServiceCallbacks {
    void beaconCallbacks(Beacon beacon, boolean flagBluetoothEnable);
}
