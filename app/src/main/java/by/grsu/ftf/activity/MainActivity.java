package by.grsu.ftf.activity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.architecture.BeaconLifecycle;
import by.grsu.ftf.architecture.BeaconRoom;
import by.grsu.ftf.architecture.BeaconViewModal;
import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.bluetooth.BluetoothServiceCallbacks;
import by.grsu.ftf.maths.AdapterBeacon;

public class MainActivity extends AppCompatActivity implements BluetoothServiceCallbacks, View.OnClickListener {

    private AdapterBeacon adapter = new AdapterBeacon();
    private BeaconViewModal beaconViewModal;
    private List<Beacon> beaconList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView beaconListView = (ListView) findViewById(R.id.BeaconListViwe);
        Button goMapButton = (Button) findViewById(R.id.goMap);
        goMapButton.setOnClickListener(this);
        getLifecycle().addObserver(new BeaconLifecycle(this));
        beaconViewModal = ViewModelProviders.of(this).get(BeaconViewModal.class);
        LiveData<List<BeaconRoom>> beaconData = beaconViewModal.getBeacon();
        beaconData.observe(this, new Observer<List<BeaconRoom>>() {
            @Override
            public void onChanged(@Nullable List<BeaconRoom> beacons) {
                beaconList.clear();
                for (BeaconRoom beaconRoom : beacons){
                    beaconList.add(new Beacon(beaconRoom.getName(),beaconRoom.getUUID(),beaconRoom.getRssi(),new PointF(beaconRoom.getCoordinate_X(),beaconRoom.getCoordinate_Y())));
                }
                adapter.updateList(beaconList);
                adapter.notifyDataSetChanged();
            }
        });
        beaconListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent goMapActivity = new Intent(this,MapActivity.class);
        startActivity(goMapActivity);
        //beaconViewModal.deleteALL();
    }

    @Override
    public void onReceivingBeacon(Beacon beacon, boolean flagBluetoothEnable){
        beaconViewModal.addBeacon(beacon);
        beaconViewModal.getBeacon();
    }
}
