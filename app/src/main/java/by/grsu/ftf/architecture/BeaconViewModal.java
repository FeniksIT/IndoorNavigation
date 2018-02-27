package by.grsu.ftf.architecture;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import by.grsu.ftf.beacon.Beacon;


public class BeaconViewModal extends AndroidViewModel {

    private BeaconRepository beaconRepository = new BeaconRepository(this.getApplication());
    private MutableLiveData<List<Beacon>> beacon;

    public BeaconViewModal(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Beacon>> getBeacon(){
        if(beacon == null){
            beacon = new MutableLiveData<>();
        }
        return beaconRepository.getBeacons();
    }

    public void addBeacon(Beacon beacon){
        beaconRepository.addBeacon(beacon);
        getBeacon();
    }

    public List<Float> getSetings(){
        return beaconRepository.getSetings();
    }
}
