package by.grsu.ftf.beacon;

import android.graphics.PointF;

import java.util.ArrayList;

public class BeaconInfo {

    private String name;
    private int txPower;
    private int rssi;
    private double distance;
    private PointF coordinates;

    BeaconInfo(String name, int txPower, int rssi, double distance, PointF coordinates) {
        this.name = name;
        this.txPower = txPower;
        this.rssi = rssi;
        this.distance = distance;
        this.coordinates = coordinates;
    }

    BeaconInfo(ArrayList<String> beacon){
        this.name = beacon.get(0);
        this.txPower = Integer.valueOf(beacon.get(1));
        this.rssi = Integer.valueOf(beacon.get(2));
        this.distance = Integer.valueOf(beacon.get(3));
        //this.coordinates = new PointF(beacon.get(4));
    }

    public String getName() {
        return name;
    }

    public int getTxPower() {
        return txPower;
    }

    public int getRssi() {
        return rssi;
    }

    public double getDistance() {
        return distance;
    }

    public PointF getCoordinates() {
        return coordinates;
    }
}