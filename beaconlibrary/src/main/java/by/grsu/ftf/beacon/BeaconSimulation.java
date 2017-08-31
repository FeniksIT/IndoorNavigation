package by.grsu.ftf.beacon;


import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class BeaconSimulation extends Service {

    private static final String FILTER_BEACON_SIMULATION="BeaconSim";
    public static final String KEY_BEACON_SIMULATION="BeaconUUID";

    private ArrayList<String> Beacon = new ArrayList<>();

    Intent intentSim = new Intent(FILTER_BEACON_SIMULATION);
    Random random = new Random();
    Handler handler = new Handler();
    BeaconConfig beaconConfig = new BeaconConfig();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(runnable,100);
        return START_STICKY;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int rssi = random.nextInt(30) - 80;
            int id = random.nextInt(beaconConfig.getUUID().size());
            Beacon.add(0,beaconConfig.getUUID().get(id));
            Beacon.add(1,String.valueOf(rssi));
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
