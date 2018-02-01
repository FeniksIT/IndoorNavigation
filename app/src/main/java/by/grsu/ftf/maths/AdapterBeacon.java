package by.grsu.ftf.maths;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.activity.R;
import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.customView.RssiBar;

public class AdapterBeacon extends BaseAdapter {

    private List<Beacon> beaconList = new ArrayList<>();

    public AdapterBeacon(){}

    public AdapterBeacon(List<Beacon> beacon) {
        beaconList = beacon;
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
    public Beacon getItem(int position) {
        return beaconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        BeaconHolder beaconHolder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            TextView textViewIdBeacon = (TextView) view.findViewById(R.id.idBeacon);
            TextView textViewUUIDBeacon = (TextView) view.findViewById(R.id.UUIDBeacon);
            TextView textViewCoordinatesBeacon = (TextView) view.findViewById(R.id.coordinatesBeacon);
            RssiBar rssiBarCoefficient = (RssiBar) view.findViewById(R.id.RssiB);
            beaconHolder = new BeaconHolder(textViewIdBeacon, textViewUUIDBeacon, rssiBarCoefficient, textViewCoordinatesBeacon);
            view.setTag(beaconHolder);
        } else {
            beaconHolder = (BeaconHolder) view.getTag();
        }
        Beacon beacon = getItem(position);
        float coefficient = BeaconUtility.coefficientRssi(beacon.getRssi());
        beaconHolder.textViewIdBeacon.setText(beacon.getName());
        beaconHolder.textViewUUIDBeacon.setText(beacon.getUUID());
        beaconHolder.rssiBarCoefficient.setValue(coefficient);
        beaconHolder.textViewCoordinatesBeacon.setText(beacon.getCoordinates().toString());
        return view;
    }

    public void updateList(List<Beacon> beaconList){
        this.beaconList = beaconList;
    }

    private class BeaconHolder {
        private TextView textViewIdBeacon;
        private TextView textViewUUIDBeacon;
        private RssiBar rssiBarCoefficient;
        private TextView textViewCoordinatesBeacon;

        private BeaconHolder(TextView textViewIdBeacon, TextView textViewUUIDBeacon, RssiBar rssiBarCoefficient, TextView textViewCoordinatesBeacon) {
            this.textViewIdBeacon = textViewIdBeacon;
            this.textViewUUIDBeacon = textViewUUIDBeacon;
            this.rssiBarCoefficient = rssiBarCoefficient;
            this.textViewCoordinatesBeacon = textViewCoordinatesBeacon;
        }
    }
}
