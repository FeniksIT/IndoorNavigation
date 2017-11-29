package by.grsu.ftf.bluetooth;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import by.grsu.ftf.beacon.*;

public class BluetoothService extends Service {

    private boolean flagEnableBluetooth  = true;

    private IBinder mBinder = new ServiceBeaconBinder();
    private Handler handler = new Handler();
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BluetoothServiceCallbacks bluetoothServiceCallbacks;
    private SimulatorBeacon simulatorBeacon = new SimulatorBeacon(10);
    private Beacon beacon;


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDetectedRunnable.run();
        startScan();
        super.onCreate();
    }

    private Runnable bluetoothDetectedRunnable = new Runnable() {
        @Override
        public void run() {
            if(!bluetoothAdapter.isEnabled() & flagEnableBluetooth){
                if (bluetoothServiceCallbacks != null){
                    //flagEnableBluetooth=false;
                    beacon = simulatorBeacon.getBeacon();
                    bluetoothServiceCallbacks.onReceivingBeacon(beacon,flagEnableBluetooth);
                }
            }
            if(bluetoothAdapter.isEnabled()){
                startScan();
                flagEnableBluetooth = true;
            }
            handler.postDelayed(this,200);
        }
    };

    //SDK>21
    @SuppressLint("NewApi")
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (device.getName() != null) {
                String UUID = convertASCIItoString(result.getScanRecord().getServiceUuids().toString());
                String name = device.getName();
                int rssi = result.getRssi();
                beacon = new Beacon(name, UUID, rssi);
                if (bluetoothServiceCallbacks != null){
                    bluetoothServiceCallbacks.onReceivingBeacon(beacon,flagEnableBluetooth);
                }
            }
        }
    };

    //SDK<21
    private BluetoothAdapter.LeScanCallback LeScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            new Runnable() {
                public void run() {
                    // Требуется проверка на android 4.4
                    if(device.getName()!=null) {
                        String UUID=convertASCIItoString(device.getUuids().toString());
                        String name = device.getName();
                        beacon = new Beacon(name, UUID, rssi);
                        if (bluetoothServiceCallbacks != null){
                            bluetoothServiceCallbacks.onReceivingBeacon(beacon,true);
                        }
                    }
                }
            };
        }
    };

    private void startScan(){
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bluetoothAdapter.startLeScan(LeScanCallback);
            } else {
                bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
                ScanSettings settings = new ScanSettings.Builder()
                        .setReportDelay(0)
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                bluetoothLeScanner.startScan(null, settings, scanCallback);
            }
        }
    }

    private void stopScan() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bluetoothAdapter.stopLeScan(LeScanCallback);
            } else {
                bluetoothLeScanner.stopScan(scanCallback);
                bluetoothLeScanner = null;
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        stopScan();
        handler.removeCallbacks(bluetoothDetectedRunnable);
        super.onDestroy();
    }

    private String convertASCIItoString(String  UUID){
        UUID = UUID.substring(1,UUID.length()-1);
        UUID = UUID.replaceAll("-","");
        UUID = UUID.replaceAll("00","");
        byte [] txtInByte = new byte [UUID.length() / 2];
        int j = 0;
        for (int i = 0; i < UUID.length(); i += 2) txtInByte[j++] = Byte.parseByte(UUID.substring(i, i + 2), 16);
        return String.valueOf(new StringBuffer(new String(txtInByte)).reverse());
    }

    public class ServiceBeaconBinder extends Binder {
        public void setCallbacks(BluetoothServiceCallbacks callbacks) {
            bluetoothServiceCallbacks = callbacks;
        }
    }

}
