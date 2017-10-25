package by.grsu.ftf.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.beacon.*;

public class BluetoothService extends Service {

    private boolean flagEnableBluetooth  = true;

    private final IBinder mBinder = new ServiceBeaconBinder();
    private BeaconScanner scanner = new BeaconScanner();
    private Handler handler = new Handler();
    private BluetoothAdapter bluetoothAdapter;
    private static List<BeaconInfo> beacon = new ArrayList<>();
    private BluetoothServiceCallbacks bluetoothServiceCallbacks;

    @Override
    public IBinder onBind(Intent intent) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        beaconDetectedRunnable.run();
        bluetoothDetectedRunnable.run();
        return mBinder;
    }

    private Runnable bluetoothDetectedRunnable = new Runnable() {
        @Override
        public void run() {
            if(!bluetoothAdapter.isEnabled() & flagEnableBluetooth){
                if (bluetoothServiceCallbacks != null){
                    flagEnableBluetooth=false;
                    bluetoothServiceCallbacks.beaconCallbacks(false);
                }
            }
            if(bluetoothAdapter.isEnabled()){
                scanner.startScan();
                flagEnableBluetooth = true;
            }
            handler.postDelayed(this,200);
        }
    };

    private Runnable beaconDetectedRunnable = new Runnable() {
        @Override
        public void run() {
            scanner.setListener(new BeaconScanner.BeaconDetected() {
                @Override
                public void onBeaconDetected(BeaconInfo beaconInfo) {
                    if(bluetoothAdapter.isEnabled()) {
                        sortingBeacon(beaconInfo);
                        if (bluetoothServiceCallbacks != null){
                            bluetoothServiceCallbacks.beaconCallbacks(true);
                        }
                    }
                }
            });
        }
    };

    public void setCallbacks(BluetoothServiceCallbacks callbacks) {
        bluetoothServiceCallbacks = callbacks;
    }

    public static List<BeaconInfo> getListBeacon(){
        return beacon;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        handler.removeCallbacks(beaconDetectedRunnable);
        handler.removeCallbacks(bluetoothDetectedRunnable);
        return super.onUnbind(intent);
    }

    private void sortingBeacon(BeaconInfo beaconInfo){
        BeaconInfo info;
        if (beacon.size()==0) beacon.add(beaconInfo);
        for (int i=0;i<beacon.size();i++){
            info = beacon.get(i);
            if(!info.getUUID().equals(beaconInfo.getUUID())) {
                beacon.add(beaconInfo);
            }else{
                beacon.remove(i);
                beacon.add(i,beaconInfo);
            }
        }
    }

    public class ServiceBeaconBinder extends Binder {
        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

}
