package by.grsu.ftf.maths;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.grsu.ftf.activity.R;
import by.grsu.ftf.beacon.BeaconInfo;

public class AdapterBeacon extends BaseAdapter{

    private List<BeaconInfo> beaconInfoList;
    private LayoutInflater layoutInflate;

    public AdapterBeacon(Context context, List<BeaconInfo> beaconInfo){
        beaconInfoList=beaconInfo;
        layoutInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return beaconInfoList.size();
    }

    @Override
    public BeaconInfo getItem(int position) {
        return beaconInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflate.inflate(R.layout.item, parent, false);
        }
        BeaconInfo beaconInfo = getBeaconInfo(position);
        ((TextView)view.findViewById(R.id.tvDescr)).setText(beaconInfo.getName()+" -- "+beaconInfo.getRssi());
        return view;
    }

    private BeaconInfo getBeaconInfo(int position) {
        return getItem(position);
    }
}
