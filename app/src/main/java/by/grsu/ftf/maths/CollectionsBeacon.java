package by.grsu.ftf.maths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;

public class CollectionsBeacon {

    private List<Beacon> beaconList = new ArrayList<>();
    private HashMap<String, Beacon> beaconHashMap = new HashMap<>();

    public void addBeacon(Beacon beacon){
        beaconHashMap.put(beacon.getName(),beacon);
        beaconList.clear();
        for (Beacon s : beaconHashMap.values()) {
            beaconList.add(s);
        }
    }

    public List<Beacon> getListBeacon(){
        return beaconList;
    }
}
