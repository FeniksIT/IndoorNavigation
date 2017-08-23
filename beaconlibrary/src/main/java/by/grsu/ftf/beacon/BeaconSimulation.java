package by.grsu.ftf.beacon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

public class BeaconSimulation extends Service{

    private final static String FILTER_BEACON_SIMULATION="BeaconSim";

    private int rssi = 0;
    private int id = 0;
    private String[] BeaconUUID;
    private String[] Beacon=  new String[2];

    Intent intentSim = new Intent(FILTER_BEACON_SIMULATION);
    Random random = new Random();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BeaconUUID=intent.getStringArrayExtra("SIM");
        simulation();
        return super.onStartCommand(intent,flags,startId);
    }

    private void simulation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(100);
                        rssi= random.nextInt(30)-80;
                        id = random.nextInt(BeaconUUID.length);
                        Beacon[0]=BeaconUUID[id];
                        Beacon[1]=String.valueOf(rssi);
                        intentSim.putExtra("BeaconUUID",Beacon);
                        sendBroadcast(intentSim);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
