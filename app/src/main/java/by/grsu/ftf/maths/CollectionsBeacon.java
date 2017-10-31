package by.grsu.ftf.maths;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;

public class CollectionsBeacon {

    private static List<Beacon> beaconList = new ArrayList<>();
    private static ArrayMap<String,Beacon> beaconArrayMap = new ArrayMap<>();

    public static void sortingBeacon(Beacon beacon){
        beaconArrayMap.put(beacon.getName(),beacon);
        beaconList.clear();
        for (int i = 0; i < beaconArrayMap.size(); i++) {
            beaconList.add(beaconArrayMap.valueAt(i));
        }
    }

    public static List<Beacon> getListBeacon(){
        return beaconList;
    }
}
