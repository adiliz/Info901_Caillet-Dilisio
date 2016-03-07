package model;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;


/**
 * Created by Anthony on 03/03/2016.
 */


//in list de personne
public class RumeurGraphique {

    private static final String COLOR_IGNORANT = "ignorant";
    private static final String COLOR_DIFFUSEUR = "diffuseur";
    private static final String COLOR_ETOUFFEUR = "etouffeur";
    private static final String CSS= " node {fill-color : grey;}" +
            "node.ignorant {fill-color: green; }" +
            "node.diffuseur {fill-color: red; }" +
            "node.etouffeur {fill-color: blue; }" ;

    Rumeur rumeur;
    Graph graph;

    public RumeurGraphique(Rumeur rumeur) {
        this.rumeur = rumeur;
        this.graph = new SingleGraph("graph");

        //ADD NODES
        for(int i=0; i<this.rumeur.getNbPersonnes(); i++) {
            Node n = graph.addNode(String.valueOf(rumeur.getPersonnes().get(i).getMyId()));
            n.setAttribute("personne", rumeur.getPersonnes().get(i));
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
        graph.addAttribute("ui.stylesheet", CSS);
    }

    public void update(){
        int i = 0;

        for (Node n : graph.getEachNode()) {
            n.setAttribute("personne", rumeur.getPersonnes().get(i));
            if (i < rumeur.getNbPersonnes()) { //TODO: il faut modifié cette fonction car elle est degeulasse
                switch (rumeur.getPersonnes().get(i).getEtat()) {
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

    public static void main(String[] args) throws InterruptedException {
        RumeurGraphique rg = new RumeurGraphique(new Rumeur(100));
        rg.graph.display();

        int idPremier = (int) (Math.random() * (rg.rumeur.getNbPersonnes()));
        Personne premier = rg.rumeur.getPersonnes().get(idPremier);
        rg.rumeur.lancerRumeur(premier, rg);
        rg.update();
        if(rg.rumeur.verifFinRumeur()) {
            System.out.println("Fin de la propoagtion de rumeur");
            javax.swing.JOptionPane.showMessageDialog(null,"Fin de la propoagtion de rumeur");
        }


    }


}
