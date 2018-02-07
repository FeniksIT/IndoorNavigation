package by.grsu.ftf.beacon;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "Beacon")
public class Beacon implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String UUID;
    private int rssi;
    private Float X;
    private Float Y;
    @Ignore
    private Float distance;

    public Beacon(){
    }

    public Beacon(Parcel parcel){
        super();
        this.name = parcel.readString();
        this.UUID = parcel.readString();
        this.rssi = parcel.readInt();
        this.X = parcel.readFloat();
        this.Y = parcel.readFloat();
        this.distance = parcel.readFloat();
    }

    public Beacon(Beacon beacon){
        this.name = beacon.getName();
        this.UUID = beacon.getUUID();
        this.rssi = beacon.getRssi();
        this.X = beacon.getX();
        this.Y = beacon.getY();
        this.distance = beacon.getDistance();
    }

    public Beacon(String name, String UUID, int rssi, Float X, Float Y) {
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
        this.X = X;
        this.Y = Y;
    }

    public Beacon(String name, String UUID, int rssi, Float X, Float Y, Float distance) {
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
        this.X = X;
        this.Y = Y;
        this.distance = distance;
    }

    public Beacon(String name, String UUID, int rssi) {
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
    }

    public static final Parcelable.Creator<Beacon> CREATOR = new Parcelable.Creator<Beacon>() {
        @Override
        public Beacon createFromParcel(Parcel source) {
            return new Beacon(source);
        }

        @Override
        public Beacon[] newArray(int size) {
            return new Beacon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rssi);
        dest.writeString(UUID);
        dest.writeString(name);
        dest.writeFloat(X);
        dest.writeFloat(Y);
        dest.writeFloat(distance);
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

    public Float getX() {
        return X;
    }

    public void setX(Float x) {
        X = x;
    }

    public Float getY() {
        return Y;
    }

    public void setY(Float y) {
        Y = y;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}