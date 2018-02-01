package by.grsu.ftf.architecture;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "BeaconRoom")
public class BeaconRoom {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String UUID;
    private int rssi;
    private float coordinate_X;
    private float coordinate_Y;

    public BeaconRoom(String name, String UUID, int rssi, float coordinate_X, float coordinate_Y){
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
        this.coordinate_X = coordinate_X;
        this.coordinate_Y = coordinate_Y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public float getCoordinate_X() {
        return coordinate_X;
    }

    public void setCoordinate_X(float coordinate_X) {
        this.coordinate_X = coordinate_X;
    }

    public float getCoordinate_Y() {
        return coordinate_Y;
    }

    public void setCoordinate_Y(float coordinate_Y) {
        this.coordinate_Y = coordinate_Y;
    }
}
