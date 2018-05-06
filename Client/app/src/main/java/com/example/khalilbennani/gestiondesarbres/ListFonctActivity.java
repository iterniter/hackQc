package com.example.khalilbennani.gestiondesarbres;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListFonctActivity extends AppCompatActivity {

    public List<Arbre> listArbres= null;
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray =new JSONArray();
    JSONObject geometryJson;

    //declaration de la progresse bar
    private ProgressBar mProgressBar;
    //declaration du text de la progressBar
    private TextView mLoadingText;
    //etat de la progression
    private int mProgressStatus = 0;

    //boolean pour choisir la ville
    Boolean longueuil = false;

    //string pour l adresse de la ville a choisir
    String url;

    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fonct);


        //connecter les progressbar et le textprogressBar avec l interface graphique
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mLoadingText = (TextView) findViewById(R.id.loadingCompletTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(300);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingText.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listArbres = new ArrayList<>();


        OkHttpClient client = new OkHttpClient();

        if(longueuil){
               url = "https://www.longueuil.quebec/sites/longueuil/files/donnees_ouvertes/arbres.json";

        }else{
                url = "https://www.longueuil.quebec/sites/longueuil/files/donnees_ouvertes/arbres.json";

        }

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Erreur:","il y a un probleme");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String response_khalil = response.body().string();

                //transformer le string recu par la fonction get a un objet Json
                try {
                    jsonObject = new JSONObject(response_khalil);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    //pour le while
                    int count = 0;
                    //creation d un tableau de json
                    jsonArray  =  jsonObject.getJSONArray("features");
                    String type,geometry,porpriete;

                    //creer un tableau de json pour mettre les objet lu
                    while (count < jsonArray.length()) {

                        Arbre arbreTemp = new Arbre();

                        JSONObject Jo = jsonArray.getJSONObject(count);

                        //String pour recuperer les valeur des string qui constitues le json
                        type = Jo.getString("type");
                        arbreTemp.setType(type);
                        geometry = Jo.getString("geometry");
                        porpriete = Jo.getString("properties");

                        //Creation des objet json -- les sous tableaux du json pere
                        geometryJson = new JSONObject(geometry);
                        JSONObject porprieteJson = new JSONObject(porpriete);

                        //mettre les coordones dans un tableau de json pour pouvoir acceder au jeu de donnees
                        JSONArray tableau = geometryJson.getJSONArray("coordinates");
                        arbreTemp.setTypeGeo(geometryJson.getString("type"));

                        //mettre les donnes recuperer sous forme de objet dans un tableau de double (double parse)
                        arbreTemp.setCoord_x(Double.parseDouble(tableau.getString(0)));
                        arbreTemp.setCoord_y(Double.parseDouble(tableau.getString(1)));


                        arbreTemp.setEspece(porprieteJson.get("Espece").toString());

                        Object diametreObject = porprieteJson.get("Diametre_Tronc");
                        String diametreString = String.valueOf(diametreObject);
                        double diametre = 0;

                        try {
                            arbreTemp.setDiametre(Double.parseDouble(diametreString));

                        } catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                            arbreTemp.setDiametre(0);
                        }

                        listArbres.add(arbreTemp);
                        count++;
                    }
                    ((Arbi)getApplication()).setList(listArbres);
                    ((Arbi)getApplication()).setEstRemplie(true);
                    Button buttonSaisie = (Button) findViewById(R.id.buttonSaisir);
                    if(((Arbi)getApplication()).getEstRemplie()) {
                        //remplissage fini ?
                        //buttonSaisie.setEnabled(true);
                        runOnUiThread(new OnUIThreadRunnable(buttonSaisie));
                    }

                    // Assigner la donnee et la couleur du background au UI
                    //if (getView() != null) {

                    //}


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });


    }

    class OnUIThreadRunnable implements Runnable {
        private Button but = null;

        public OnUIThreadRunnable(Button b1) {
            this.but=b1;
        }

        @Override
        public void run() {
            but.setEnabled(true);
        }
    }


    public void onEditButton(View view){
        Intent intent = new Intent(this, listViewArbreActivity.class);
        //intent.putExtra("USER_NAME", editUsername.getText().toString());
        startActivity(intent);
    }



}
