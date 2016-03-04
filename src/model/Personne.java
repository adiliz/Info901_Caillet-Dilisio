package model;

/**
 * Created by Anthony on 03/03/2016.
 */
public class Personne {
    private Etat etat;

    public Personne(Etat etat) {
        this.etat = etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Etat getEtat() {

        return etat;
    }

    public void changerEtat(Personne p) {
        if(this.etat == Etat.Ignorant) {
            this.etat = Etat.Diffuseur;
        }
    }
}
