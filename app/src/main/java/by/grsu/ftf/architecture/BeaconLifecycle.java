package by.grsu.ftf.architecture;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import by.grsu.ftf.bluetooth.BluetoothService;
import by.grsu.ftf.bluetooth.BluetoothServiceCallbacks;

public class BeaconLifecycle implements LifecycleObserver{

    private Context context;

    public BeaconLifecycle(Context context){
        this.context = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        Intent intent = new Intent(context, BluetoothService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d("MainActivity", "!!!!!Start   ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy() {
        context.unbindService(mConnection);
        Log.d("MainActivity", "!!!!!Stop   ");
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothService.ServiceBeaconBinder binder = (BluetoothService.ServiceBeaconBinder) service;
            binder.setCallbacks((BluetoothServiceCallbacks) context);
            Log.d("MainActivity", "Connect   ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("MainActivity", "DisConnect   ");
        }
    };
}
