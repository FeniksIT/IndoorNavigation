package by.grsu.ftf.beacon;

public class BeaconInfo {

    private String name;
    private String UUID;
    private int rssi;

    public BeaconInfo(String name, String UUID, int rssi) {
        this.name = name;
        this.UUID = UUID;
        this.rssi = rssi;
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