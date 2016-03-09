package model;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 03/03/2016.
 */
public class Rumeur {

    public List<Personne> personnes = new ArrayList<Personne>();
    public int nbPersonnes;

    public Rumeur(int nbPersonnes) throws InterruptedException {
        this.nbPersonnes = nbPersonnes;
        creerPersonnes(this.nbPersonnes);
        lierPersonnes();
    }

    public void creerPersonnes(int nbPersonnes) {
        Personne p;

        for(int i =0 ; i < this.nbPersonnes ; ++i) {
            int random = (int)(Math.random() * (4)) + 1;
            switch (random) {
                case 1:
                    p = new Personne(i,Etat.Ignorant);
                    personnes.add(p);
                    break;
                case 2:
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
            int randomNbVoisins = (int)(Math.random() * (this.nbPersonnes/20) + 1);
            List<Personne> mesVoisins = p.getVoisins();
            for (int i=0 ; i < randomNbVoisins ; ++i) {
                Boolean voisinCorrect = false;
                int randomVoisin = (int)(Math.random() * (this.nbPersonnes-1));
                while(!voisinCorrect) {
                    Personne voisin = personnes.get(randomVoisin);
                    if( !mesVoisins.contains(voisin) && p!= voisin ) {
                        mesVoisins.add(voisin);
                        //personne1<-->personne2
                        if (!personnes.get(randomVoisin).getVoisins().contains(p)) {
                            personnes.get(randomVoisin).getVoisins().add(p);
                            voisinCorrect = true;
                        }
                    }
                    else {
                        randomVoisin = (int)(Math.random() * (this.nbPersonnes-1));
                    }
                }
            }
            System.out.println("Personne " + p.getMyId() + "{Mes voisin : " + p.getVoisins().size()+ "}");
        }
        System.err.println("nb "+personnes.size());
    }

    public void lancerRumeur(Personne p, RumeurGraphique rg) throws InterruptedException {
        List<Personne> mesVoisins = p.getVoisins();

        p.changerEtat();

        rg.update();
        System.out.println(p.getEtat());
        if(p.getEtat() == Etat.Diffuseur) {
            Node node = rg.graph.getNode(p.getMyId());
            node.setAttribute("know", true);
            float tauxTransmission;
            float probabilité;

            for (Personne voisin: mesVoisins) {
                tauxTransmission = (float)(Math.random());
                probabilité = (float) (Math.random());
                if(probabilité > tauxTransmission) {
                    voisin.changerEtat();
                    if(voisin.getEtat() == Etat.Diffuseur) {
                        if(! verifFinRumeur()) {
                            Edge edge = rg.graph.getEdge(p.getMyId() + "-" + voisin.getMyId());
                            if (edge == null) {
                                edge = rg.graph.getEdge(voisin.getMyId() + "-" + p.getMyId());
                                edge.setAttribute("ui.class", "highlight");
                                Thread.sleep(200);
                                edge.clearAttributes();
                            }else {
                                edge.setAttribute("ui.class", "highlight");
                                Thread.sleep(200);
                                edge.clearAttributes();
                            }
                            lancerRumeur(voisin, rg);
                        }
                        else {
                            System.out.println("Fin de la propoagtion de rumeur");
                        }
                    }if (voisin.getEtat().equals(Etat.Etouffeur)){
                        p.setEtat(Etat.Etouffeur);
                    }
                }
            }
        }

    }

    public boolean verifFinRumeur() {
        for (Personne p: getPersonnes()) {
            if(p.getEtat() == Etat.Diffuseur) {
                return false;
            }
        }
        return true;
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
