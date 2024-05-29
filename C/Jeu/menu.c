#include <stdlib.h>
#include <stdio.h>
#include <graph.h>
#include "menu.h"

 /* Permet d'afficher le menu d'accueil */
int menu_accueil() {
    InitialiserGraphique();
    CreerFenetre(10, 10, 1300, 630);
    ChargerImageFond("./wallpaper_snake.png");
}

/* Permet d'afficher les bordures noir du terrain */
int fond_noir() {
    int couleur_b = CouleurParComposante(0, 0, 0);
    ChoisirCouleurDessin(couleur_b);
    RemplirRectangle(0, 0, 1300, 630);
}

/* Permet d'afficher le terrain du jeu, y compris le serpent et les pastilles */
int terrain_de_jeu(CaseSerpent serpent[], int longueurSerpent, Obstacle obstacles[], int nombre_obstacles) {
    int couleur_v = CouleurParComposante(0, 255, 0);
    int i;
    int couleur_serpent = CouleurParComposante(0, 108, 255);
    int couleur_obstacle = CouleurParComposante(158, 158, 158);
    ChoisirCouleurDessin(couleur_v);
    RemplirRectangle(15, 15, 900, 600);
    ChoisirCouleurDessin(couleur_serpent);
    
    creation_serpent(serpent, longueurSerpent);

    creation_obstacles(obstacles, nombre_obstacles);
}