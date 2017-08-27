package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import by.grsu.ftf.beacon.BeaconSimulation;

public class BroadCast extends BroadcastReceiver {

    private static final String TAG_BROAD_CAST="BroadCast";

    private String[] Beacon;

    BeaconSimulation beaconSimulation = new BeaconSimulation();

    @Override
    public void onReceive(Context context, Intent intent) {
        Beacon = intent.getStringArrayExtra(beaconSimulation.KEY_BEACON_SIMULATION);
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+Beacon[0]+ " RSSI= "+ Beacon[1]);
    }
}