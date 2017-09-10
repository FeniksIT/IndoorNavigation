package by.grsu.ftf.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.beacon.BeaconInfo;
import by.grsu.ftf.beacon.BeaconScanner;

public class BluetoothService extends Service {

    public static String FILTER_BEACON_SERVICE="BeaconSearch";
    public static String KEY_BEACON_SERVICE="BeaconUUID";

    private BeaconScanner scanner = new BeaconScanner();
    private Handler handler = new Handler();

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
                    //Log.d("BroadCast","&&&&&&&&&&   "+beaconInfo.getName()+"  &&&  "+beaconInfo.getRssi());
                    Intent intentSim = new Intent(FILTER_BEACON_SERVICE);
                    ArrayList<BeaconInfo> beacon = new ArrayList<>();
                    beacon.add(0,new BeaconInfo(beaconInfo.getName(),beaconInfo.getUUID(),beaconInfo.getRssi()));
                    intentSim.putParcelableArrayListExtra(KEY_BEACON_SERVICE,beacon);
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
