package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 03/03/2016.
 */
public class Personne {

    private int id;
    private Etat etat;
    private Interet interet;
    private List<Personne> voisins = new ArrayList<Personne>();


    public Personne(int id, Etat etat) {
        this.id = id;
        this.etat = etat;

        int random = (int)(Math.random() * (3)) + 1;
        switch (random) {
            case 1:
                this.interet = Interet.Sport;
                break;
            case 2:
                this.interet = Interet.IT;
                break;
            case 3:
                this.interet = Interet.Physique;
                break;
            default:
                this.interet = Interet.Sport;
                break;
        }
    }


    public void changerEtat() {
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

    public int getMyId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Interet getInteret() {
        return interet;
    }

    public void setInteret(Interet interet) {
        this.interet = interet;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "etat=" + etat +
                ", id=" + id +
                '}';
    }
}
