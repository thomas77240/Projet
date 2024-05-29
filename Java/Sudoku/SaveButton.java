/*
La classe <code>SaveButton</code> est utilisée pour la sauvegarde de la grille au format .gri à l'aide d'un bouton.
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class SaveButton implements ActionListener {

    private int GRID_SIZE;
    private JTextField[][] grid;

    public SaveButton(int GRID_SIZE, JTextField[][] grid) {
	this.GRID_SIZE = GRID_SIZE;
	this.grid = grid;

    }
	
    @Override
    // Lorsque l'utilisateur appuie sur le bouton, il exécute l'action saveFichier();
    public void actionPerformed(ActionEvent e) {
	    saveFichier();
    }

    private void saveFichier() {
        try {
            //Ouvre le fichier de sauvegarde
            FileOutputStream fr = new FileOutputStream("GrilleNum1.gri");
            DataOutputStream fichier = new DataOutputStream(fr);
            JTextField[][] texte = grid;

            // Parcour la grille
            for (int i = 0; i < GRID_SIZE; i++) {
                StringBuilder build = new StringBuilder();
                for (int j = 0; j < GRID_SIZE; j++) {
                    String value = texte[i][j].getText();

                    // Vérifier si la case contient plus d'un chiffre
                    if (value.length() > 1) {
                        // Afficher un message d'erreur et arrêter la sauvegarde
                        JOptionPane.showMessageDialog(null, "Une case comporte plus d'un chiffre. Veuillez corriger avant de sauvegarder.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        try {
                            fichier.close();
                        } catch (IOException e) {
                            System.err.println("Erreur de fermeture");
                        }
                        return; // Arrêter la sauvegarde si une case comporte plus d'un chiffre
                    }

                    // Si la case est vide, ajouter "0" à la chaine de caractères à sauvegarder
                    if (value.isEmpty()) {
                        build.append("0");
                    } else {
                        build.append(value);
                    }
                }

                // Convertir la chaine de caractères en entier et écrire dans le fichier
                String convert = build.toString();
                int write = Integer.parseInt(convert);
                fichier.writeInt(write);
            }

            // Fermer le fichier après la sauvegarde
            try {
                fichier.close();
            } catch (IOException e) {
                System.err.println("Erreur de fermeture");
            }
            // Affiche un message de succès
            JOptionPane.showMessageDialog(null, "Grille sauvegardée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            // Affiche un message d'erreur
            JOptionPane.showMessageDialog(null, "Erreur lors de la sauvegarde de la grille.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
