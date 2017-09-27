package by.grsu.ftf.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
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

    private BeaconScanner scanner = new BeaconScanner();
    private Handler handler = new Handler();
    private BluetoothAdapter bluetoothAdapter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter.isEnabled()) scanner.startScan();
        runnable.run();
        runnable1.run();
        return START_STICKY;
    }

    private Runnable runnable1 = new Runnable() {
        private boolean flagEnable =true;
        @Override
        public void run() {
            if(!bluetoothAdapter.isEnabled()) {
                bluetoothEnable();
                flagEnable =false;
            }else{
                if(!flagEnable ) {
                    scanner.startScan();
                    flagEnable =true;
                }
            }
            handler.postDelayed(this,200);
        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scanner.setListener(new BeaconScanner.BeaconDetected() {
                @Override
                public void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo) {
                    if(bluetoothAdapter.isEnabled()) {
                        Intent intentSim = new Intent(FILTER_BEACON_SERVICE);
                        ArrayList<BeaconInfo> beacon = new ArrayList<>();
                        beacon.add(0, new BeaconInfo(beaconInfo.getName(), beaconInfo.getUUID(), beaconInfo.getRssi()));
                        Log.d("BroadCast", "Beacon   " + beaconInfo.getName());
                        intentSim.putParcelableArrayListExtra(KEY_BEACON_SERVICE, beacon);
                        sendBroadcast(intentSim);
                    }else{
                        scanner.stopScan();
                    }
                }
            });
        }
    };

    private void bluetoothEnable(){
        Context context=this;
        Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(btIntent);
    }

    @Override
    public void onDestroy() {
        scanner.stopScan();
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable1);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
