package by.grsu.ftf.beacon;

import android.os.Parcel;
import android.os.Parcelable;

public class BeaconInfo implements Parcelable{

    private String name;
    private String UUID;
    private int rssi;

    public BeaconInfo(String name, String UUID, int rssi) {
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
    }

    private BeaconInfo(Parcel in) {
        name = in.readString();
        UUID = in.readString();
        rssi = in.readInt();
    }

    public static final Creator<BeaconInfo> CREATOR = new Creator<BeaconInfo>() {
        @Override
        public BeaconInfo createFromParcel(Parcel in) {
            return new BeaconInfo(in);
        }

        @Override
        public BeaconInfo[] newArray(int size) {
            return new BeaconInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUUID() {
        return UUID;
    }

    public int getRssi() {
        return rssi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(UUID);
        dest.writeInt(rssi);
    }
}