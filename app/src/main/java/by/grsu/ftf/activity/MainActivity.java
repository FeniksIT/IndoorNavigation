package by.grsu.ftf.activity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import by.grsu.ftf.architecture.BeaconLifecycle;
import by.grsu.ftf.architecture.BeaconViewModal;
import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.bluetooth.BluetoothServiceCallbacks;
import by.grsu.ftf.maths.AdapterBeacon;

public class MainActivity extends AppCompatActivity implements BluetoothServiceCallbacks, View.OnClickListener {

    private AdapterBeacon adapter = new AdapterBeacon();
    private BeaconViewModal beaconViewModal;
    private BeaconLifecycle beaconLifecycle = new BeaconLifecycle(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView beaconListView = (ListView) findViewById(R.id.BeaconListViwe);
        Button goMapButton = (Button) findViewById(R.id.goMap);
        goMapButton.setOnClickListener(this);
        getLifecycle().addObserver(beaconLifecycle);
        beaconViewModal = ViewModelProviders.of(this).get(BeaconViewModal.class);
        LiveData<List<Beacon>> beaconData = beaconViewModal.getBeacon();
        beaconData.observe(this, new Observer<List<Beacon>>() {
            @Override
            public void onChanged(@Nullable List<Beacon> beacons) {
                adapter.updateList(beacons);
                adapter.notifyDataSetChanged();
            }
        });
        beaconListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent goMapActivity = new Intent(this,MapActivity.class);
        goMapActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goMapActivity);
    }

    @Override
    public void onReceivingBeacon(Beacon beacon, boolean flagBluetoothEnable){
         beaconViewModal.addBeacon(beacon);
        //Log.d("MainActivity", "1   ");
    }
}
