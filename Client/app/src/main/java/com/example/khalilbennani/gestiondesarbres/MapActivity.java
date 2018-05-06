package com.example.khalilbennani.gestiondesarbres;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalilbennani.gestiondesarbres.Logique.Localisation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap = null;
    private Arbre arbre;

    double coord_x;
    double coord_y;
    LatLng LAT_LNG_ARBRE;


    // Un pointeur vers un service qui nous facilite un peu les fonctionnalites de localisation
    private Localisation localisation = null;

    // Fragment de la google map
    SupportMapFragment mapFragment = null;

    /**
     * Cette méthode est appelée lors de la création de l'activité
     * @param savedInstanceState
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.addmap);
        mapFragment.getMapAsync(this);

        Bundle b= getIntent().getExtras();
        Intent intent = getIntent();
        coord_x = b.getDouble("lng");
        coord_y = b.getDouble("lat");
        LAT_LNG_ARBRE = new LatLng(coord_x, coord_y);

    }

    /**
     * Manipule la map lorsque celle-ci devient disponible.
     * Ce callback est appelé lorsque la map est prête à être utilisée.
     * C'est ici que les marqueurs sont ajoutés.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        placerMarqueurArbre();
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(LAT_LNG_ARBRE, 10.0f) );

        // Initialiser le service de localisation
        //Localisation.initialize(this);
//        try { localisation = Localisation.instance(); }
       // catch (Exception e) { e.printStackTrace(); }

    }

    private void placerMarqueurArbre() {
        MarkerOptions markerArbre = new MarkerOptions().position(LAT_LNG_ARBRE).title("Position").snippet("ARBRE");
        markerArbre.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerArbre);
    }


}
