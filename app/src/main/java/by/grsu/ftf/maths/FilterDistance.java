package by.grsu.ftf.maths;


import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class FilterDistance {

    private static List<String> BeaconUUID = new ArrayList<>();
    private static List<Integer> BeaconRSSi = new ArrayList<>();
    private static List<PointF> BeaconCoordinates = new ArrayList<>();
    private static List<Float> BeaconDistance = new ArrayList<>();


    public static PointF bic(String UUID,int RSSI, PointF coordinates, float distance){
        if(!BeaconUUID.contains(UUID)) {
            BeaconUUID.add(UUID);
            BeaconRSSi.add(RSSI);
            BeaconCoordinates.add(coordinates);
            BeaconDistance.add(distance);
        }else{
            int index=BeaconUUID.indexOf(UUID);
            if(BeaconRSSi.get(index)<RSSI){
                BeaconRSSi.set(index,RSSI);
                BeaconDistance.set(index,distance);
            }
        }
        if(BeaconUUID.size()==3){
            PointF coord = trilaterate(BeaconCoordinates.get(0),BeaconCoordinates.get(1),BeaconCoordinates.get(2),BeaconDistance.get(0),BeaconDistance.get(1),BeaconDistance.get(2));
            BeaconUUID.clear();
            BeaconRSSi.clear();
            BeaconDistance.clear();
            BeaconCoordinates.clear();
            return coord;
        }else{
            return null;
        }
    }

    public float distance(int txPower, int rssi) {
        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return (float)(Math.pow(ratio,10));
        }
        else {
            return (float)((0.89976)*Math.pow(ratio,7.7095) + 0.111);
        }
    }

    private static PointF trilaterate(PointF a, PointF b, PointF c, float distA, float distB, float distC) {
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
