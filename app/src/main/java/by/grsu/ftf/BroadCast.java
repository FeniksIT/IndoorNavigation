package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.activity.MainActivity;
import by.grsu.ftf.beacon.*;
import by.grsu.ftf.bluetooth.*;
import by.grsu.ftf.math.FilterDistance;

public class BroadCast extends BroadcastReceiver {

    public static final String TAG_BROAD_CAST="BroadCast";

    private ArrayList<String> Beacon = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Beacon = intent.getStringArrayListExtra(BluetoothService.KEY_BEACON_SERVICE);
        BeaconInfo beaconInfo = new BeaconInfo(Beacon);
        MainActivity.mText.append(beaconInfo.getName()+"\n");
        //Log.d(TAG_BROAD_CAST, "Бикон UUID= "+ Beacon.get(0)+ " RSSI= "+ Beacon.get(3));
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+ beaconInfo.getUUID()+ " RSSI= "+ beaconInfo.getDistance());
        //FilterDistance.bic(beaconInfo.getName(),beaconInfo.getRssi(),beaconInfo.getCoordinates(),beaconInfo.getDistance());
    }
}