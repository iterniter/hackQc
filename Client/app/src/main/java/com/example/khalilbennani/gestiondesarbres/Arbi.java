package com.example.khalilbennani.gestiondesarbres;

import android.app.Application;

import java.util.List;

public class Arbi extends Application{

    private List<Arbre> listArbre;
    private boolean estRempli = false;

    public void setList(java.util.List<Arbre> list)
    {
        listArbre= list;
    }

    public List<Arbre> getList()
    {
        return listArbre;
    }

    public void setEstRemplie(boolean remplie)
    {
        estRempli= remplie;
    }

    public boolean getEstRemplie()
    {
        return estRempli;
    }

}
