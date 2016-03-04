package model;

import java.util.List;

/**
 * Created by Anthony on 03/03/2016.
 */
public class Rumeur {

    public List<Personne> personnes;
    public int nbPersonnes;

    public Rumeur(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
        creerPersonnes(this.nbPersonnes);
        lierPersonnes();
    }

    public void creerPersonnes(int nbPersonnes) {
        Personne p;

        for(int i =0 ; i < this.nbPersonnes ; ++i) {
            int random = (int)(Math.random() * (3-1)) + 1;
            switch (random) {
                case 1:
                    p = new Personne(Etat.Ignorant);
                    personnes.add(p);
                case 2:
                    p = new Personne(Etat.Diffuseur);
                    personnes.add(p);
                case 3:
                    p = new Personne(Etat.Etouffeur);
                    personnes.add(p);
            }
        }
    }

    public void lierPersonnes() {
        for (Personne p: personnes
             ) {

        }
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes = personnes;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }
}
