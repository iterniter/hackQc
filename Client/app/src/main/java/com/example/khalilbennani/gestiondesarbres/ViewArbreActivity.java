package com.example.khalilbennani.gestiondesarbres;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewArbreActivity extends AppCompatActivity {

    double coord_x;
    double coord_y;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_arbre);
        Bundle b= getIntent().getExtras();

        TextView typeFeat=findViewById(R.id.typeFeatures);
        TextView typeGeo=findViewById(R.id.typeGeometry);
        TextView textCoord=findViewById(R.id.textCoordinates);
        TextView textEspece=findViewById(R.id.textEspece);
        TextView textDiametre=findViewById(R.id.textDiametre);

        typeFeat.setText(b.getString("typeFeature"));
        typeGeo.setText(b.getString("typeGeometry"));
        textCoord.setText("("+String.valueOf(b.getDouble("textCoordinates_x"))+","+String.valueOf(b.getDouble("textCoordinates_y"))+")");
        coord_x = b.getDouble("textCoordinates_x");
        coord_y = b.getDouble("textCoordinates_y");
        textEspece.setText(b.getString("textEspece"));
        textDiametre.setText(""+b.getDouble("textDiametre"+"")+" Cm");

        query = b.getString("textEspece");
    }

    public void OnViewPositionClick (View v)
    {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("lat",coord_x);
        intent.putExtra("lng", coord_y);
        startActivity(intent);
    }

    public void changerDePage(View v){
        Intent intent = new Intent(this, ActionActivity.class);
        startActivity(intent);
    }


    public void vueGoogle(View v){
        /*
        Uri uri = Uri.parse("https://www.google.com/search?q="+query);
        Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(gSearchIntent);
        */

        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query); // query contains search string
        startActivity(intent);

    }


}

