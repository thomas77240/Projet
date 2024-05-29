#include <stdlib.h>
#include <stdio.h>
#include <graph.h>
#include <time.h>
#include "obstacles_pommes.h"

/* Permet de crée les obstacles */
void creation_obstacles(Obstacle obstacles[], int nombre_obstacles){
    int couleur_obstacle = CouleurParComposante(158, 158, 158),i;
    ChoisirCouleurDessin(couleur_obstacle);
    for (i = 0; i < nombre_obstacles; i++) {
        RemplirRectangle(obstacles[i].x, obstacles[i].y, 15, 15);
    }
}

/* Permet d'initialiser les obstacles */
void initialiser_obstacles(Obstacle obstacles[], int nombre_obstacles, CaseSerpent serpent[], int longueurSerpent, Pastille pastilles[], int nombre_pastilles) {
    int i, j;
    int x, y;
    int collision;
  
    /* Crée les obstacles de façon aléatoires */
    for (i = 0; i < nombre_obstacles; i++) {
        do {
            collision = 0;
            x = 15 + rand() % (900 - 2 * 15 - 15);
            y = 15 + rand() % (600 - 2 * 15 - 15);
            x = x - (x % 15);
            y = y - (y % 15);

           /* Ne pas faire apparaître d'obstacles sur le serpent */
            for (j = 0; j < longueurSerpent; j++) {
                if (x == serpent[j].x && y == serpent[j].y) {
                    collision = 1;
                    break;
                }
            }

            /* Ne pas faire apparaître d'obstacles sur les pastilles */
            for (j = 0; j < nombre_pastilles; j++) {
                if (x == pastilles[j].x && y == pastilles[j].y) {
                    collision = 1;
                    break;
                }
            }
        } while (collision);

        obstacles[i].x = x;
        obstacles[i].y = y;
    }
}

/* Permet de crée et dessiner les pastilles (pomme) de manière aléatoire */
void dessiner_pastilles_aleatoires(Pastille pastilles[], int *nombre_pastilles, Obstacle obstacles[], int nombre_obstacles) {
    int i, x, y, j;
    int Present = 0;

    /* Tant que le nombre de pastilles est inférieur à 5 on  */
    while (*nombre_pastilles < 5) {
        Present = 0;
        x = 15 + rand() % (900 - 2 * 15 - 15);
        y = 15 + rand() % (600 - 2 * 15 - 15);
        x = x - (x % 15);
        y = y - (y % 15);
        /* Ne pas dessiner de pastilles sur des pastilles déjà présente */
        for (j = 0; j < *nombre_pastilles; j++) {
            if (x == pastilles[j].x && y == pastilles[j].y) {
                Present = 1;
                break;
            }
        }
        /* Ne pas dessiner des pastilles sur des obstacles présent */
        for (j = 0; j < nombre_obstacles; j++) {
            if (x == obstacles[j].x && y == obstacles[j].y) {
                Present = 1;
                break;
            }
        }
        /* Si il n'y a pas de pastilles ou d'obstacles à l'endroit prévu, on dessine la pastille */
        if (!Present) {
            pastilles[*nombre_pastilles].x = x;
            pastilles[*nombre_pastilles].y = y;
            (*nombre_pastilles)++;
            ChargerImage("./pomme.png",x,y,0,0,15,15);
        }
    }
}

/* Si le serpent entre en collision avec une pastille on return les valeurs de celle-ci */
int collision_pastille(int x_serpent, int y_serpent, int x_pastille, int y_pastille) {
    return x_serpent == x_pastille && y_serpent == y_pastille;
}

/* Permet de faire grandir le serpent si on mange une pastille */
int manger_pastille(CaseSerpent serpent[], Pastille pastilles[], int *nombre_pastilles, int *longueurSerpent) {
    int i, j;
    int x_tete = serpent[0].x;
    int y_tete = serpent[0].y;
    
    for (i = 0; i < *nombre_pastilles; i++) {
        if (collision_pastille(x_tete, y_tete, pastilles[i].x, pastilles[i].y)) {
            for (j = *longueurSerpent - 1; j > 0; j--) {
                serpent[j] = serpent[j - 1];
            }

            serpent[0].x = pastilles[i].x;
            serpent[0].y = pastilles[i].y;

            for (j = i; j < *nombre_pastilles - 1; j++) {
                pastilles[j].x = pastilles[j + 1].x;
                pastilles[j].y = pastilles[j + 1].y;
            }
            (*nombre_pastilles)--;
            return 1;
        }
    }
    return 0;
}

/* Permet de redessiner les pastilles quand on en mange une */
int redessiner_pastilles(Pastille pastilles[], int nombre_pastilles) { 
    int i;
    int score=0;

    for (i = 0; i < nombre_pastilles; i++) {
        ChargerImage("./pomme.png", pastilles[i].x,pastilles[i].y,0,0,15,15);
    }
} 