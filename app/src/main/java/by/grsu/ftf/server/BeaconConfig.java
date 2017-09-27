package by.grsu.ftf.server;


import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class BeaconConfig {

    private List<String> UUID = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<PointF> coordinates = new ArrayList<>();
    private List<Integer> rssiOneMeter = new ArrayList<>();

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

    public List<PointF> getCoordinates() {
        return coordinates;
    }

    public List<Integer> getRssiOneMeter() {
        return rssiOneMeter;
    }

    public List<String> getUUID() {
        return UUID;
    }

    public List<String> getName() {
        return name;
    }


}

