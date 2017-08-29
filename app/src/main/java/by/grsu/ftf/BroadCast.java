package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.beacon.*;

public class BroadCast extends BroadcastReceiver {

    private static final String TAG_BROAD_CAST="BroadCast";

    private ArrayList<String> Beacon = new ArrayList<>();

    BeaconSimulation beaconSimulation = new BeaconSimulation();

    @Override
    public void onReceive(Context context, Intent intent) {
        Beacon = intent.getStringArrayListExtra(beaconSimulation.KEY_BEACON_SIMULATION);
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+Beacon.get(0)+ " RSSI= "+ Beacon.get(1));
    }
}