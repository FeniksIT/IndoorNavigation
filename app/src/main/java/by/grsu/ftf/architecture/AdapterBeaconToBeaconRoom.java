package by.grsu.ftf.architecture;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;

public class AdapterBeaconToBeaconRoom {

    private BeaconRepository beaconRepository;

    private List<Beacon> beaconList = new ArrayList<>();
    private LiveData<List<Beacon>> listLiveData = new MediatorLiveData<>();

    public AdapterBeaconToBeaconRoom(Context context){
        beaconRepository = new BeaconRepository(context.getApplicationContext());
    }

   /* public LiveData<List<Beacon>> getBeacons(){
        beaconList.clear();
        for (BeaconRoom beaconRoom : beaconRepository.getBeacons().getValue()){
            beaconList.add(new Beacon(beaconRoom.getName(),beaconRoom.getUUID(),beaconRoom.getRssi(),new PointF(beaconRoom.getCoordinate_X(),beaconRoom.getCoordinate_Y())));
        }

        return
    }*/
}
