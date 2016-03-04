package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 03/03/2016.
 */
public class Rumeur {

    public List<Personne> personnes = new ArrayList<Personne>();
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
                    p = new Personne(i,Etat.Ignorant);
                    personnes.add(p);
                    break;
                case 2:
                    p = new Personne(i,Etat.Diffuseur);
                    personnes.add(p);
                    break;
                case 3:
                    p = new Personne(i,Etat.Etouffeur);
                    personnes.add(p);
                    break;
                default:
                    p = new Personne(i,Etat.Ignorant);
                    personnes.add(p);
                    break;
            }
        }
    }

    public void lierPersonnes() {
        for (Personne p: personnes) {
            int randomNbVoisins = (int)(Math.random() * (this.nbPersonnes-1));
            List<Personne> mesVoisins = p.getVoisins();
            for (int i=0 ; i < randomNbVoisins ; ++i) {
                Boolean voisinCorrect = false;
                int randomVoisin = (int)(Math.random() * (this.nbPersonnes-1));
                while(!voisinCorrect) {
                    Personne voisin = personnes.get(randomVoisin);
                    if( !mesVoisins.contains(voisin) && p!= voisin ) {
                        mesVoisins.add(voisin);
                        voisinCorrect = true;
                    }
                    else {
                        randomVoisin = (int)(Math.random() * (this.nbPersonnes-1));
                    }
                }
            }
            System.out.println("Personne " + p.getId() + "{Mes voisin : " + p.getVoisins().size()+ "}");
        }
        System.err.println("nb "+personnes.size());
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
