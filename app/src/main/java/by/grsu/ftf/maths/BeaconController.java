package by.grsu.ftf.maths;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import by.grsu.ftf.beacon.Beacon;

public class BeaconController {

    private HashMap<String, Beacon> beaconHashMap = new HashMap<>();
    private HashMap<String, String> dateReceiving = new HashMap<>();
    private TreeMap<Integer, Beacon> beaconTreeMap = new TreeMap<>();
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public BeaconController(){
    }

    public BeaconController(List<Beacon> beacons){
        for(Beacon beacon : beacons){
            beaconHashMap.put(beacon.getName(),beacon);
        }
    }

    public boolean addBeacon(Beacon beacon) {
        boolean contains = beaconHashMap.containsKey(beacon.getName());
        beaconHashMap.put(beacon.getName(),beacon);
        dateReceiving.put(beacon.getName(),simpleDateFormat.format(new Date()));
        return contains;
    }

    private void deleteOldBeacons(){
        for (Iterator<Map.Entry<String, String>> iterator = dateReceiving.entrySet().iterator(); iterator.hasNext();) {
            try {
                Map.Entry<String,String> infoBeacon = iterator.next();
                Date dateReceivingBeacon = simpleDateFormat.parse(infoBeacon.getValue());
                Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                if(currentDate.getTime() - dateReceivingBeacon.getTime() > 15000){
                    beaconHashMap.remove(infoBeacon.getKey());
                    iterator.remove();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /*private void deleteLowBeacon(){
        beaconTreeMap.clear();
        for(Beacon beacon : beaconHashMap.values()){
            beaconTreeMap.put(beacon.getRssi(),beacon);
        }
    }*/

    public List<Beacon> getBeaconList(){
        deleteOldBeacons();
        //deleteLowBeacon();
        return new ArrayList<>(beaconHashMap.values());
        //return new ArrayList<>(beaconTreeMap.values());
    }
}
