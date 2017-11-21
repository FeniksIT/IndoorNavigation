package by.grsu.ftf.beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatorBeacon {

    private int kolBeacon;

    private Random random = new Random();
    private Beacon beacon;

    public SimulatorBeacon(){

    }

    public SimulatorBeacon(int kolBeacon){
        this.kolBeacon = kolBeacon;
    }

    public Beacon getBeacon() {
        int  numberBeacon = random.nextInt(kolBeacon)+1;
        String id = "id"+numberBeacon;
        String UUID = "Beacon " + numberBeacon;
        int rssi = random.nextInt(30) - 80;
        return new Beacon(id,UUID,rssi);
    }
}