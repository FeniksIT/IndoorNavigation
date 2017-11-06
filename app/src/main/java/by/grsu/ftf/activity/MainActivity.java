package by.grsu.ftf.activity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.bluetooth.*;
import by.grsu.ftf.maths.*;

public class MainActivity extends AppCompatActivity implements BluetoothServiceCallbacks {

    private final String KEY_SAVE_LIST_BEACON = "listBecon";

    private boolean connectService = false;

    private AdapterBeacon adapter;
    private List<Beacon > beaconList = new ArrayList<>();
    private CollectionsBeacon collectionsBeacon = new CollectionsBeacon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            accessLocationPermission();
        }
        setContentView(R.layout.activity_main);
        ListView beaconListViwe = (ListView) findViewById(R.id.BeaconListViwe);
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if (savedInstanceState == null) {
            beaconList = collectionsBeacon.getListBeacon();
        }else{
            beaconList = savedInstanceState.getParcelableArrayList(KEY_SAVE_LIST_BEACON);
        }
        adapter = new AdapterBeacon(this, beaconList);
        beaconListViwe.setAdapter(adapter);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BluetoothService.ServiceBeaconBinder binder = (BluetoothService.ServiceBeaconBinder) service;
            binder.setCallbacks(MainActivity.this);
            connectService = true;
            Log.d("MainActivity", "Connect   ");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            connectService = false;
            Log.d("MainActivity", "DisConnect   ");
        }
    };

    @Override
    public void beaconCallbacks(Beacon beacon, boolean flagBluetoothEnable){
        if(connectService & flagBluetoothEnable) {
            collectionsBeacon.addBeacon(beacon);
            adapter.notifyDataSetChanged();
        }else{
            bluetoothEnable();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        connectService = false;
        Log.d("MainActivity", " onDestroy MAI   ");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_SAVE_LIST_BEACON, (ArrayList<? extends Parcelable>) beaconList);
    }
    @TargetApi(23)
    private void accessLocationPermission() {
        int accessCoarseLocation = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation   = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listRequestPermission = new ArrayList<>();

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listRequestPermission.isEmpty()) {
            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
            requestPermissions(strRequestPermission, 1);
        }
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
