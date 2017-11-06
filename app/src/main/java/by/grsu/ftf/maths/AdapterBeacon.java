package by.grsu.ftf.maths;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.grsu.ftf.activity.R;
import by.grsu.ftf.beacon.Beacon;

public class AdapterBeacon extends BaseAdapter{

    private List<Beacon> beaconList;
    private LayoutInflater layoutInflate;

    public AdapterBeacon(Context context, List<Beacon> beacon){
        beaconList = beacon;
        layoutInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    private Beacon getBeacon(int position) {
        return getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflate.inflate(R.layout.item, parent, false);
        }
        Beacon beacon = getBeacon(position);
        String coefficient = coefficientRssi(-90.0F,-35.0F,(float)beacon.getRssi());
        ((TextView)view.findViewById(R.id.idBeacon)).setText(beacon.getName());
        ((TextView)view.findViewById(R.id.UUIDBeacon)).setText(beacon.getUUID());
        ((RssiBar)view.findViewById(R.id.RssiB)).setValue(coefficient);
        return view;
    }

    private String coefficientRssi(float minRssi, float maxRssi, float valueRssi){
        return String.valueOf(Math.abs((minRssi - valueRssi)/(maxRssi - minRssi)));
    }
}
