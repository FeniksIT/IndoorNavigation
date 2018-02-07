package by.grsu.ftf.architecture;

import android.arch.persistence.room.Room;
import android.content.Context;

class DatabaseCreate {

    private static BeaconDatabase beaconDatabase;
    private static final Object OBJECT = new Object();

    synchronized static BeaconDatabase getBeaconDatabase(Context context){
        if(beaconDatabase==null){
            synchronized (OBJECT){
                if(beaconDatabase==null){
                    beaconDatabase = Room.databaseBuilder(context,BeaconDatabase.class,"Beacon6").allowMainThreadQueries().build();
                }
            }
        }
        return beaconDatabase;
    }
}
