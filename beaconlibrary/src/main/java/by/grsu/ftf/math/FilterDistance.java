package by.grsu.ftf.math;


import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

import by.grsu.ftf.beacon.BeaconConfig;

public class FilterDistance {

    private static ArrayList<String> BeaconUUID = new ArrayList<>();
    private static ArrayList<Integer> BeaconRSSi = new ArrayList<>();


    public static void bic(String UUID,int RSSI){
        BeaconConfig beaconConfig = new BeaconConfig();
        ArrayList<String> Beacon = new ArrayList<>();
        if(!BeaconUUID.contains(UUID)) {
            BeaconUUID.add(UUID);
            BeaconRSSi.add(RSSI);
        }else{
            int index=BeaconUUID.indexOf(UUID);
            if(BeaconRSSi.get(index)<RSSI) BeaconRSSi.set(index,RSSI);
        }
        if(BeaconUUID.size()==3){
            Beacon=beaconConfig.getName();
            Log.d("BroadCast",String.valueOf(BeaconUUID.size()));
        }
    }

    public double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }
        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }

    private PointF trilaterate(PointF a, PointF b, PointF c, float distA, float distB, float distC) {
        float P1[] = { a.x, a.y, 0 };
        float P2[] = { b.x, b.y, 0 };
        float P3[] = { c.x, c.y, 0 };
        float ex[] = { 0, 0, 0 };
        float P2P1 = 0;
        for (int i = 0; i < 3; i++) {
            P2P1 += Math.pow(P2[i] - P1[i], 2);
        }
        for (int i = 0; i < 3; i++) {
            ex[i] = (float) ((P2[i] - P1[i]) / Math.sqrt(P2P1));
        }
        float p3p1[] = { 0, 0, 0 };
        for (int i = 0; i < 3; i++) {
            p3p1[i] = P3[i] - P1[i];
        }
        float ivar = 0;
        for (int i = 0; i < 3; i++) {
            ivar += (ex[i] * p3p1[i]);
        }
        float p3p1i = 0;
        for (int  i = 0; i < 3; i++) {
            p3p1i += Math.pow(P3[i] - P1[i] - ex[i] * ivar, 2);
        }
        float ey[] = { 0, 0, 0};
        for (int i = 0; i < 3; i++) {
            ey[i] = (float) ((P3[i] - P1[i] - ex[i] * ivar) / Math.sqrt(p3p1i));
        }
        float ez[] = { 0, 0, 0 };
        float d = (float) Math.sqrt(P2P1);
        float jvar = 0;
        for (int i = 0; i < 3; i++) {
            jvar += (ey[i] * p3p1[i]);
        }
        float x = (float) ((Math.pow(distA, 2) - Math.pow(distB, 2) + Math.pow(d, 2)) / (2 * d));
        float y = (float) (((Math.pow(distA, 2) - Math.pow(distC, 2) + Math.pow(ivar, 2)
                + Math.pow(jvar, 2)) / (2 * jvar)) - ((ivar / jvar) * x));
        float z = (float) Math.sqrt(Math.pow(distA, 2) - Math.pow(x, 2) - Math.pow(y, 2));
        if (Float.isNaN(z)) z = 0;
        float triPt[] = { 0, 0, 0 };
        for (int i = 0; i < 3; i++) {
            triPt[i] =  P1[i] + ex[i] * x + ey[i] * y + ez[i] * z;
        }
        float lon = triPt[0];
        float lat = triPt[1];
        return new PointF(lon, lat);
    }
}
