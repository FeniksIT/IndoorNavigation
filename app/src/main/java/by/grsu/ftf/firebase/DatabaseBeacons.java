package by.grsu.ftf.firebase;

import android.graphics.PointF;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseBeacons {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private HashMap<String, PointF> coordinatesBeacon = new HashMap<>();
    private List<Float> setings = new ArrayList<>();
    //private HashMap<String, List<String>> stringlistHashMap = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> stringHashMapHashMap = new HashMap<>();

    public DatabaseBeacons(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Sergei");
    }

    public void getBeacondForDataBase(){
        databaseReference.child("Beacons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    coordinatesBeacon.put(childSnapshot.getKey(),new PointF(Float.valueOf(childSnapshot.child("X").getValue().toString()),Float.valueOf(childSnapshot.child("Y").getValue().toString())));
                    //String s = childSnapshot.child("RSSI").getValue().toString();
                    //String[] s1 = s.split(" ");
                    //stringlistHashMap.put(childSnapshot.getKey(),new ArrayList<>(Arrays.asList(s1)));
                    //Log.d("MainActivity", "!!!3333p   " + s1.length);
                    String s = childSnapshot.child("RSSI").getValue().toString();
                    String[] s1 = s.split(" ");
                    int k = 0;
                    String q = "";
                    HashMap<String, Integer> integerHashMap = new HashMap<>();
                    for (String aS1 : s1) {
                        k++;
                        if (k % 2 == 0) {
                            integerHashMap.put(q, Integer.valueOf(aS1));
                        } else {
                            q = aS1;
                        }
                    }
                    stringHashMapHashMap.put(childSnapshot.getKey(),integerHashMap);
                    //stringlistHashMap.put(childSnapshot.getKey(),new ArrayList<>(Arrays.asList(s1)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSetingForDataBase(){
        databaseReference.child("Setings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    Log.d("MainActivity", "PPPPP    " + childSnapshot.getValue());
                    setings.add(Float.valueOf(childSnapshot.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setValueDataBaze(String key, String value){
        databaseReference.child("Beacons").child(key).child("RSSI").setValue(value);
    }

    public Float getCoordinatesBeaconX(String nameBeacon) {
        if(coordinatesBeacon.size()!=0 && coordinatesBeacon.containsKey(nameBeacon))
        return coordinatesBeacon.get(nameBeacon).x;
        else return null;
    }

    public Float getCoordinatesBeaconY(String nameBeacon) {
        if(coordinatesBeacon.size()!=0 && coordinatesBeacon.containsKey(nameBeacon))
            return coordinatesBeacon.get(nameBeacon).y;
        else return null;
    }

    public List<Float> getSetings(){
        return setings;
    }

   /* public List<String> getRSSIMert(String name){
        //return stringlistHashMap.get(name);
    } */

    public HashMap<String, Integer> getRSSIMert(String name){
        return stringHashMapHashMap.get(name);
    }

}
