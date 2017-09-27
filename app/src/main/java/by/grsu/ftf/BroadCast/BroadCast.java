package by.grsu.ftf.BroadCast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.activity.MainActivity;
import by.grsu.ftf.beacon.*;
import by.grsu.ftf.bluetooth.*;
import by.grsu.ftf.maths.FilterDistance;
import by.grsu.ftf.server.BeaconConfig;

public class BroadCast extends BroadcastReceiver {

    public static final String TAG_BROAD_CAST="BroadCast";

    private FilterDistance filterDistance = new FilterDistance();
    private static List<String> Beacon = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        BeaconConfig beaconConfig = new BeaconConfig();
        ArrayList<BeaconInfo> beacon = intent.getParcelableArrayListExtra(BluetoothService.KEY_BEACON_SERVICE);
        BeaconInfo beaconInfo = beacon.get(0);
        Beacon.add(beaconInfo.getName()+"---"+beaconInfo.getRssi());
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+ beaconInfo.getUUID()+ " RSSI= "+ beaconInfo.getRssi());
        int index = beaconConfig.getName().indexOf(beaconInfo.getName());
        PointF coordinatesBeacon = beaconConfig.getCoordinates().get(index);
        float distanceBeacon = filterDistance.distance(beaconConfig.getRssiOneMeter().get(index),beaconInfo.getRssi());
        PointF coordinatesUserPointF = FilterDistance.bic(beaconInfo.getName(),beaconInfo.getRssi(),coordinatesBeacon,distanceBeacon);
        //if(coordinatesUser!=null)Beacon.add("Координаты "+ coordinatesUserPointF);
        if(coordinatesUserPointF!=null){
            String coordinateUser="Coordinate user"+ coordinatesUserPointF.toString();
            MainActivity.coordinatesTextViwe.setText(coordinateUser);
        }
        MainActivity.adapter.notifyDataSetChanged();
    }

    public static List<String> getBeacon() {
        return Beacon;
    }
}