package by.grsu.ftf.beacon;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

import by.grsu.ftf.math.FilterDistance;

public class BeaconSimulation extends Service {

    public static String FILTER_BEACON_SIMULATION="BeaconSim";
    public static String KEY_BEACON_SIMULATION="BeaconUUID";

    private ArrayList<String> Beacon = new ArrayList<>();

    Intent intentSim = new Intent(FILTER_BEACON_SIMULATION);
    Random random = new Random();
    Handler handler = new Handler();
    BeaconConfig beaconConfig = new BeaconConfig();
    FilterDistance filterDistance= new FilterDistance();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable.run();
        return START_STICKY;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int rssi = random.nextInt(30) - 80;
            int id = random.nextInt(beaconConfig.getUUID().size());
            Beacon.add(0,beaconConfig.getName().get(id));
            Beacon.add(1,beaconConfig.getUUID().get(id));
            Beacon.add(2,"-2");
            Beacon.add(3,String.valueOf(rssi));
            Beacon.add(4,String.valueOf(filterDistance.distance(beaconConfig.getRssiOneMeter().get(id), rssi)));
            Beacon.add(5,String.valueOf(beaconConfig.getCoordinates().get(id).x));
            Beacon.add(6,String.valueOf(beaconConfig.getCoordinates().get(id).y));
            intentSim.putStringArrayListExtra(KEY_BEACON_SIMULATION,Beacon);
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
