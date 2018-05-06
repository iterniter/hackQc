package com.example.khalilbennani.gestiondesarbres;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.khalilbennani.gestiondesarbres.Utilitaires.Preferences;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private CheckBox rememberMe;
    private CheckBox rememberPassword;
    private Button buttonLogin;
    private EditText editPassword;
    private EditText editUsername;

    String response_authentification;

    //private static int SPLASH_TIME_OUT = 4000;


    JSONObject jsonObject = new JSONObject();
    JSONObject idJson = new JSONObject();

    JSONArray jsonArray =new JSONArray();
    JSONObject geometryJson;

    //creation d un tableau de Double pour mettre les coordones prises du json
    //a fin de les comparés avec les coordonees de depart
    String coordonesX, coordonesY ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Preferences.init(getBaseContext());

        editPassword = (EditText) findViewById(R.id.password);
        editUsername = (EditText) findViewById(R.id.username);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        rememberPassword = (CheckBox) findViewById(R.id.rememberPassword);


        String username = Preferences.get(Preferences.USERNAME);
        editUsername.setText(username);
        String password = Preferences.get(Preferences.PASSWORD);
        editPassword.setText(password);
        rememberPassword.setChecked(Boolean.parseBoolean(Preferences.get(Preferences.REMEMBER_PASSWORD)));
        rememberMe.setChecked(Boolean.parseBoolean(Preferences.get(Preferences.REMEMBER_ME)));





        // LOGIN
        buttonLogin = findViewById(R.id.login);
        //code on button click

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(final View view) {

                final String username = editUsername.getText().toString().trim();
                final String password = editPassword.getText().toString().trim();

                if (rememberMe.isChecked()) {
                    Preferences.set(Preferences.USERNAME, username);
                    Preferences.set(Preferences.REMEMBER_ME, "true");
                } else {
                    Preferences.set(Preferences.USERNAME, "");
                    Preferences.set(Preferences.REMEMBER_ME, "false");
                }
                if (rememberPassword.isChecked()) {
                    Preferences.set(Preferences.PASSWORD, password);
                    Preferences.set(Preferences.REMEMBER_PASSWORD, "true");
                } else {
                    Preferences.set(Preferences.PASSWORD, "");
                    Preferences.set(Preferences.REMEMBER_PASSWORD, "false");
                }


                //verification par id

                OkHttpClient clientAuthentification = new OkHttpClient();
                String url = "https://jsonplaceholder.typicode.com/posts?userId=1&id=1";

                final Request requestUrl = new Request.Builder()
                        .url(url)
                        .build();
                // faire le call back pour la reponse du serveur

                clientAuthentification.newCall(requestUrl).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.i("Connection Echoue:", "mauvais mot de passe ou nom d utilisateur");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        response_authentification = response.body().string();

                        Log.i("Omaaar:", response_authentification);

                        try {
                            idJson = new JSONObject(response_authentification);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Boolean test = false;

                        try {
                            test = idJson.getString("id").equals(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (username.equals("Ben") && password.equals("ben")) {
                                Log.i("connection:", "Etablie");

                                    //si tous les tests === reussis
                                    onStartClick(view);

                            }




                    }


                });


                //bypass
                if (username.equals("bypass")) {
                    onStartClick(view);
                }


            }

        });
    }

    /*
   * Pour ouvrir le layout de mode de reception
   */
    public void onStartClick(View view){
        Intent intent = new Intent(this, ChoixDeLaVille.class);
        startActivity(intent);
    }

}
