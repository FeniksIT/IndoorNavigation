package by.grsu.ftf.beacon;


import android.graphics.PointF;

import java.util.ArrayList;

public class BeaconConfig {

    private ArrayList<String> UUID = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<PointF> coordinates = new ArrayList<>();
    private ArrayList<Integer> rssiOneMeter = new ArrayList<>();

    public BeaconConfig(){
        UUID.add("Beacon 1");
        UUID.add("Beacon 2");
        UUID.add("Beacon 3");
        UUID.add("Beacon 4");
        name.add("id1");
        name.add("id2");
        name.add("id3");
        name.add("id4");
        coordinates.add(new PointF(12,2));
        coordinates.add(new PointF(13,3));
        coordinates.add(new PointF(14,4));
        coordinates.add(new PointF(15,5));
        rssiOneMeter.add(-35);
        rssiOneMeter.add(-46);
        rssiOneMeter.add(-45);
        rssiOneMeter.add(-51);
    }

    public ArrayList<PointF> getCoordinates() {
        return coordinates;
    }

    public ArrayList<Integer> getRssiOneMeter() {
        return rssiOneMeter;
    }

    public ArrayList<String> getUUID() {
        return UUID;
    }

    public ArrayList<String> getName() {
        return name;
    }


}

