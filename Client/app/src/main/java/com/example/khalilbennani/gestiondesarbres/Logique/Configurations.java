package com.example.khalilbennani.gestiondesarbres.Logique;

/**
 * Created by passenger on 4/1/2018.
 */

public class Configurations {
    private Configurations() {}

    private static Configurations instance = null;

    // Les configurations sont dans une classe singleton comme nous nous sommes entendus
    // dans la discussion initiale.
    public static Configurations getInstance() {
        if (instance == null)
            instance = new Configurations();
        return instance;
    }
}
