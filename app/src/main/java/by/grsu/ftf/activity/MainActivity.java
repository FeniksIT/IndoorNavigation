package by.grsu.ftf.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import by.grsu.ftf.beacon.*;
import by.grsu.ftf.bluetooth.*;
import by.grsu.ftf.maths.*;

public class MainActivity extends AppCompatActivity implements BluetoothServiceCallbacks {

    private boolean connectService = false;

    private ListView beaconListViwe;
    private BluetoothService bluetoothService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconListViwe = (ListView)findViewById(R.id.BeaconListViwe);
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        //AdapterBeacon adapter = new AdapterBeacon(this, bluetoothService.getListBeacon());
        //beaconListViwe.setAdapter(adapter);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothService.ServiceBeaconBinder binder = (BluetoothService.ServiceBeaconBinder) service;
            bluetoothService = binder.getService();
            connectService = true;
            bluetoothService.setCallbacks(MainActivity.this);
            Log.d("MainActivity", "Connect   ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            connectService = false;
            Log.d("MainActivity", "DisConnect   ");
        }
    };

    @Override
    public void beaconCallbacks(List<BeaconInfo> beaconInfoList, boolean flagBluetoothEnable){
        Log.d("MainActivity", "Adapter   ");
        if(connectService & flagBluetoothEnable) {
            AdapterBeacon adapter = new AdapterBeacon(this, bluetoothService.getListBeacon());
            beaconListViwe.setAdapter(adapter);
        }else{
            bluetoothEnable();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        connectService = false;
        super.onDestroy();
    }

    private void bluetoothEnable(){
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
}
