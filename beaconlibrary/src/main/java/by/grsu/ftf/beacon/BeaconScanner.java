package by.grsu.ftf.beacon;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;

import by.grsu.ftf.math.FilterDistance;


@TargetApi(21)
public class BeaconScanner {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private OnBeaconDetectedListerner listener;
    private FilterDistance filterDistance= new FilterDistance();

    public void setListener(OnBeaconDetectedListerner listener) {
        this.listener = listener;
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            int rssi = result.getRssi();
            double dist = filterDistance.calculateAccuracy(2, (double) rssi);
            BeaconInfo info = new BeaconInfo(
                    result.getDevice().getName(),
                    result.getDevice().getAddress(),
                    rssi,
                    dist);
            if (listener != null)
                listener.onBeaconDetected(device, info);
        }
    };

    public BeaconScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan() {
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            // bluetooth not enabled?
            return;
        }
        ScanSettings settings = new ScanSettings.Builder()
                .setReportDelay(0)
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
        bluetoothLeScanner.startScan(null, settings, scanCallback);
    }

    public void endScan() {
        bluetoothLeScanner.stopScan(scanCallback);
        bluetoothLeScanner = null;
    }

    public interface OnBeaconDetectedListerner {
        void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo);
    }
}
