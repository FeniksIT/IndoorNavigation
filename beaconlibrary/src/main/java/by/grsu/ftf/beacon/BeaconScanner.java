package by.grsu.ftf.beacon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;

import by.grsu.ftf.math.FilterDistance;

public class BeaconScanner extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BeaconDetected listener;
    private FilterDistance filterDistance= new FilterDistance();
    BeaconConfig beaconConfig = new BeaconConfig();

    public interface BeaconDetected {
        void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo);
    }

    public void setListener(BeaconDetected listener) {
        this.listener = listener;
    }

    //SDK>21
    @SuppressLint("NewApi")
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            ScanRecord scanRecord = result.getScanRecord();
            if(beaconConfig.getName().contains(device.getName())) {
                String UUID=convertASCIItoString(result.getScanRecord().getServiceUuids().toString());
                if(beaconConfig.getUUID().contains(UUID)) {
                    String name = device.getName();
                    int rssi = result.getRssi();
                    int index = beaconConfig.getName().indexOf(name);
                    int txPower = scanRecord.getTxPowerLevel();
                    float distance = filterDistance.distance(beaconConfig.getRssiOneMeter().get(index), rssi);
                    PointF coordinates = beaconConfig.getCoordinates().get(index);
                    BeaconInfo info = new BeaconInfo(name, UUID, txPower, rssi, distance, coordinates);
                    if (listener != null) listener.onBeaconDetected(device, info);
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
                    if(beaconConfig.getName().contains(device.getName())) {
                        String UUID=convertASCIItoString(device.getUuids().toString());
                        if(beaconConfig.getUUID().contains(UUID)) {
                            String name = device.getName();
                            int index = beaconConfig.getName().indexOf(name);
                            float distance = filterDistance.distance(beaconConfig.getRssiOneMeter().get(index), rssi);
                            PointF coordinates = beaconConfig.getCoordinates().get(index);
                            BeaconInfo info = new BeaconInfo(name, UUID, 0, rssi, distance, coordinates);
                            if (listener != null) listener.onBeaconDetected(device, info);
                        }
                    }
                }
            };
        }
    };

    public BeaconScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan(){
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            //Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
            bluetoothAdapter.enable();
        }
        if(Build.VERSION.SDK_INT < 21){
            bluetoothAdapter.startLeScan(LeScanCallback);
        }else {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            ScanSettings settings = new ScanSettings.Builder()
                    .setReportDelay(0)
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
            bluetoothLeScanner.startScan(null, settings, scanCallback);
        }

    }

    public void stopScan() {
        if(Build.VERSION.SDK_INT < 21) {
            bluetoothAdapter.stopLeScan(LeScanCallback);
        }else{
            bluetoothLeScanner.stopScan(scanCallback);
            bluetoothLeScanner = null;
        }
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
}
