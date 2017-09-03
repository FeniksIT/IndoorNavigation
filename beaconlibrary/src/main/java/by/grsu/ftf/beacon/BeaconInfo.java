package by.grsu.ftf.beacon;

import android.graphics.PointF;

import java.util.ArrayList;

public class BeaconInfo {

    private String name;
    private String UUID;
    private int txPower;
    private int rssi;
    private float distance;
    private PointF coordinates;

    BeaconInfo(String name, String UUID, int txPower, int rssi, float distance, PointF coordinates) {
        this.name = name;
        this.UUID = UUID;
        this.txPower = txPower;
        this.rssi = rssi;
        this.distance = distance;
        this.coordinates = coordinates;
    }

    public BeaconInfo(ArrayList<String> beacon){
        this.name = beacon.get(0);
        this.UUID = beacon.get(1);
        this.txPower = Integer.valueOf(beacon.get(2));
        this.rssi = Integer.valueOf(beacon.get(3));
        this.distance = Float.valueOf(beacon.get(4));
        this.coordinates = new PointF(Float.valueOf(beacon.get(5)),Float.valueOf(beacon.get(6)));
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return UUID;
    }

    public int getTxPower() {
        return txPower;
    }

    public int getRssi() {
        return rssi;
    }

    public float getDistance() {
        return distance;
    }

    public PointF getCoordinates() {
        return coordinates;
    }
}