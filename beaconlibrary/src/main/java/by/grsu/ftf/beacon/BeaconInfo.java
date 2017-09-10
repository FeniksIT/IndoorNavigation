package by.grsu.ftf.beacon;

import android.graphics.PointF;

import java.util.ArrayList;

public class BeaconInfo {

    private String name;
    private String UUID;
    private int rssi;

    BeaconInfo(String name, String UUID, int rssi) {
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
    }

    public BeaconInfo(ArrayList<String> beacon){
        this.name = beacon.get(0);
        this.UUID = beacon.get(1);
        this.rssi = Integer.valueOf(beacon.get(2));
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return UUID;
    }

    public int getRssi() {
        return rssi;
    }

}