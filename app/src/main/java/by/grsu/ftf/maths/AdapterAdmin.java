package by.grsu.ftf.maths;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.grsu.ftf.activity.R;
import by.grsu.ftf.beacon.Beacon;

public class AdapterAdmin extends BaseAdapter {

    private List<Beacon> beaconList;

    public AdapterAdmin(List<Beacon> beaconList){
        this.beaconList = beaconList;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beaconList.size();
    }

    @Override
    public Beacon getItem(int i) {
        return beaconList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void updateList(List<Beacon> beaconList){
        this.beaconList = beaconList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewText = view;
        BeaconHolder beaconHolder;
        if (viewText == null) {
            viewText = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin, viewGroup, false);
            TextView textViewIdBeacon = (TextView) viewText.findViewById(R.id.TextAdmin);
            beaconHolder = new AdapterAdmin.BeaconHolder(textViewIdBeacon);
            viewText.setTag(beaconHolder);
        } else {
            beaconHolder = (AdapterAdmin.BeaconHolder) viewText.getTag();
        }
        Beacon beacon = getItem(i);
        beaconHolder.textViewIdBeacon.setText(beacon.getName());
        return viewText;
    }

    private class BeaconHolder {
        private TextView textViewIdBeacon;

        private BeaconHolder(TextView textViewIdBeacon) {
            this.textViewIdBeacon = textViewIdBeacon;
        }
    }
}
