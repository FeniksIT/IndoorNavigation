package by.grsu.ftf.architecture;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.maths.BeaconController;


public class BeaconViewModal extends AndroidViewModel {

    private BeaconController beaconController = new BeaconController();
    private MutableLiveData<List<Beacon>> beacon;

    private BeaconRepository beaconRepository = new BeaconRepository(this.getApplication());
    private MutableLiveData<List<BeaconRoom>> beaconID;

    public BeaconViewModal(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<BeaconRoom>> getBeacon(){
        if(beaconID == null){
            beaconID = new MutableLiveData<>();
        }
        //beacon.setValue(beaconController.getBeaconList());
        return beaconRepository.getBeacons();
    }

    public void addBeacon(Beacon beacon){
        beaconRepository.addBeacon(beacon);
        //beaconController.addBeacon(new Beacon(beacon,databaseBeacons.getCoordinatesBeacon(beacon.getName())));
    }

    public void deleteALL(){
        beaconRepository.deleteALL();
    }

}
