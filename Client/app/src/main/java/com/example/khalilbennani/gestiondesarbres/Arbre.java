package com.example.khalilbennani.gestiondesarbres;

/**
 * Created by mounianordine on 18-05-05.
 */

public class Arbre {

    private String type;
    private String typeGeo;
    private double coord_x;
    private double coord_y;
    private String espece;
    private double diametre;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeGeo() {
        return typeGeo;
    }

    public void setTypeGeo(String typeGeo) {
        this.typeGeo = typeGeo;
    }

    public double getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(double coord_x) {
        this.coord_x = coord_x;
    }

    public double getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(double coord_y) {
        this.coord_y = coord_y;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public double getDiametre() {
        return diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    @Override
    public String toString() {
        return this.espece + "_" + this.diametre;
    }

}
