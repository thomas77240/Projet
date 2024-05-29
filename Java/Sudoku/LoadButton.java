/*
La classe <code>LoadButton</code> est utilisée pour de charger un fichier au format .gri en fonction du programme lancé (le status).
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class LoadButton implements ActionListener {

    // Attributs
    private int GRID_SIZE;
    private JTextField[][] grid;
    private int status;
    private JFrame parent;

    // Constructeur
    public LoadButton( int GRID_SIZE, JTextField[][] grid, int status) {
	this.GRID_SIZE = GRID_SIZE;
	this.grid = grid;
    this.status = status;
    }

    // Méthode pour gérer l'action du bouton
    @Override
    public void actionPerformed(ActionEvent e)  {
        // Execute l'action chargerFichier si le programme Concepteur est lancé
        if (status == 1) {
            chargerFichier();
        // Execute l'action chargerFichierNonEditable si le programme Joueur est lancé
        } else if(status == 2){
           chargerFichierNonEditable();
        }
    }

    // Méthode pour charger un fichier dans la grille
    public void chargerFichier() {
        JFileChooser choix = new JFileChooser(System.getProperty("user.dir")); // ce placer dans le repertoire ou se situe le code pour JFIleChooser
        choix.setDialogTitle("Choisir un fichier");
        choix.setFileFilter(new FileNameExtensionFilter("Fichiers gri", "gri"));
        int choixValide = choix.showOpenDialog(parent);

        if (choixValide != JFileChooser.APPROVE_OPTION) return;
        File Selection = choix.getSelectedFile();

        // efface que si l'utilisateur choisit un fichier
        effacerGrille();
        try {
            FileInputStream fich = new FileInputStream(Selection); // permet de réecrire le fichier dans la grille
            DataInputStream file = new DataInputStream(fich);
            for (int i = 0; i < 9; i++){
                int valeur = file.readInt();
                for(int j = 8; j >= 0; j--){
                    int cell = valeur % 10;
                    valeur /= 10;
                    grid[i][j].setText(cell == 0 ? "" : String.valueOf(cell));
                }
            }
            try {
                file.close(); 
            } catch (IOException e) {
                System.err.println("Erreur de fermeture");
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Le fichier séléctionné est introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ouverture du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Méthode pour charger un fichier dans la grille
    private void chargerFichierNonEditable() {
        JFileChooser choix = new JFileChooser(System.getProperty("user.dir"));
        choix.setDialogTitle("Choisir un fichier");
        choix.setFileFilter(new FileNameExtensionFilter("Fichiers gri", "gri"));
        int choixValide = choix.showOpenDialog(parent);

        if (choixValide != JFileChooser.APPROVE_OPTION) return;
        File Selection = choix.getSelectedFile();

        // efface que si l'utilisateur choisit un fichier
        effacerGrille();
        try {
            FileInputStream fich = new FileInputStream(Selection);
            DataInputStream file = new DataInputStream(fich);
            for (int i = 0; i < 9; i++) {
                int valeur = file.readInt();
                for (int j = 8; j >= 0; j--) {
                    int cell = valeur % 10;
                    valeur /= 10;
                    grid[i][j].setText(cell == 0 ? "" : String.valueOf(cell));
                    // Rendre les champs de texte non éditables
                    if (cell != 0) {
                        grid[i][j].setEditable(false);
                        grid[i][j].setEnabled(false);
                        grid[i][j].setBackground(Color.white);
                        UIManager.put("TextField.inactiveForeground", Color.BLACK);
                        SwingUtilities.updateComponentTreeUI(grid[i][j]);
                    }
                }
            }
            try {
                file.close();
            } catch (IOException e) {
                System.err.println("Erreur de fermeture");
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Le fichier sélectionné est introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ouverture du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Effacer le contenu de la grille
    public void effacerGrille() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j].setText("");
            }
        }
    }
}