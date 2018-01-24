package by.grsu.ftf.maths;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;


public class BeaconViewModal extends ViewModel {

    public List<Beacon> LiveDataBeacon = new ArrayList<>();

    public List<Beacon> getLiveDataBeacon() {
        return LiveDataBeacon;
    }

    public void setLiveDataBeacon(List<Beacon> liveDataBeacon) {
        LiveDataBeacon = liveDataBeacon;
    }
}
