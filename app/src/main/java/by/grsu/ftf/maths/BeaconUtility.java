package by.grsu.ftf.maths;

public class BeaconUtility {

    public static float coefficientRssi(float valueRssi){
        float maxRssi = -35.0F;
        float minRssi = -90.0F;
        return Math.abs((minRssi - valueRssi)/(maxRssi - minRssi));
    }
}
