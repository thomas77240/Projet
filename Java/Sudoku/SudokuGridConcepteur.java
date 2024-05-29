/*
La classe <code>SudokuGridConcepteur</code> est utilisée pour initialisé la grille et toutes les fonction, utilisation en rapport avec celle ci (boutons, evenements, etc)
pour le programme du concepteur de la grille du sudoku.
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import javax.swing.*;
import java.awt.*;

public class SudokuGridConcepteur extends JFrame {
    private static final int GRID_SIZE = 9; // Taille de la grille Sudoku 9x9
    private static final int REGION_SIZE = 3;
    private static final int status = 1;
    private JTextField[][] grid;

    public SudokuGridConcepteur() {
        // Panneau pour la grille Sudoku.
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // Utiliser GridLayout
        gridPanel.setBackground(new Color(88, 169, 191)); // Fond vert

        // Initialiser la grille
        grid = new JTextField[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = new JTextField();
		
                TextFilter filtre = new TextFilter(grid[i][j], GRID_SIZE, grid, i, j, status);
                
                grid[i][j].addKeyListener(filtre);
                grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                grid[i][j].setFont(new Font("Verdana", Font.BOLD,40));
                gridPanel.add(grid[i][j]);

                int top = 1;
                int left = 1;
                int bottom = 1;
                int right = 1;

                // Vérifier si la case est sur le bord de la région horizontalement
                if ((j + 1) % REGION_SIZE == 0 && j != GRID_SIZE - 1) {
                    right = 5; // Ajouter une bordure plus épaisse à droite
                }

                // Vérifier si la case est sur le bord de la région verticalement
                if ((i + 1) % REGION_SIZE == 0 && i != GRID_SIZE - 1) {
                    bottom = 5; // Ajouter une bordure plus épaisse en bas
                }

                // Appliquer la bordure à la case
                grid[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
            }
        }

        // Panneau pour les boutons
        JPanel bouton = new JPanel();
        bouton.setBackground(new Color(88, 169, 191)); 
        bouton.setPreferredSize(new Dimension(150, 0)); // Espace pour les boutons
        
        // Bouton pour sauvegarder la grille
        JButton save = new JButton("Sauvegarder");
	    SaveButton saver = new SaveButton(GRID_SIZE,grid);
        save.addActionListener(saver);

        // Bouton pour chargé la grille
        JButton load = new JButton("Charger");
	    LoadButton loader = new LoadButton(GRID_SIZE, grid, status);
        load.addActionListener(loader);
    
        bouton.add(load);
        bouton.add(save);
	

        // Ajout des panneaux à la fenetre
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(bouton, BorderLayout.EAST);
    }

   
    
}
