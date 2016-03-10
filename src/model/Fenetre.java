package model;

import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Anthony on 09/03/2016.
 */
public class Fenetre extends JFrame {

    public RumeurGraphique rg;

    private JLabel nomLabel, typeLabel, nbPersonnesLabel, nbVoisinsMaxLabel, proportionLabel,vitesseLabel, icon;
    private JComboBox type;
    private JTextField nom, nbPersonnes, nbVoisinsMax, proportion, vitesse;

    public Fenetre(){
        this.setSize(650, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setTitle("Paramètres de la rumeur");
        initFrame();
        this.setVisible(true);
    }

    private void initFrame(){
        //Icône
        icon = new JLabel(new ImageIcon("src/images/icone.jpg"));
        JPanel panIcon = new JPanel();
        panIcon.setBackground(Color.white);
        panIcon.setLayout(new BorderLayout());
        panIcon.add(icon);

        //Le nom
        JPanel panNom = new JPanel();
        panNom.setBackground(Color.white);
        panNom.setPreferredSize(new Dimension(250, 60));
        nom = new JTextField();
        nom.setPreferredSize(new Dimension(100, 25));
        panNom.setBorder(BorderFactory.createTitledBorder("Nom de la rumeur"));
        nomLabel = new JLabel("Saisir un nom :");
        panNom.add(nomLabel);
        panNom.add(nom);

        //Le type
        JPanel panType = new JPanel();
        panType.setBackground(Color.white);
        panType.setPreferredSize(new Dimension(250, 60));
        panType.setBorder(BorderFactory.createTitledBorder("Type de la rumeur"));
        type = new JComboBox();
        type.addItem("Sport");
        type.addItem("IT");
        type.addItem("Physique");
        typeLabel = new JLabel("Type : ");
        panType.add(typeLabel);
        panType.add(type);

        //Le nombre de personnes
        JPanel panNbPersonnes = new JPanel();
        panNbPersonnes.setBackground(Color.white);
        panNbPersonnes.setBorder(BorderFactory.createTitledBorder("Nombre de personnes"));
        panNbPersonnes.setPreferredSize(new Dimension(250, 75));
        nbPersonnes = new JTextField();
        nbPersonnes.setPreferredSize(new Dimension(100, 25));
        panNbPersonnes.setBorder(BorderFactory.createTitledBorder("Nombre de personnes"));
        nbPersonnesLabel = new JLabel("Saisir un nombre :");
        panNbPersonnes.add(nbPersonnesLabel);
        panNbPersonnes.add(nbPersonnes);

        //Le nombre de voisins max
        JPanel panVoisins = new JPanel();
        panVoisins.setBackground(Color.white);
        panVoisins.setPreferredSize(new Dimension(250, 75));
        panVoisins.setBorder(BorderFactory.createTitledBorder("Nombre de voisins max"));
        nbVoisinsMax = new JTextField();
        nbVoisinsMax.setPreferredSize(new Dimension(100, 25));
        panVoisins.setBorder(BorderFactory.createTitledBorder("Nombre de voisins max"));
        nbVoisinsMaxLabel = new JLabel("Saisir un nombre :");
        panVoisins.add(nbVoisinsMaxLabel);
        panVoisins.add(nbVoisinsMax);

        //La proportion
        JPanel panProportion = new JPanel();
        panProportion.setBackground(Color.white);
        panProportion.setPreferredSize(new Dimension(250, 75));
        panProportion.setBorder(BorderFactory.createTitledBorder("La proportion Ignorant/Etouffeur"));
        proportion = new JTextField();
        proportion.setPreferredSize(new Dimension(100, 25));
        panProportion.setBorder(BorderFactory.createTitledBorder("La proportion Ignorant/Etouffeur"));
        proportionLabel = new JLabel("Saisir un nombre :");
        panProportion.add(proportionLabel);
        panProportion.add(proportion);

        //La vitesse
        JPanel panVitesse = new JPanel();
        panVitesse.setBackground(Color.white);
        panVitesse.setPreferredSize(new Dimension(250, 75));
        panVitesse.setBorder(BorderFactory.createTitledBorder("La vitesse de propagation (en milli)"));
        vitesse = new JTextField();
        vitesse.setPreferredSize(new Dimension(100, 25));
        panVitesse.setBorder(BorderFactory.createTitledBorder("La vitesse de propagation (en milli)"));
        vitesseLabel = new JLabel("Saisir un nombre :");
        panVitesse.add(vitesseLabel);
        panVitesse.add(vitesse);

        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panNom);
        content.add(panType);
        content.add(panNbPersonnes);
        content.add(panVoisins);
        content.add(panProportion);
        content.add(panVitesse);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");

        okBouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                rg = new RumeurGraphique(new Rumeur(nom.getText(), Interet.valueOf(type.getSelectedItem().toString()),Integer.valueOf(nbPersonnes.getText()), Integer.valueOf(nbVoisinsMax.getText()), Integer.valueOf(proportion.getText()),Integer.valueOf(vitesse.getText())));
                rg.canLunch = true;
            }
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        control.add(okBouton);
        control.add(cancelBouton);

        this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }

    public static void main(String argv[]){


    }
}
