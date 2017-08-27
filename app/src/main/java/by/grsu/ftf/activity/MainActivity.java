package by.grsu.ftf.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import by.grsu.ftf.beacon.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, BeaconSimulation.class));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(MainActivity.this, BeaconSimulation.class));
        super.onDestroy();
    }
}
