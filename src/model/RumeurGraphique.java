package model;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;


/**
 * Created by Anthony on 03/03/2016.
 */


//in list de personne
public class RumeurGraphique {

    private static final int COLOR_IGNORANT = 0;
    private static final int COLOR_DIFFUSEUR = 1;
    private static final int COLOR_ETOUFFEUR = 2;

    Rumeur rumeur;

    public RumeurGraphique(Rumeur rumeur) {
        this.rumeur = rumeur;
        Graph graph = new SingleGraph("Kiki");

        //ADD NODES
        for(int i=0; i<this.rumeur.getNbPersonnes(); i++) {
            Node n = graph.addNode(String.valueOf(rumeur.getPersonnes().get(i).getId()));
            n.setAttribute("personne", rumeur.getPersonnes().get(i));
        }

        //ADD EDGES
        for(int i=0; i<this.rumeur.getNbPersonnes(); i++) {
           Node n = graph.getNode(String.valueOf(i));
            Personne pCourante= n.getAttribute("personne");
            for (Personne v: pCourante.getVoisins()) {
                System.out.println(pCourante.getId() + "-" + v.getId());
                graph.addEdge( pCourante.getId() + "-" + v.getId(), String.valueOf(pCourante.getId()), String.valueOf(v.getId()));
            }
        }

        graph.addAttribute("ui.stylesheet", "node { fill-mode: dyn-plain; fill-color: black, red, blue; }");

        int i = 0;



        for (Node n : graph.getEachNode()){
            if (i < rumeur.getNbPersonnes()) { //TODO: il faut modifié cette fonction car elle est degeulasse
                switch (rumeur.getPersonnes().get(i).getEtat()) {
                    case Etouffeur:
                        n.setAttribute("ui.color", COLOR_ETOUFFEUR);
                        break;
                    case Diffuseur:
                        n.setAttribute("ui.color", COLOR_DIFFUSEUR);
                        break;
                    case Ignorant:
                        n.setAttribute("ui.color", COLOR_IGNORANT);
                        break;
                    default:
                        break;
                }
            }
            ++i;
        }



        graph.display();
    }

    public static void main(String[] args) throws InterruptedException {
        new RumeurGraphique(new Rumeur(100));
    }


}
