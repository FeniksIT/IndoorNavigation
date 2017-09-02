package by.grsu.ftf.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.beacon.BeaconInfo;
import by.grsu.ftf.beacon.BeaconScanner;

public class BluetoothService extends Service {

    public static String FILTER_BEACON_SERVICE="BeaconSearch";
    public static String KEY_BEACON_SERVICE="BeaconUUID";

    Intent intentSim = new Intent(FILTER_BEACON_SERVICE);
    BeaconScanner scanner = new BeaconScanner();
    Handler handler = new Handler();
    private ArrayList<String> Beacon = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scanner.startScan();
        runnable.run();
        return START_STICKY;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scanner.setListener(new BeaconScanner.BeaconDetected() {
                @Override
                public void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo) {
                    //Log.d("BroadCast","&&&&&&&&&&   "+beaconInfo.getName()+"  &&&  "+beaconInfo.getDistance());
                    Beacon.add(0,beaconInfo.getName());
                    Beacon.add(1,String.valueOf(beaconInfo.getTxPower()));
                    Beacon.add(2,String.valueOf(beaconInfo.getRssi()));
                    Beacon.add(3,String.valueOf(beaconInfo.getDistance()));
                    Beacon.add(4,String.valueOf(beaconInfo.getCoordinates()));
                    intentSim.putStringArrayListExtra(KEY_BEACON_SERVICE,Beacon);
                    sendBroadcast(intentSim);
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        scanner.stopScan();
        handler.removeMessages(0);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
