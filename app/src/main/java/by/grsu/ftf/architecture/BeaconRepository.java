package by.grsu.ftf.architecture;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.firebase.DatabaseBeacons;
import by.grsu.ftf.maths.BeaconController;
import by.grsu.ftf.maths.DeleteBeaconCallbacks;

public class BeaconRepository implements DeleteBeaconCallbacks{

    private final BeaconDAO beaconDAO;
    private BeaconController beaconController;
    private DatabaseBeacons databaseBeacons = new DatabaseBeacons();
    private Context context;

    BeaconRepository(Context context){
        beaconDAO = DatabaseCreate.getBeaconDatabase(context).BeaconDatabase();
        databaseBeacons.getBeacondForDataBase();
        this.context=context;
        HashMap<String, String> beaconList = readFromSP();
        beaconController = new BeaconController(beaconDAO.gerAll(),beaconList,this);
    }

    void addBeacon(Beacon beacon){
        Float x = databaseBeacons.getCoordinatesBeaconX(beacon.getName());
        Float y = databaseBeacons.getCoordinatesBeaconY(beacon.getName());
        if(beaconController.addBeacon(beacon)){
            beaconDAO.updateBeacon(beacon.getRssi(),beacon.getName(),x,y);
        }else{
            beaconDAO.insertBeacon(new Beacon(beacon.getName(),beacon.getUUID(),beacon.getRssi(),x,y));
        }
        insertToSP(beaconController.getDateReceiving());
    }

    @Override
    public void onDeliteOldBeacon(String name) {
        beaconDAO.deleteBeacon(name);
    }

    LiveData<List<Beacon>> getBeacons(){
        return beaconDAO.gerBeacons();
    }

    void deleteALL(){
        beaconDAO.deleteALL();
    }

    private void insertToSP(Map<String, String> jsonMap) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(jsonMap);
        editor.putString("HashMap",json);
        editor.apply();
    }

    private HashMap<String, String> readFromSP(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("HashMap","");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
