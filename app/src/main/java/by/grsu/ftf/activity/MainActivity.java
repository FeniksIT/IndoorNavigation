package by.grsu.ftf.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import by.grsu.ftf.beacon.*;

public class MainActivity extends AppCompatActivity {

    private String[] BeaconUUID = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BeaconUUID[0]="Beacon Sim 1";
        BeaconUUID[1]="Beacon Sim 2";
        BeaconUUID[2]="Beacon Sim 3";
        BeaconUUID[3]="Beacon Sim 4";
        startService(new Intent(MainActivity.this, BeaconSimulation.class).putExtra("SIM",BeaconUUID));
    }
}
