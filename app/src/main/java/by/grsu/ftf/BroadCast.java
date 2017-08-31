package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.activity.MainActivity;
import by.grsu.ftf.beacon.*;

public class BroadCast extends BroadcastReceiver {

    private static final String TAG_BROAD_CAST="BroadCast";

    private ArrayList<String> Beacon = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Beacon = intent.getStringArrayListExtra(BeaconSimulation.KEY_BEACON_SIMULATION);
        MainActivity.mText.append(Beacon.get(0)+"\n");
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+Beacon.get(0)+ " RSSI= "+ Beacon.get(1));
    }
}