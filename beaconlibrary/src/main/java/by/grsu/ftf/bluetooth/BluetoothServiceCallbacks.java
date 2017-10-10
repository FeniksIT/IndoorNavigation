package by.grsu.ftf.bluetooth;


import java.util.List;

import by.grsu.ftf.beacon.BeaconInfo;

public interface BluetoothServiceCallbacks {
    void beaconCallbacks(List<BeaconInfo> beaconInfoList,boolean flagBluetoothEnable);
}
