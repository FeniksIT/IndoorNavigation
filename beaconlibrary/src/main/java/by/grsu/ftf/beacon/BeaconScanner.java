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
import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseArray;

import java.nio.ByteBuffer;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
            if(beaconConfig.getName().contains(device.getName())) {
                String UUID=convertASCIItoString(result.getScanRecord().getServiceUuids().toString());
                if(beaconConfig.getUUID().contains(UUID)) {
                    int rssi = result.getRssi();
                    int index = beaconConfig.getName().indexOf(device.getName());
                    int txPower = scanRecord.getTxPowerLevel();
                    float dist = filterDistance.distance(beaconConfig.getRssiOneMeter().get(index), rssi);
                    PointF coord = beaconConfig.getCoordinates().get(index);
                    BeaconInfo info = new BeaconInfo(device.getName(), UUID, txPower, rssi, dist, coord);
                    if (listener != null) listener.onBeaconDetected(device, info);
                }
            }
        }
    };

    public BeaconScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan() {
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
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
