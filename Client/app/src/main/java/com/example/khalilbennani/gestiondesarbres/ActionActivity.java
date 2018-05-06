package com.example.khalilbennani.gestiondesarbres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
    }

    public void confirmer(View v){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
