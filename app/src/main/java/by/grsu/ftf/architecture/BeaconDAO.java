package by.grsu.ftf.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import by.grsu.ftf.beacon.Beacon;

@Dao
public interface BeaconDAO {
    @Insert
    void insertBeacon(Beacon beacon);
    @Query("UPDATE beacon SET rssi = :rssi, X = :coordinate_X, Y = :coordinate_Y, distance =:distance  WHERE name = :name")
    void updateBeacon(int rssi, String name, Float coordinate_X, Float coordinate_Y, Float distance);
    @Query("delete from beacon where name = :name")
    void deleteBeacon(String name);
    @Query("select * from beacon")
    LiveData<List<Beacon>> gerBeacons();
    @Query("delete from beacon")
    void deleteALL();
    @Query("select * from beacon")
    List<Beacon> gerAll();
}
