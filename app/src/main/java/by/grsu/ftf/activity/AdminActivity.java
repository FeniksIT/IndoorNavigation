package by.grsu.ftf.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.grsu.ftf.architecture.BeaconLifecycle;
import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.bluetooth.BluetoothServiceCallbacks;
import by.grsu.ftf.firebase.DatabaseBeacons;
import by.grsu.ftf.maths.AdapterAdmin;

public class AdminActivity extends AppCompatActivity implements BluetoothServiceCallbacks {

    private BeaconLifecycle beaconLifecycle = new BeaconLifecycle(this);
    private DatabaseBeacons databaseBeacons = new DatabaseBeacons();
    private AdapterAdmin adapterAdmin;
    private HashMap<String, Beacon> beaconList = new HashMap<>();
    private HashMap<String, List<Integer>> stringListHashMap = new HashMap<>();
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private List<String> strings = new ArrayList<>();
    private HashMap<String, Integer> integerHashMap = new HashMap<>();
    private TextView textViewMert0;
    private TextView textViewMert1;
    private TextView textViewMert2;
    private TextView textViewMert3;
    private TextView textID;
    private boolean flag = false;
    Date currentDate;
    Date dateReceivingBeacon;
    String stroka = "";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getLifecycle().addObserver(beaconLifecycle);
        adapterAdmin = new AdapterAdmin(new ArrayList<>(beaconList.values()));
        listView = (ListView) findViewById(R.id.List);
        Button button = (Button) findViewById(R.id.ButtonSave);
        Button button1 = (Button) findViewById(R.id.Button9);
        button.setOnClickListener(onClickListenerButtonSave);
        button1.setOnClickListener(onClickListenerButton);
        textViewMert0 = (TextView) findViewById(R.id.TextMetr0);
        textID = (TextView) findViewById(R.id.textID);
        textViewMert1 = (TextView) findViewById(R.id.TextMetr1);
        //textViewMert2 = (TextView) findViewById(R.id.TextMetr2);
        //textViewMert3 = (TextView) findViewById(R.id.TextMetr3);
        listView.setAdapter(adapterAdmin);
        listView.setOnItemClickListener(onItemClickListener);
        databaseBeacons.getBeacondForDataBase();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //strings = databaseBeacons.getRSSIMert(adapterAdmin.getItem(i).getName());
            integerHashMap = databaseBeacons.getRSSIMert(adapterAdmin.getItem(i).getName());
            textID.setText(adapterAdmin.getItem(i).getName());
         //   if (strings!=null) {
            if (integerHashMap!=null) {
                textViewMert0.setText(integerHashMap.toString());
                //textViewMert1.setText(strings.get(1));
                //textViewMert2.setText(strings.get(2));
                //textViewMert3.setText(strings.get(3));
            }else{
                textViewMert0.setText("null");
                //textViewMert1.setText("null");
                //textViewMert2.setText("null");
                //textViewMert3.setText("null");
            }
        }
    };

    private View.OnClickListener onClickListenerButtonSave = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            databaseBeacons.setValueDataBaze(textID.getText().toString(),stroka);
            textViewMert1.setText("Собранное");
        }
    };

    private View.OnClickListener onClickListenerButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            flag = true;
            try {
                textViewMert1.setText("Собранное");
                currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onReceivingBeacon(Beacon beacon, boolean flagBluetoothEnable) {
        beaconList.put(beacon.getName(),beacon);
        adapterAdmin.updateList(new ArrayList<>(beaconList.values()));
        adapterAdmin.notifyDataSetChanged();
        try {
            dateReceivingBeacon = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(flag){
            listView.setVisibility(View.INVISIBLE);
            if(dateReceivingBeacon.getTime() - currentDate.getTime() < 120000) {
                List<Integer> rssiList = stringListHashMap.get(beacon.getName());
                if (rssiList != null) {
                    rssiList.add(beacon.getRssi());
                    stringListHashMap.put(beacon.getName(), rssiList);
                    //Log.d("MainActivity", "!!!!!############   " + beacon.getName() + " ---------- " + rssiList.size());
                } else {
                    stringListHashMap.put(beacon.getName(), new ArrayList<Integer>());
                }
            }else{
                stroka = "";
                String s = "";
                int ID = 0;
                for(Map.Entry<String, List<Integer>> entry : stringListHashMap.entrySet()){
                    int summa = 0;
                    ID++;
                    for(Integer integer:entry.getValue()){
                        summa = summa + integer;
                    }
                    stroka = stroka + entry.getKey() + " " + String.valueOf(summa/entry.getValue().size()) + " ";
                }
                flag = false;
                listView.setVisibility(View.VISIBLE);
                Log.d("MainActivity", "!!!!!############" + stroka);
                textViewMert1.setText(stroka);
            }
        }
    }


}
