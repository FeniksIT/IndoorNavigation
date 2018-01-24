package by.grsu.ftf.firebase;

import android.graphics.PointF;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseBeacons {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private HashMap<String, PointF> coordinatesBeacon = new HashMap<>();

    public DatabaseBeacons(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void getBeacondForDataBase(){
        databaseReference.child("Beacons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    coordinatesBeacon.put(childSnapshot.child("id").getValue().toString(),new PointF(Float.valueOf(childSnapshot.child("X").getValue().toString()),Float.valueOf(childSnapshot.child("Y").getValue().toString())));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public PointF getCoordinatesBeacon(String nameBeacon) {
        if(coordinatesBeacon.size()!=0 && coordinatesBeacon.containsKey(nameBeacon))
        return coordinatesBeacon.get(nameBeacon);
        else return new PointF(0,0);
    }

    public ArrayList<PointF> getCoordiate() {
        return new ArrayList<>(coordinatesBeacon.values());
    }
}
