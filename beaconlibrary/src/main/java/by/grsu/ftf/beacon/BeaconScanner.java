package by.grsu.ftf.beacon;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
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
public class BeaconScanner extends Activity {

    private final static int REQUEST_ENABLE_BT = 1;

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
                    String name = device.getName();
                    int rssi = result.getRssi();
                    int index = beaconConfig.getName().indexOf(name);
                    int txPower = scanRecord.getTxPowerLevel();
                    float distance = filterDistance.distance(beaconConfig.getRssiOneMeter().get(index), rssi);
                    PointF coordinates = beaconConfig.getCoordinates().get(index);
                    BeaconInfo info = new BeaconInfo(name, UUID, txPower, rssi, distance, coordinates);
                    if (listener != null) listener.onBeaconDetected(device, info);
                    //if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) startScan();
                }
            }
        }
    };

    public BeaconScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan() {
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            //Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
            bluetoothAdapter.enable();
        }
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
