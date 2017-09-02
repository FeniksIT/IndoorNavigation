package by.grsu.ftf.beacon;


import android.graphics.PointF;

import java.util.ArrayList;

public class BeaconConfig {

    private ArrayList<String> UUID = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<PointF> coordinates = new ArrayList<>();
    private ArrayList<Integer> RssiOneMeter = new ArrayList<>();

    public BeaconConfig(){
        UUID.add("111111111");
        UUID.add("222222222");
        UUID.add("333333333");
        UUID.add("444444444");
        name.add("id1");
        name.add("id2");
        name.add("id3");
        name.add("id4");
        coordinates.add(new PointF(12,12));
        coordinates.add(new PointF(13,13));
        coordinates.add(new PointF(14,14));
        coordinates.add(new PointF(15,15));
        RssiOneMeter.add(-35);
        RssiOneMeter.add(-46);
        RssiOneMeter.add(-45);
        RssiOneMeter.add(-51);
    }

    public int getIndex(String namelist){
        return name.indexOf(namelist);
    }

    public void setUUID(ArrayList<String> UUID) {
        this.UUID = UUID;
    }

    public ArrayList<String> getUUID() {
        return UUID;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<PointF> getCoordinates() {
        return coordinates;
    }

    public PointF getCoordinates(int index) {
        return coordinates.get(index);
    }

    public void setCoordinates(ArrayList<PointF> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Integer> getRssiOneMeter() {
        return RssiOneMeter;
    }
}

