package by.grsu.ftf.architecture;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Picture;
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
import by.grsu.ftf.navigation.Trilateration;

public class BeaconRepository implements DeleteBeaconCallbacks{

    private final BeaconDAO beaconDAO;
    private BeaconController beaconController;
    private DatabaseBeacons databaseBeacons = new DatabaseBeacons();
    private Trilateration trilateration = new Trilateration();
    private Context context;

    BeaconRepository(Context context){
        beaconDAO = DatabaseCreate.getBeaconDatabase(context).BeaconDatabase();
        databaseBeacons.getBeacondForDataBase();
        databaseBeacons.getSetingForDataBase();
        this.context=context;
        HashMap<String, String> beaconList = readFromSP();
        if (beaconList == null){
            beaconController = new BeaconController();
        }else {
            beaconController = new BeaconController(beaconDAO.gerAll(), beaconList, this);
        }
    }

    void addBeacon(Beacon beacon){
        Float x = databaseBeacons.getCoordinatesBeaconX(beacon.getName());
        Float y = databaseBeacons.getCoordinatesBeaconY(beacon.getName());
        if(beaconController.addBeacon(beacon)){
            if(databaseBeacons.getRSSIMert(beacon.getName())!=null && databaseBeacons.getCoordinates()!=null && databaseBeacons.getCoordinatesBeaconX(beacon.getName())!=null) beaconDAO.updateBeacon(beacon.getRssi(),beacon.getName(),x,y, trilateration.distance(beacon.getName(),beacon.getRssi(),x,y,databaseBeacons.getRSSIMert(beacon.getName()),databaseBeacons.getCoordinates()));
        }else{
            if(databaseBeacons.getRSSIMert(beacon.getName())!=null && databaseBeacons.getCoordinates()!=null && databaseBeacons.getCoordinatesBeaconX(beacon.getName())!=null) beaconDAO.insertBeacon(new Beacon(beacon.getName(),beacon.getUUID(),beacon.getRssi(),beacon.getX(),beacon.getY(), trilateration.distance(beacon.getName(),beacon.getRssi(), x,y,databaseBeacons.getRSSIMert(beacon.getName()),databaseBeacons.getCoordinates())));
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

    public List<Float> getSetings(){
        return databaseBeacons.getSetings();
    }

    public Picture getXML(){
        return databaseBeacons.getSVG();
    }
}
