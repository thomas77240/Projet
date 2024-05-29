/*
La classe <code>Resolver</code> est utilisée pour permettre la résolution automatique de la grille avec le temps qu'il faut pour sa résolution automatique.
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;


public class Resolver implements ActionListener {

	// Attributs
    private JTextField[][] grid;
    private int GRID_SIZE;

	// Constructeur
    public Resolver(JTextField[][] grid, int GRID_SIZE) {
	this.grid = grid;
	this.GRID_SIZE = GRID_SIZE;
    }

	// Lorsque l'utilisateur appuie sur le bouton, il exécute l'action resolution();
    @Override
    public void actionPerformed(ActionEvent e) {
	resolution();
    }

	// Méthode resolution pour résoudre la grille automatiquement
    private void resolution() {

		// Mesurer le temps de départ
		long depart = System.nanoTime();

		// Liste pour stocker les cases vides
		ArrayList<JTextField> case_vide = new ArrayList<JTextField>();

		// Boucle pour résoudre la grille
		do {
			for (int row = 0; row < this.GRID_SIZE; row++) {
				for (int col = 0; col < this.GRID_SIZE; col++) {
					if (grid[row][col].getText().isEmpty()) {
						if (!case_vide.contains(grid[row][col])) {
							case_vide.add(grid[row][col]);
						}

						// Création de la liste de chiffre possible
						ArrayList<String> chiffre_possible = new  ArrayList<String>();

						for (int i = 1; i <= GRID_SIZE; i++) {
							chiffre_possible.add(""+i);
						}
						
						// Recherche des chiffres present sur la ligne
						for (int col2 = 0; col2 < this.GRID_SIZE; col2++) {
							if (!grid[row][col2].getText().isEmpty()) {
								chiffre_possible.remove(""+grid[row][col2].getText());
							}
						}

						// Recherche des chiffres sur la colonne
						for (int row2 = 0; row2 < this.GRID_SIZE; row2++) {
							if (!grid[row2][col].getText().isEmpty()) {
								chiffre_possible.remove(""+grid[row2][col].getText());
							}
						}

						// Recherche des chiffres dans la région
						int rowregion = row/3*3;
						int colregion = col/3*3;
						for (int row2 = rowregion; row2 < rowregion + 3; row2++) {
							for (int col2 = colregion; col2 < colregion + 3; col2++) {
								if (!grid[row2][col2].getText().isEmpty()) {
									chiffre_possible.remove(""+grid[row2][col2].getText());
								}
							}
						}
						// Si un seul chiffre possible reste, le placer dans la case vide
						if (chiffre_possible.size() == 1) {
							grid[row][col].setText(chiffre_possible.getFirst());
							case_vide.remove(grid[row][col]);
						}	
					}
				}
			}
		} while (!case_vide.isEmpty());
		// Calculer la durée de résolution
		double durée = (((System.nanoTime()) - depart )* Math.pow(10,-9));
		DecimalFormat df = new DecimalFormat("0.00");


		// Afficher un message de fin de résolution avec le temps obtenu
		JOptionPane.showMessageDialog(null, "Grille terminé en " + df.format(durée) + " secondes", "Résolution automatique", JOptionPane.INFORMATION_MESSAGE);

    }
}
