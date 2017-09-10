package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.activity.MainActivity;
import by.grsu.ftf.beacon.*;
import by.grsu.ftf.bluetooth.*;
import by.grsu.ftf.maths.FilterDistance;
import by.grsu.ftf.server.BeaconConfig;

public class BroadCast extends BroadcastReceiver {

    public static final String TAG_BROAD_CAST="BroadCast";

    private FilterDistance filterDistance = new FilterDistance();
    private ArrayList<String> beacon = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        BeaconConfig beaconConfig = new BeaconConfig();
        beacon = intent.getStringArrayListExtra(BluetoothService.KEY_BEACON_SERVICE);
        BeaconInfo beaconInfo = new BeaconInfo(beacon);
        MainActivity.setBeaconTextView("Beacon Name: "+beaconInfo.getName()+" RSSI: "+beaconInfo.getRssi()+" UUID: "+beaconInfo.getUUID());
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+ beaconInfo.getUUID()+ " RSSI= "+ beaconInfo.getRssi());
        int index = beaconConfig.getName().indexOf(beaconInfo.getName());
        PointF coordinatesBeacon = beaconConfig.getCoordinates().get(index);
        float distanceBeacon = filterDistance.distance(beaconConfig.getRssiOneMeter().get(index),beaconInfo.getRssi());
        PointF coordinatesUser = FilterDistance.bic(beaconInfo.getName(),beaconInfo.getRssi(),coordinatesBeacon,distanceBeacon);
        if(coordinatesUser!=null)MainActivity.setBeaconTextView("Координаты "+ coordinatesUser);
    }
}