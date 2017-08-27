package by.grsu.ftf.beacon;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class BeaconSimulation extends Service{

    private static final String FILTER_BEACON_SIMULATION="BeaconSim";
    public final String KEY_BEACON_SIMULATION="BeaconUUID";

    private int rssi = 0;
    private int id = 0;
    private String[] Beacon=  new String[2];

    Intent intentSim = new Intent(FILTER_BEACON_SIMULATION);
    Random random = new Random();
    Handler handler = new Handler();
    BeaconConfig beaconConfig = new BeaconConfig();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(runnable,100);
        return super.onStartCommand(intent,flags,startId);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rssi= random.nextInt(30)-80;
            id = random.nextInt(beaconConfig.getUUID().size());
            Beacon[0]= beaconConfig.getUUID().get(id);
            Beacon[1]=String.valueOf(rssi);
            intentSim.putExtra(KEY_BEACON_SIMULATION,Beacon);
            sendBroadcast(intentSim);
            handler.postDelayed(this,200);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
