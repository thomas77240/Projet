/*
La classe <code>TextFilter</code> est utilisée pour gerer toutes les fonctions lié à la saisie des touches
@version 1.1
@author Thomas Follea, Yann Keraudren
*/

import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Font;

public class TextFilter extends KeyAdapter {

    private JTextField Text;
    private int GRID_SIZE;
    private JTextField[][] grid;
    private int row;
    private int col;
    private ArrayList<Integer> bad_numbers = new ArrayList<Integer>();
    private boolean errorDetected = false;
    private int status;

    public TextFilter (JTextField t,int GRID_SIZE, JTextField[][] grid, int i, int j, int status) {

	this.Text = t;
	this.GRID_SIZE = GRID_SIZE;
	this.status = status;
	this.grid = grid;
	this.row = i;
	this.col = j;
    }
    

    @Override

    public void keyTyped(KeyEvent e) {
	
		char chiffre = e.getKeyChar();
		int taille = this.Text.getText().length();
		boolean end_game = true;

		// Si la longueur du texte est égale à 3 après l'ajout du chiffre, définir la taille de la police à 20
		if (taille > 1) {
			Text.setFont(new Font("Verdana", Font.BOLD, 20));
		} else {
			Text.setFont(new Font("Verdana", Font.BOLD, 40));
		}

		// Ignorer l'événement si le caractère entré n'est pas un chiffre entre 1 et 9 ou la touche de retour arrière
		if ( ((chiffre < '1') || (chiffre > '9')) && (chiffre != KeyEvent.VK_BACK_SPACE)) {
			e.consume(); // ignorer l'événement
		}

		// Vérifier le statut (1 == concepteur,2 == joueur)
		if (status == 2){
			if (!errorDetected) {
				if ((chiffre >= '1') && (chiffre <= '9')) {
					GrilleValide(chiffre);
				}
			} else {
				e.consume(); // empêcher la saisie de chiffres lorsque qu'une erreur est détectée
			}

			// permet de bloquer la saisie de doublon dans la même case
			if (!Text.getText().isEmpty() && (chiffre != KeyEvent.VK_BACK_SPACE)) {
				String existingText = Text.getText();
				char[] existingChars = existingText.toCharArray();
				for (char c : existingChars) {
					if (chiffre == c) {
						e.consume(); // empêcher la réécriture du même chiffre
					return;
					}
				}
			}

			// Réinitialiser les couleurs si la touche de retour arrière est pressée lors d'une erreur
			if ((!bad_numbers.isEmpty()) && (chiffre == KeyEvent.VK_BACK_SPACE)) {
				Text.setBackground(Color.white);
			while (!bad_numbers.isEmpty()) {
				grid[bad_numbers.removeFirst()][bad_numbers.removeFirst()].setBackground(Color.white);
			}
			errorDetected = false; // Réinitialiser errorDetected après avoir corrigé les erreurs
			}

			// Défini la taille maiximum par case
			if ( taille >= 4)  {
				e.consume();
			}

			//Test si toutes les cases sont remplies et affiche un message de fin
			if (!errorDetected) {
				for (int row = 0; row < GRID_SIZE; row++) {
					for (int col = 0; col < GRID_SIZE; col++) {

					String value = grid[row][col].getText();
					// Vérifier si la case contient plus d'un chiffre
					if (value.length() > 1) {
						end_game = false;
					}

					if (grid[row][col].getText().isEmpty()) {
						end_game = false;
					}
					}
				}
				if (end_game) {
					JOptionPane.showMessageDialog(null, "Bravo vous avez terminé la grille !", "Fin du jeu", JOptionPane.INFORMATION_MESSAGE);
				}
			}
				
		} else if(status == 1){
		    // Taille maximum par case
		    if ( taille >= 1 )  {
			e.consume();
		    }else{

		    
			// Traite la saisie pour savoir si le chiffer peut être posé
			if (((chiffre >= '1') && (chiffre <= '9')) && (chiffre != KeyEvent.VK_ENTER)) {
			    GrilleValide(chiffre);
			}

			// Réinitialiser les couleurs si la touche de retour arrière est pressée lors d'une erreur
			if ((!bad_numbers.isEmpty()) && (chiffre == KeyEvent.VK_BACK_SPACE)) {
			    Text.setBackground(Color.white);
			    while(!bad_numbers.isEmpty()) {
				grid[bad_numbers.removeFirst()][bad_numbers.removeFirst()].setBackground(Color.white);
			    }
			}
		    }   
		}
		
		// colorie la case si elle est bonne ou pas
		if ( (bad_numbers.isEmpty()) && (chiffre == KeyEvent.VK_BACK_SPACE)) {
			Coloriage();
		}
    }
    
    // permet de validé les règles du sudoku pour un chiffre posé.
    public void GrilleValide(char chiffre) { 
		int evaluant = Integer.parseInt(Character.toString(chiffre));
		// Test de validité sur les lignes
		for (int row2 = row + 1; row2 < this.GRID_SIZE; row2++) {
			if (!grid[row2][col].getText().isEmpty()) {
				int comparateur = Integer.parseInt(grid[row2][col].getText());
				if (evaluant == comparateur) {
					this.grid[row2][col].setBackground(Color.red);
					this.grid[row][col].setBackground(Color.red);
					this.bad_numbers.add(row2);
					this.bad_numbers.add(col);
					errorDetected = true;
				}
			}
		}
		for (int row2 = row - 1; row2 >= 0; row2--) {
			if (!grid[row2][col].getText().isEmpty()) {
				int comparateur = Integer.parseInt(grid[row2][col].getText());
				if (evaluant == comparateur) {
					this.grid[row2][col].setBackground(Color.red);
					this.grid[row][col].setBackground(Color.red);
					this.bad_numbers.add(row2);
					this.bad_numbers.add(col);
					errorDetected = true;
				}
			}
		}
		
		// Test de validité sur les colonnes
		for (int col2 = col + 1; col2 < this.GRID_SIZE; col2++) {
			if (!grid[row][col2].getText().isEmpty()) {
				int comparateur = Integer.parseInt(grid[row][col2].getText());
				if (evaluant == comparateur) {
					this.grid[row][col2].setBackground(Color.red);
					this.grid[row][col].setBackground(Color.red);
					this.bad_numbers.add(row);
					this.bad_numbers.add(col2);
					errorDetected = true;
				}
			}
		}
		for (int col2 = col - 1; col2 >= 0; col2--) {
			if (!grid[row][col2].getText().isEmpty()) {
				int comparateur = Integer.parseInt(grid[row][col2].getText());
				if (evaluant == comparateur) {
					this.grid[row][col2].setBackground(Color.red);
					this.grid[row][col].setBackground(Color.red);
					this.bad_numbers.add(row);
					this.bad_numbers.add(col2);
					errorDetected = true;
				}
			}
		}
		
		// Vérifier la validité dans les régions
		int rowregion = this.row/3*3;
		int colregion = this.col/3*3;
		for (int row2 = rowregion; row2 < rowregion + 3; row2++) {
			for (int col2 = colregion; col2 < colregion + 3; col2++) {				
				if (row2 != row && col2 != col ) {					
					if (!grid[row2][col2].getText().isEmpty()) {
						int comparateur = Integer.parseInt(grid[row2][col2].getText());
						if (evaluant == comparateur) {
							this.grid[row2][col2].setBackground(Color.red);
							this.grid[row][col].setBackground(Color.red);
							this.bad_numbers.add(row2);
							this.bad_numbers.add(col2);
							errorDetected = true;
						}
					}
				}
			}
		}
    }
    
    // permet de colorier en blanc si l'erreur n'est plus présente
    public void Coloriage() {
	//coloriage sur les lignes
	for (int row2 = 0; row2 < this.GRID_SIZE; row2++) {
	    if (this.grid[row2][this.col].getBackground() == Color.red)  {
			this.grid[row2][this.col].setBackground(Color.white);
	    }
	}
	
	//coloriage sur les colonnes
	for (int col2 = 0; col2 < this.GRID_SIZE; col2++) {
	    if (this.grid[this.row][col2].getBackground() == Color.red)  {
			this.grid[this.row][col2].setBackground(Color.white);
	    }
	}
	
	//coloriage des régions
	int rowregion = this.row/3*3;
	int colregion = this.col/3*3;
	for (int row2 = rowregion; row2 < rowregion + 3; row2++) {
	    for (int col2 = colregion; col2 < colregion + 3; col2++) {
			if (this.grid[row2][col2].getBackground() == Color.red)  {
				this.grid[row2][col2].setBackground(Color.white);
			}
	    }
	}
    }		    		 
}
