package com.example.khalilbennani.gestiondesarbres.Logique;

import android.content.Context;
import android.util.Log;

import com.example.khalilbennani.gestiondesarbres.Arbre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by passenger on 4/1/2018.
 */

public class FacadeModele {

    public List<Arbre> listeArbres() { return listeArbres; }
    public void listeEndroits(List<Arbre> arbres) { listeArbres = arbres; }
    public DeviceInfo deviceInfo;
    private List<Arbre> listeArbres = new ArrayList<>();
    private FacadeModele() { deviceInfo = new DeviceInfo();}
   // private EndroitDataSource datasource;
    private static FacadeModele instance = null;

    // FacadeModele est un singleton comme dans le cadriciel de projet 2
    public static FacadeModele instance() {
        if (instance == null) instance = new FacadeModele();
        return instance;
    }

    // TODO: Maybe call this updateListeEndroits
    public void refreshListeEndroits(){
        // TODO: Delete listeEndroits : done through garbage collection

        // TODO: Read the whole database into the list
       // FacadeModele.instance().getDatasource().open(); // Does this need to be done only at startup?
        //listeEndroits = FacadeModele.instance().getDatasource().getAllEndroit();
    }

    public List<Arbre> getListArbres(){
        return listeArbres;
    }

//    public boolean hasDatabase(){
//        return datasource != null;
//    }

//    public void deleteEndroit(Endroit e){
//        datasource.deleteEndroit(e);
//        refreshListeEndroits();
//    }

//    // Comparer par rapport aux adresses
//    public boolean listeEndroitContainsByAddress(Endroit endroit){
//        for (Endroit item : listeEndroits) {
//            if (item.getAdresse().equals(endroit.getAdresse())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Comparer par rapport aux latitudes et longitudes
//    public boolean listeEndroitContainsByLocation(Endroit endroit){
//        for (Endroit item : listeEndroits)
//            if (egaux(item, endroit)) return true;
//        return false;
//    }
//
//    // Deux positions sont les meme si leurs latitudes et longitudes sont les memes
//    private static boolean egaux(Endroit endroitA, Endroit endroitB) {
//        final double precision = 0.00001;
//        boolean latsEgaux = Utilitaire.egalZero(endroitA.getLatitude() - endroitB.getLatitude(), precision);
//        boolean lngsEgaux = Utilitaire.egalZero(endroitA.getLongitude() - endroitB.getLongitude(), precision);
//        return latsEgaux && lngsEgaux;
//    }
//
//    public Endroit ajouterEndroit(String adresse, double latitude, double longitude){
//        Endroit endroit = datasource.createEndroit(
//                adresse,
//                latitude,
//                longitude
//        );
//        FacadeModele.instance().listeEndroits().add(endroit);
//        refreshListeEndroits();
//        return endroit;
//    }
//
//    public void modifierEndroitById(Endroit endroit){
//        // deleter l'endroit qui a le meme id
//
//        datasource.deleteEndroit(endroit);
//        // utiliser le nouveau create pour rajouter l'endroit dans la bd
//
//        datasource.createEndroit(endroit);
//    }
//
//    public EndroitDataSource getDatasource() {
//        return datasource;
//    }
//
//    public void setDatasource(EndroitDataSource datasource) {
//        this.datasource = datasource;
//    }
//
//    public Endroit findEndroitById(long id){
//        Endroit retval = null;
//        if(listeEndroits.size() == 0){
//            Log.i("FacadeModele", "findEndroitById(): listeEndroits has size 0");
//        }
//        for(Endroit e : listeEndroits){
//            if(e.getId() == id){
//                retval = e;
//            }
//        }
//        return retval;
//    }
//
//    public void initDatabase(Context context){
//        setDatasource(new EndroitDataSource(context));
//        getDatasource().open();
//        // Recuperer la liste des endroits de la base de donnees
//        listeEndroits = getDatasource().getAllEndroit();
//    }

}
