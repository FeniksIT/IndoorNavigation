package by.grsu.ftf.maths;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import java.io.IOException;

public class BeaconUtility {

    public static float coefficientRssi(float valueRssi){
        float maxRssi = -35.0F;
        float minRssi = -90.0F;
        return Math.abs((minRssi - valueRssi)/(maxRssi - minRssi));
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

    interface Confirmer<T> {
        boolean test(T thingy);
    }
    @MainThread
    public static <X> LiveData<X> filter(@NonNull LiveData<X> source,
                                  @NonNull final Confirmer<X> confirmer) {
        final MediatorLiveData<X> result=new MediatorLiveData<>();

        result.addSource(source, new Observer<X>() {
            @Override
            public void onChanged(@Nullable X x) {
                if (confirmer.test(x)) {
                    result.setValue(x);
                }
            }
        });

        return(result);
    }
}
