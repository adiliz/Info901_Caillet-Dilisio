package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 03/03/2016.
 */
public class Personne {

    private int id;
    private Etat etat;
    private List<Personne> voisins = new ArrayList<Personne>();

    public Personne(int id, Etat etat) {
        this.id = id;
        this.etat = etat;
    }

    public void changerEtat(Personne p) {
        if(this.etat == Etat.Ignorant) {
            this.etat = Etat.Diffuseur;
        }
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Etat getEtat() {
        return etat;
    }

    public List<Personne> getVoisins() {
        return voisins;
    }

    public void setVoisins(List<Personne> voisins) {
        this.voisins = voisins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
