package by.grsu.ftf.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import by.grsu.ftf.BroadCast.BroadCast;
import by.grsu.ftf.beacon.BeaconSimulation;
import by.grsu.ftf.bluetooth.*;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    final String COORDINATES_USER = "COORDINATES_USER";

    private SharedPreferences sPref;
    public static ListView beaconListViwe;
    public static TextView coordinatesTextViwe;
    public static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect peripherals.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    }
                });
                builder.show();
            }
        }
        beaconListViwe = (ListView)findViewById(R.id.BeaconListViwe);
        coordinatesTextViwe = (TextView) findViewById(R.id.coordinatesUser);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BroadCast.getBeacon());
        beaconListViwe.setAdapter(adapter);
        loadCoordinatesUser();
    }

    @Override
    protected void onDestroy() {
        //stopService(new Intent(MainActivity.this, BluetoothService.class));
        saveCoordinatesUser();
        super.onDestroy();
    }

    public void onClickStart(View view) {
        //startService(new Intent(MainActivity.this, BeaconSimulation.class)); //Эмулятор биконов
        startService(new Intent(MainActivity.this, BluetoothService.class)); // Поиск биконов
    }

    public void onClickStop(View view) {
        //stopService(new Intent(MainActivity.this, BeaconSimulation.class));
        stopService(new Intent(MainActivity.this, BluetoothService.class));
    }

    void saveCoordinatesUser() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(COORDINATES_USER, coordinatesTextViwe.getText().toString());
        ed.commit();
    }

    void loadCoordinatesUser() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(COORDINATES_USER,"");
        coordinatesTextViwe.setText(savedText);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
            }
        }
    }
}
