package by.grsu.ftf.beacon;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class BeaconSimulation extends Service {


    public static String FILTER_BEACON_SERVICE="BeaconSearch";
    public static String KEY_BEACON_SERVICE="BeaconUUID";

    Random random = new Random();
    Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable.run();
        return START_STICKY;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int rssi = random.nextInt(30) - 80;
            int id = random.nextInt(3)+1;
            Intent intentSim = new Intent(FILTER_BEACON_SERVICE);
            ArrayList<String> Beacon = new ArrayList<>();
            Beacon.add("id"+id);
            Beacon.add("Beacon "+id);
            Beacon.add(String.valueOf(rssi));
            intentSim.putStringArrayListExtra(KEY_BEACON_SERVICE,Beacon);
            sendBroadcast(intentSim);
            handler.postDelayed(this,200);
        }
    };

    @Override
    public void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
