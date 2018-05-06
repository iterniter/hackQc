package com.example.khalilbennani.gestiondesarbres;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

public class listViewArbreActivity extends AppCompatActivity {

    private List<Arbre> listDesArbre= null;
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray =new JSONArray();
    JSONObject geometryJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_arbre);
        listDesArbre= new ArrayList<>();
        listDesArbre= ((Arbi)getApplication()).getList();

    }

    @Override
    public void onStart() {
        super.onStart();
        // la taille du tableau est zero donc comment voulez vous le remplir ?
        loadArbreListView();
    }


    void loadArbreListView(){

        final ListView listview = findViewById(R.id.list);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<Arbre> adapter = (ArrayAdapter<Arbre>) listview.getAdapter();
                Arbre arbre = (Arbre) listview.getAdapter().getItem(position);
                startViewEndroitActivity(arbre);
            }
        });
        ArrayAdapter<Arbre> adapter = new ArrayAdapter<Arbre>(
                this,
                android.R.layout.simple_list_item_1,listDesArbre);

        listview.setAdapter(adapter);
    }

    //TODO...
    private void startViewEndroitActivity(Arbre e){
        Intent intent = new Intent(this, ViewArbreActivity.class);
        intent.putExtra("typeFeature", e.getType()); // <-- TODO : get this to work
        intent.putExtra("typeGeometry", e.getTypeGeo());
        intent.putExtra("textCoordinates_x", e.getCoord_x());
        intent.putExtra("textCoordinates_y", e.getCoord_y());
        intent.putExtra("textEspece", e.getEspece());
        intent.putExtra("textDiametre", e.getDiametre());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(com.example.mounia.tp1.Logique.FacadeModele.instance().hasDatabase()){
//            loadEndroitListView();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
