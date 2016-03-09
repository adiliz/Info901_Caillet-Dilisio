package model;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;


import java.util.List;


/**
 * Created by Anthony on 03/03/2016.
 */


//in list de personne
public class RumeurGraphique {

    private static final String COLOR_IGNORANT = "ignorant";
    private static final String COLOR_DIFFUSEUR = "diffuseur";
    private static final String COLOR_ETOUFFEUR = "etouffeur";
    private static final String CSS= " node {size: 20px; fill-color : grey;  stroke-mode: plain; stroke-color: #999; shadow-mode: gradient-vertical; shadow-width: 4px; shadow-color: #999, white; shadow-offset: 0px;}" +
            "node.ignorant {fill-color: rgba(128,255,0,180); }" +
            "node.diffuseur {fill-color: rgba(255,51,51,140); }" +
            "node.etouffeur {fill-color: rgba(0,128,255,180); }" +
            "edge { size: 1px; fill-color: rgba(0,0,0,50); }" +
            "edge.highlight {shape: blob; size: 3px; fill-color: rgba(255,51,51,180);}" +
            "graph {fill-mode: image-scaled-ratio-max; fill-image: url('data/mapmonde.png'); }" ;

    Rumeur rumeur;
    Graph graph;

    public RumeurGraphique(Rumeur rumeur) {
        this.rumeur = rumeur;
        this.graph = new SingleGraph("graph");

        //ADD NODES
        for(int i=0; i<this.rumeur.getNbPersonnes(); i++) {
            Node n = graph.addNode(String.valueOf(rumeur.getPersonnes().get(i).getMyId()));
            n.setAttribute("personne", rumeur.getPersonnes().get(i));
            n.setAttribute("know", false);
            n.setAttribute("ui.label", rumeur.getPersonnes().get(i).getMyId());

            int WestEst = (int) (Math.random() * 3) + 1;


            int p1, p2 = 0;

            if (WestEst > 3) {
                p1 = (int)(Math.random() * (15-1)) ;
            }else{
                p1 = (int)(Math.random() * (70-30)) + 30;
            }



            p2 = (int) (Math.random() * 25) + 10;



            n.setAttribute("xy", p1, p2);

        }

        //ADD EDGES
        for(int i=0; i<this.rumeur.getNbPersonnes(); i++) {
           Node n = graph.getNode(String.valueOf(i));
            Personne pCourante= n.getAttribute("personne");
            for (Personne v: pCourante.getVoisins()) {
                //System.out.println(pCourante.getId() + "-" + v.getId());
                Edge e = graph.getEdge(pCourante.getMyId() + "-" + v.getMyId());
                if (e == null) {
                    e = graph.getEdge(v.getMyId() + "-" + pCourante.getMyId());
                    if (e == null) {
                        graph.addEdge(pCourante.getMyId() + "-" + v.getMyId(), String.valueOf(pCourante.getMyId()), String.valueOf(v.getMyId()));
                    }
                }
            }
        }
        graph.removeAttribute("ui.stylesheet");
        graph.addAttribute("ui.stylesheet", CSS);
    }

    public void update(){
        int i = 0;

        for (Node n : this.graph.getEachNode()) {
            n.setAttribute("personne", this.rumeur.getPersonnes().get(i));
            if (i < this.rumeur.getNbPersonnes()) { //TODO: il faut modifier cette fonction car elle est degeulasse
                switch (this.rumeur.getPersonnes().get(i).getEtat()) {
                    case Etouffeur:
                        n.setAttribute("ui.class", COLOR_ETOUFFEUR);
                        break;
                    case Diffuseur:
                        n.setAttribute("ui.class", COLOR_DIFFUSEUR);
                        break;
                    case Ignorant:
                        n.setAttribute("ui.class", COLOR_IGNORANT);
                        break;
                    default:
                        break;
                }
            }
            ++i;
        }
    }

    public void lancerRumeur(Personne p) {
        List<Personne> mesVoisins = p.getVoisins();

        p.changerEtat();

        this.update();
        System.out.println(p.getEtat());
        if(p.getEtat() == Etat.Diffuseur) {
            Node node = this.graph.getNode(p.getMyId());
            node.setAttribute("know", true);
            float tauxTransmission;
            float probabilité;

            for (Personne voisin: mesVoisins) {
                tauxTransmission = (float)(Math.random());
                probabilité = (float) (Math.random());
                if(this.rumeur.interet.equals(p.getInteret())) {
                    probabilité = probabilité*2;
                }
                if(probabilité > tauxTransmission) {
                    voisin.changerEtat();
                    if(voisin.getEtat() == Etat.Diffuseur) {
                        if(! verifFinRumeur()) {
                            Edge edge = this.graph.getEdge(p.getMyId() + "-" + voisin.getMyId());
                            if (edge == null) {
                                edge = this.graph.getEdge(voisin.getMyId() + "-" + p.getMyId());
                                edge.setAttribute("ui.class", "highlight");
                                try {
                                    Thread.sleep(this.rumeur.vitesse);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                edge.clearAttributes();
                            }else {
                                edge.setAttribute("ui.class", "highlight");
                                try {
                                    Thread.sleep(this.rumeur.vitesse);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                edge.clearAttributes();
                            }
                            lancerRumeur(voisin);
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
        for (Personne p: this.rumeur.getPersonnes()) {
            if(p.getEtat() == Etat.Diffuseur) {
                return false;
            }
        }
        return true;
    }

    public void startGraph() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = this.graph.display();
        viewer.getDefaultView().resizeFrame(1440,900);

        int idPremier = (int) (Math.random() * (this.rumeur.getNbPersonnes()));
        Personne premier = this.rumeur.getPersonnes().get(idPremier);
        this.lancerRumeur(premier);
        this.update();

        if(this.verifFinRumeur()) {
            System.out.println("Fin de la propoagtion de rumeur");
            javax.swing.JOptionPane.showMessageDialog(null,"Fin de la propoagtion de rumeur");
        }

        int infected = 0;
        for (Node n : this.graph.getEachNode()) {
            if(n.getAttribute("know")){
                infected ++;
            }
        }

        System.out.println(infected + "/" + this.rumeur.getPersonnes().size() + "infected");
    }

    public static void main(String argv[]){
        RumeurGraphique rg = new RumeurGraphique(new Rumeur("Rumeur", Interet.Sport,200, 20, 4,200));
        rg.startGraph();
    }


}
