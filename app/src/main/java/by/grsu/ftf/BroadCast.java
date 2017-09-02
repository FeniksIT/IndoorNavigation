package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.activity.MainActivity;
import by.grsu.ftf.bluetooth.*;

public class BroadCast extends BroadcastReceiver {

    public static final String TAG_BROAD_CAST="BroadCast";

    private ArrayList<String> Beacon = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Beacon = intent.getStringArrayListExtra(BluetoothService.KEY_BEACON_SERVICE);
        MainActivity.mText.append(Beacon.get(0)+"\n");
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+Beacon.get(0)+ " RSSI= "+ Beacon.get(1));
        //FilterDistance.bic(Beacon.get(0),Integer.valueOf(Beacon.get(1)));
    }
}