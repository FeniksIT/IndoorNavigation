package by.grsu.ftf.maths;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.io.IOException;

public class BeaconUtility {

    public static final float MAX_RSSI = -35.0F;
    public static final float MIN_RSSI = -90.0F;
    public static final float POWER = -65;


    public static float coefficientRssi(float valueRssi){
        return Math.abs((MIN_RSSI - valueRssi)/(MAX_RSSI  - MIN_RSSI));
    }

    public void bluetoothEnable(Context context){
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Важное сообщение");
            builder.setMessage("Приложению требуется включить Bluetooth на устройстве");
            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton("Включить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bluetoothAdapter.enable();
                }
            });
            builder.show();
        }
    }

    public boolean ethernetIsEnable(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e){ e.printStackTrace(); }
        return false;
    }
}
