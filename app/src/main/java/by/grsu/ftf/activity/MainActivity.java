package by.grsu.ftf.activity;


import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.bluetooth.BluetoothService;
import by.grsu.ftf.bluetooth.BluetoothServiceCallbacks;
import by.grsu.ftf.firebase.DatabaseBeacons;
import by.grsu.ftf.maths.AdapterBeacon;
import by.grsu.ftf.maths.BeaconController;
import by.grsu.ftf.maths.BeaconViewModal;

public class MainActivity extends AppCompatActivity implements BluetoothServiceCallbacks, View.OnClickListener {

    private boolean connectService = false;
    private boolean flagEhternet = true;

    private AdapterBeacon adapter;
    private BeaconController beaconController;
    private List<Beacon> listBeacon;
    private BeaconViewModal beaconViewModal;
    private DatabaseBeacons databaseBeacons = new DatabaseBeacons();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView beaconListView = (ListView) findViewById(R.id.BeaconListViwe);
        Button goMapButton = (Button) findViewById(R.id.goMap);
        goMapButton.setOnClickListener(this);
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        beaconViewModal = ViewModelProviders.of(this).get(BeaconViewModal.class);
        if (beaconViewModal.getLiveDataBeacon() == null){
            beaconController = new BeaconController();
        }else{
            beaconController = new BeaconController(beaconViewModal.getLiveDataBeacon());
        }
        listBeacon = beaconController.getBeaconList();
        adapter = new AdapterBeacon(listBeacon);
        beaconListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent goMapActivity = new Intent(this,MapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mylist", databaseBeacons.getCoordiate());
        goMapActivity.putExtras(bundle);
        startActivity(goMapActivity);
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
    public void onReceivingBeacon(Beacon beacons, boolean flagBluetoothEnable){
        Beacon beacon;
       // if (ethernetIsEnable() && flagEhternet){
            flagEhternet = false;
            databaseBeacons.getBeacondForDataBase();
            Log.d("MainActivity", " ethernet connect   ");
       // }else if (!ethernetIsEnable()){
      //      flagEhternet = true;
      //  }
        beacon = new Beacon(beacons,databaseBeacons.getCoordinatesBeacon(beacons.getName()));
        beaconController.addBeacon(beacon);
        listBeacon = beaconController.getBeaconList();
        adapter.updateList(listBeacon);
        adapter.notifyDataSetChanged();
       // if(connectService & flagBluetoothEnable) {
       // }else{
       //     //bluetoothEnable();
       // }
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        connectService = false;
        beaconViewModal.setLiveDataBeacon(listBeacon);
        Log.d("MainActivity", " onDestroy MAI   ");
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

    private boolean ethernetIsEnable(){
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
