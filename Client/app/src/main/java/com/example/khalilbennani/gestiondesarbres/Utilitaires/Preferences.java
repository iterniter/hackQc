package com.example.khalilbennani.gestiondesarbres.Utilitaires;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {


    public static SharedPreferences preferences = null;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REMEMBER_ME = "rememberMe";
    public static final String REMEMBER_PASSWORD = "rememberPassword";


    public static void init(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void  set(String name, String content){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, content);
        editor.commit();
    }

    public static String  get(String name){
        return preferences.getString(name,null);
    }
}
