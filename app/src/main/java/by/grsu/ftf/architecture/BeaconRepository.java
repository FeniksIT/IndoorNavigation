package by.grsu.ftf.architecture;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.firebase.DatabaseBeacons;
import by.grsu.ftf.maths.BeaconController;

public class BeaconRepository {

    private final BeaconDAO beaconDAO;
    private BeaconController beaconController;
    private DatabaseBeacons databaseBeacons = new DatabaseBeacons();

    public BeaconRepository(Context context){
        beaconDAO = DatabaseCreate.getBeaconDatabase(context).BeaconDatabase();
        databaseBeacons.getBeacondForDataBase();
        List<Beacon> beaconList =new ArrayList<>();
        beaconList.clear();
        for (BeaconRoom beaconRoom : beaconDAO.gerAll()){
            beaconList.add(new Beacon(beaconRoom.getName(),beaconRoom.getUUID(),beaconRoom.getRssi(),new PointF(beaconRoom.getCoordinate_X(),beaconRoom.getCoordinate_Y())));
        }
    /*    LiveData<List<Beacon>> listLiveData = Transformations.map(beaconDAO.gerAll(),  beaconRooms -> {
            List<Beacon> barList = new ArrayList<>();
            for (BeaconRoom beaconRoom: beaconRooms) {
                barList.add(new Beacon(beaconRoom.getName(),beaconRoom.getUUID(),beaconRoom.getRssi()));
            }
            return barList;
        });*/

        beaconController = new BeaconController(beaconList);
    }


    public void addBeacon(Beacon beacon){
        float x = databaseBeacons.getCoordinatesBeacon(beacon.getName()).x;
        float y = databaseBeacons.getCoordinatesBeacon(beacon.getName()).y;
        if(beaconController.addBeacon(beacon)){
            beaconDAO.updateBeacon(beacon.getRssi(),beacon.getName(),x,y);
        }else{
            beaconDAO.insertBeacon(new BeaconRoom(beacon.getName(),beacon.getUUID(),beacon.getRssi(),x,y));
        }
    }

   // public void updateBeacon(int rssi, String name){
    //    beaconDAO.updateBeacon(rssi,name);
    //}

    public void deleteBeacon(BeaconRoom beaconRoom){
        beaconDAO.deleteBeacon(beaconRoom);
    }

    public LiveData<List<BeaconRoom>> getBeacons(){
        return beaconDAO.gerBeacons();
    }

    public void deleteALL(){
        beaconDAO.deleteALL();
    }
}
