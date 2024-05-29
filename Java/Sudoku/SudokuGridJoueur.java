/*
La classe <code>SudokuGridJoueur</code> est utilisée pour initialisé la grille et toutes les fonction, utilisation en rapport avec celle ci (boutons, evenements, etc)
pour le programme du joueur.
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import javax.swing.*;
import java.awt.*;

public class SudokuGridJoueur extends JFrame {
    private static final int GRID_SIZE = 9; // Taille de la grille Sudoku 9x9
    private static final int REGION_SIZE = 3;
    private static final int status = 2;
    private JTextField[][] grid;

    public SudokuGridJoueur() {
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

        // Boutons
        JButton saveButton = new JButton("Sauvegarder");
        SaveButton saver = new SaveButton(GRID_SIZE,grid);
        saveButton.addActionListener(saver);

        JButton loadButton = new JButton("Charger");
        LoadButton loader = new LoadButton(GRID_SIZE, grid, status);
        loadButton.addActionListener(loader);

        JButton resolveButton = new JButton("Résoudre");
        Resolver resolver = new Resolver(grid, GRID_SIZE);
        resolveButton.addActionListener(resolver);

        // Personnaliser le style des boutons
        Color buttonColor = new Color(88, 169, 191); // Couleur bleue claire
        saveButton.setBackground(buttonColor);
        loadButton.setBackground(buttonColor);
        resolveButton.setBackground(buttonColor);
        saveButton.setForeground(Color.WHITE); // Texte en blanc
        loadButton.setForeground(Color.WHITE); // Texte en blanc
        resolveButton.setForeground(Color.WHITE); // Texte en blanc
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        saveButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);
        resolveButton.setFont(buttonFont);

        // Taille uniforme des boutons
        Dimension buttonSize = new Dimension(150, 50);
        saveButton.setPreferredSize(buttonSize);
        loadButton.setPreferredSize(buttonSize);
        resolveButton.setPreferredSize(buttonSize);

        // Espacement entre les boutons et la grille
        int spacing = 20;

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(88, 169, 191)); // Fond vert
        buttonPanel.add(Box.createVerticalStrut(spacing)); // Espacement au début
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createVerticalStrut(spacing)); // Espacement entre les boutons
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createVerticalStrut(spacing)); // Espacement entre les boutons
        buttonPanel.add(resolveButton);
        buttonPanel.add(Box.createVerticalStrut(spacing)); // Espacement à la fin

        // Ajout des panneaux à la fenetre
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.EAST);

        LoadButton charger = new LoadButton(GRID_SIZE, grid, status);
        // Appel de la méthode pour ouvrir la fenêtre de chargement de fichier
        charger.chargerFichier();
    }
}
