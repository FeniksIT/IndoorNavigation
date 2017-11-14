package by.grsu.ftf.maths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;

public class ControllerBeacon {

    private List<Beacon> beaconList = new ArrayList<>();
    private HashMap<String, Beacon> beaconHashMap = new HashMap<>();

    public ControllerBeacon(){
    }

    public ControllerBeacon(List<Beacon> beaconListHashMap){
        beaconList = beaconListHashMap;
        for (Beacon beacon : beaconListHashMap) {
            beaconHashMap.put(beacon.getName(),beacon);
        }
    }

    public void addBeacon(Beacon beacon){
        beaconHashMap.put(beacon.getName(),beacon);
        beaconList.clear();
        for (Beacon beaconInfo : beaconHashMap.values()) {
            beaconList.add(beaconInfo);
        }
    }

    public List<Beacon> getListBeacon(){
        return beaconList;
    }
}
