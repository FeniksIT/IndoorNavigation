package by.grsu.ftf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadCast extends BroadcastReceiver {

    private final static String TAG_BROAD_CAST="BroadCast";

    private String[] Beacon;

    @Override
    public void onReceive(Context context, Intent intent) {
        Beacon = intent.getStringArrayExtra("BeaconUUID");
        Log.d(TAG_BROAD_CAST, "Бикон UUID= "+Beacon[0]+ " RSSI= "+ Beacon[1]);
    }
}