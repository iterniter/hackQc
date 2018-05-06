package com.example.khalilbennani.gestiondesarbres.Logique;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.BatteryManager;

//import com.google.android.gms.maps.model.LatLng;

/**
 * Created by passenger on 4/17/2018.
 */

public class Utilitaire {

    // https://www.spaceotechnologies.com/calculate-distance-two-gps-coordinates-google-maps-api/
    public static double distance(double  latA, double lngA, double latB, double lngB) {
        Location locationA = new Location("Location A");
        locationA.setLatitude(latA);
        locationA.setLongitude(lngA);
        Location locationB = new Location("Location B");
        locationB.setLatitude(latB);
        locationB.setLongitude(lngB);
        return locationA.distanceTo(locationB);
    }

    public static boolean egalZero(double nombre, double EPSILON)
    {
        return (nombre < EPSILON) && (-nombre < EPSILON);
    }

    public static String obtenirBattery(Context context) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if(batteryIntent != null) {
            int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level > -1 && scale > 0) {
                return Float.toString(((float) level / (float) scale) * 100.0f);
            }
        }

        return null;
    }
}
