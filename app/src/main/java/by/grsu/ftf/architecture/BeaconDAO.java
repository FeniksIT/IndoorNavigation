package by.grsu.ftf.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BeaconDAO {
    @Insert
    long insertBeacon(BeaconRoom beaconRoom);
    @Query("UPDATE beaconRoom SET rssi = :rssi, coordinate_X = :coordinate_X, coordinate_Y = :coordinate_Y  WHERE name = :name")
    void updateBeacon(int rssi, String name, float coordinate_X, float coordinate_Y);
    @Delete
    void deleteBeacon(BeaconRoom beaconRoom);
    @Query("select * from beaconRoom")
    LiveData<List<BeaconRoom>> gerBeacons();
    @Query("delete from beaconRoom")
    void deleteALL();
    @Query("select * from beaconRoom")
    List<BeaconRoom> gerAll();
}
