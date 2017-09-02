package by.grsu.ftf.beacon;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.graphics.PointF;

import by.grsu.ftf.math.FilterDistance;

@TargetApi(21)
public class BeaconScanner {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BeaconDetected listener;
    private FilterDistance filterDistance= new FilterDistance();

    public interface BeaconDetected {
        void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo);
    }

    public void setListener(BeaconDetected listener) {
        this.listener = listener;
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BeaconConfig beaconConfig = new BeaconConfig();
            BluetoothDevice device = result.getDevice();
            ScanRecord scanRecord = result.getScanRecord();
            int rssi = result.getRssi();
            int txPower = scanRecord.getTxPowerLevel();
            double dist = filterDistance.distance(txPower, rssi);
            int index = beaconConfig.getIndex(device.getName());
            PointF coord = beaconConfig.getCoordinates(index);
            BeaconInfo info = new BeaconInfo(device.getName(), txPower, rssi, dist, coord);
            if (listener != null) listener.onBeaconDetected(device, info);
        }
    };

    public BeaconScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan() {
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
     //   if (bluetoothLeScanner == null) {
       // }
        ScanSettings settings = new ScanSettings.Builder()
                .setReportDelay(0)
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
        bluetoothLeScanner.startScan(null, settings, scanCallback);
    }

    public void stopScan() {
        bluetoothLeScanner.stopScan(scanCallback);
        bluetoothLeScanner = null;
    }
}
