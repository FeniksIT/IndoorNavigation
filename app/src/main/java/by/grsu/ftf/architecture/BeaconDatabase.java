package by.grsu.ftf.architecture;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.grsu.ftf.beacon.Beacon;

@Database(entities = {Beacon.class}, version = 1)
public abstract class BeaconDatabase extends RoomDatabase{
    public abstract BeaconDAO BeaconDatabase();
}
