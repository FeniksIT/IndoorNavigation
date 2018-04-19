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

import java.util.List;

import by.grsu.ftf.architecture.BeaconLifecycle;
import by.grsu.ftf.architecture.BeaconViewModal;
import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.bluetooth.BluetoothServiceCallbacks;
import by.grsu.ftf.customView.MapView;
import by.grsu.ftf.navigation.Trilateration;

public class MapActivity extends AppCompatActivity implements BluetoothServiceCallbacks, View.OnClickListener {

    private MapView map;
    private BeaconViewModal beaconViewModal;
    private Trilateration trilateration = new Trilateration();
    private PointF user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        map = (MapView) findViewById(R.id.ImageMap);
        Button goMapButton = (Button) findViewById(R.id.goActivity);
        goMapButton.setOnClickListener(this);
        getLifecycle().addObserver(new BeaconLifecycle(this));
        beaconViewModal = ViewModelProviders.of(this).get(BeaconViewModal.class);
        LiveData<List<Beacon>> beaconData = beaconViewModal.getBeacon();
        beaconData.observe(this, new Observer<List<Beacon>>() {
            @Override
            public void onChanged(@Nullable List<Beacon> beacons) {
                assert beacons != null;
                for(Beacon beacon : beacons){
                    if (beacon.getY() !=null && beacon.getX() !=null && beaconViewModal.getSetings().size()>1) {
                        user = trilateration.getCoordinateUser(beacon);
                        if (user!=null){
                            map.setCordinateUser(user);
                        }
                    }
                }
                map.setPointFS(beacons,beaconViewModal.getSetings());
                map.setSvgPicture(beaconViewModal.getXML());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent goMapActivity = new Intent(this,MainActivity.class);
        goMapActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goMapActivity);
    }

    @Override
    public void onReceivingBeacon(Beacon beacon, boolean flagBluetoothEnable) {
        beaconViewModal.addBeacon(beacon);
    }
}
