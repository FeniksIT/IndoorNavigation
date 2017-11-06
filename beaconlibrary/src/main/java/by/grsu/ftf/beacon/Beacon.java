package by.grsu.ftf.beacon;

import android.os.Parcel;
import android.os.Parcelable;

public class Beacon implements Parcelable {

    private String name;
    private String UUID;
    private int rssi;

    public Beacon(){
    }

    public Beacon(Parcel parcel){
        super();
        this.name = parcel.readString();
        this.UUID = parcel.readString();
        this.rssi = parcel.readInt();
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
    }

    public String getName() {
        return name;
    }
    public String getUUID() {
        return UUID;
    }
    public int getRssi() {
        return rssi;
    }

}