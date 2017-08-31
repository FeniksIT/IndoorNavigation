package by.grsu.ftf.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.TextView;

import by.grsu.ftf.beacon.*;
import by.grsu.ftf.bluetooth.*;

public class MainActivity extends AppCompatActivity {

    public static TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.Text12);
        startService(new Intent(MainActivity.this, BeaconSimulation.class));
    }

    public void onClick(View view) {
        stopService(new Intent(this, BeaconSimulation.class));
        Log.d("BroadCast","работает кнопка");
        BluetoothControl.Bluetoothcheck();
    }

}
