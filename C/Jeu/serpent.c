#include <stdlib.h>
#include <stdio.h>
#include <graph.h>
#include "serpent.h"
#include "obstacles_pommes.h"

/* Permet de crée le serpent */
void creation_serpent(CaseSerpent serpent[], int longueurSerpent){
    int i;
    for (i = 0; i < longueurSerpent; i++) {
            RemplirRectangle(serpent[i].x, serpent[i].y, 15, 15);
        }
    }

/* Permet de mettre le serpent au millieu du terrain */
void initialiser_serpent(CaseSerpent serpent[], int longueurSerpent) {
    int i;
    int milieu_x = 60 / 2;
    int milieu_y = 40 / 2;
    for (i = 0; i < longueurSerpent; i++) {
        serpent[i].x = (milieu_x - longueurSerpent / 2 + i) * 15;
        serpent[i].y = milieu_y * 15;
    }
}

/* Permet d'effacer la queue du serpent (utilisé pour la fonction du déplacement du serpent) */
void EffacerSerpent(CaseSerpent serpent[], int longueurSerpent) {
    int i;
    for (i = 0; i < longueurSerpent; i++) {
        ChoisirCouleurDessin(CouleurParComposante(0, 255, 0));
        RemplirRectangle(serpent[i].x, serpent[i].y, 15, 15);
    }
}

/* Permet de faire déplacer le serpent */
void DeplacerSerpent(CaseSerpent serpent[], int direction, int longueurSerpent, int *collision_terrain, int *collision_serpent, Obstacle obstacles[], int nombre_obstacles) {
    int i;
    EffacerSerpent(serpent, longueurSerpent);
    for (i = longueurSerpent - 1; i > 0; i--) {
        serpent[i] = serpent[i - 1];
    }
    switch (direction) {
        case 1:
            serpent[0].x += 15; /* droite */
            break;
        case 2:
            serpent[0].x -= 15; /* gauche */
            break;
        case 3:
            serpent[0].y -= 15; /* Bas */
            break;
        case 4:
            serpent[0].y += 15; /* Haut */
            break;
    }
    /* Détecte la collision des bordures noires */
    if (serpent[0].x < 15 || serpent[0].x >= 915 || serpent[0].y < 15 || serpent[0].y >= 615) {
        *collision_terrain = 0; 
        return;
    }
    /* Détecte la collision du serpent sur lui même */
    for (i = 1; i < longueurSerpent; i++) {
        if (serpent[0].x == serpent[i].x && serpent[0].y == serpent[i].y) {
            *collision_serpent = 0;
            return;
        }
    }
    /* Détecte la collision des obstacles */
    for (i = 0; i < nombre_obstacles; i++) {
        if (serpent[0].x == obstacles[i].x && serpent[0].y == obstacles[i].y) {
            *collision_terrain = 0;
            return;
        }
    }

    /* Rempli la tête du serpent pour le faire avancer */
    for (i = 0; i < longueurSerpent; i++) {
        ChoisirCouleurDessin(CouleurParComposante(0, 108, 255));
        RemplirRectangle(serpent[i].x, serpent[i].y, 15, 15);
    }
}

