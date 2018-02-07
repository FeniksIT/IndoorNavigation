package by.grsu.ftf.navigation;

import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.grsu.ftf.beacon.Beacon;
import by.grsu.ftf.maths.BeaconUtility;

public class Trilateration {

    public static HashMap<String, Beacon> beaconHashMap = new HashMap<>();

    public static PointF getCoordinateUser(Beacon beacon){
        beaconHashMap.put(beacon.getName(),beacon);
        if(beaconHashMap.size()==3){
            List<Beacon> beaconList = new ArrayList<>(beaconHashMap.values());
            PointF coord = trilaterate(beaconList.get(0).getX(), beaconList.get(0).getY(), beaconList.get(1).getX(), beaconList.get(1).getY(), beaconList.get(2).getX(), beaconList.get(2).getY(), beaconList.get(0).getDistance(), beaconList.get(1).getDistance(), beaconList.get(2).getDistance());
            beaconHashMap.clear();
            return coord;
        }else{
            return null;
        }
    }

    public static Float distance(float rssi){
        float d = (float) Math.pow(10, (rssi - BeaconUtility.POWER) / ((float) -10 * 3.2));
        return d;
    }

    public static PointF trilaterate(Float ax, Float ay, Float bx, Float by, Float cx, Float cy, float distA, float distB, float distC) {
        float P1[] = {ax, ay, 0};
        float P2[] = {bx, by, 0};
        float P3[] = {cx, cy, 0};
        float ex[] = {0, 0, 0};
        float P2P1 = 0;
        for (int i = 0; i < 3; i++) {
            P2P1 += Math.pow(P2[i] - P1[i], 2);
        }
        for (int i = 0; i < 3; i++) {
            ex[i] = (float) ((P2[i] - P1[i]) / Math.sqrt(P2P1));
        }
        float p3p1[] = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            p3p1[i] = P3[i] - P1[i];
        }
        float ivar = 0;
        for (int i = 0; i < 3; i++) {
            ivar += (ex[i] * p3p1[i]);
        }
        float p3p1i = 0;
        for (int i = 0; i < 3; i++) {
            p3p1i += Math.pow(P3[i] - P1[i] - ex[i] * ivar, 2);
        }
        float ey[] = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            ey[i] = (float) ((P3[i] - P1[i] - ex[i] * ivar) / Math.sqrt(p3p1i));
        }
        float ez[] = {0, 0, 0};
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
        float triPt[] = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            triPt[i] = P1[i] + ex[i] * x + ey[i] * y + ez[i] * z;
        }
        float lon = triPt[0];
        float lat = triPt[1];

        if (Float.isNaN(lon) && Float.isNaN(lat)) return null;
        Log.d("MainActivity", "2   " + lon);
        return new PointF(lon, lat);

    }
}
