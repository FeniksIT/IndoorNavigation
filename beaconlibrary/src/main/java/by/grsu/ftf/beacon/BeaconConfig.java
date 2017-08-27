package by.grsu.ftf.beacon;


import java.util.ArrayList;

public class BeaconConfig {

    private ArrayList<String> UUID = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();

    public BeaconConfig(){
        UUID.add("id1");
        UUID.add("id2");
        UUID.add("id3");
        UUID.add("id4");
        name.add("id1");
        name.add("id2");
        name.add("id3");
        name.add("id4");
    }

    public ArrayList<String> getUUID() {
        return UUID;
    }

    public ArrayList<String> getName() {
        return name;
    }
}
