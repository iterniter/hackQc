package com.example.khalilbennani.gestiondesarbres.Logique;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by passenger on 4/2/2018.
 */

public class Localisation {
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private double latitude = 0;
    private double longitude = 0;
    private static Activity activity = null;
    private static Localisation instance = null;

    // Doit etre appele avant la methode instance au moins une fois pour pouvoir y
    // passer un contexte non null.
    public static void initialize(Activity activity) {
        Localisation.activity = activity;
    }

    // https://stackoverflow.com/questions/39701257/retrieving-location-every-5-seconds
    public static Localisation instance() throws Exception {
        if (instance == null) instance = new Localisation();
        instance.requestLocationUpdates();
        return instance;
    }

    private Localisation() throws Exception {

        if (activity == null)
            throw new Exception("Passer à la methode 'initialize' un contexte non null au préalable");

        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude( location.getLatitude() );
                longitude( location.getLongitude() );
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        };

        // https://developer.android.com/training/permissions/requesting.html#java
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            requestLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        final int MIN_DISTANCE = 0; // un rayon de proximite
        final String PROVIDER = "gps";
        final int MIN_TIME = 10000; // mettre la position a jour a chaque 10s
        locationManager.requestLocationUpdates(PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    // Fonction qui doit être appelée à l'intérieur de la méthode qui porte le même
    // et surchargée par le fragment parent de cet objet.
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == 10) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                requestLocationUpdates();
            }
        }
    }

    public synchronized void latitude(double latitude) {
        this.latitude = latitude;
    }

    public synchronized double latitude() {
        return latitude;
    }

    public synchronized double longitude() {
        return longitude;
    }

    public synchronized void longitude(double longitude) {
        this.longitude = longitude;
    }

    // https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
    // https://stackoverflow.com/questions/12000024/import-com-google-android-maps-geopoint-cannot-be-resolved
    // FIXME : Renvoyer une liste de LatLong au lieu d'un seul [moins prioritaire]
    public LatLng stringAddressToLatLng(String strAddress){
        Geocoder coder = new Geocoder(activity);
        List<Address> addresses = null;

        try {
            addresses = coder.getFromLocationName(strAddress,5);
            if (addresses == null || addresses.isEmpty()) return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Address firstResult = addresses.get(0);
        return new LatLng(
                firstResult.getLatitude(),
                firstResult.getLongitude()
        );
    }

}
