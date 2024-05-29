/*
La classe <code>Concepteur</code> est utilisée pour permettre l'affichage de la fenêtre du programme Concepteur.
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import javax.swing.*;
import java.awt.*;

public class Concepteur {
    public static void main(String[] args) {
	
        // affichage et création de la fenetre
        SudokuGridConcepteur sudokuGrid = new SudokuGridConcepteur();
        sudokuGrid.setSize(700,600);
        sudokuGrid.setLocation(100, 100);
        sudokuGrid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sudokuGrid.setVisible(true);

        // Définir des dimensions maximales et minimales
        Dimension minSize = new Dimension(600, 500);
        Dimension maxSize = new Dimension(800, 700);

        // Appliquer les dimensions maximales et minimales à la fenêtre
        sudokuGrid.setMinimumSize(minSize);
        sudokuGrid.setMaximumSize(maxSize);
    }
}