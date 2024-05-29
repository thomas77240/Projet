#include <stdlib.h>
#include <stdio.h>
#include <graph.h>
#include <time.h>
#include "option.h"
#include "serpent.h"
#include "obstacles_pommes.h"
#include "menu.h"
#include "temps.h"

#define CYCLE 10000L
#define MAX_LONGUEUR 400
#define PAUSE 100000L

int main() {
    
    int direction = 2, go_on = 1;
    int last_direction = 0;
    int longueurSerpent = 10;
    int collision_terrain = 1;
    int collision_serpent = 1;
    CaseSerpent *serpent = (CaseSerpent *)malloc(MAX_LONGUEUR * sizeof(CaseSerpent));
    Pastille pastilles[5];
    int nombre_pastilles = 0;
    unsigned long debutJeu;
    unsigned long pauseJeu;
    unsigned long suivant = Microsecondes() + CYCLE;
    int exit= 0;
    int tempsDebut;
    int score=0;
    char score_score[10];
    int pause = 0;
    unsigned long tempsPause = 0;
    Obstacle obstacles[50];

    menu_accueil();
        /* Permet de detecter la position du clique pour faire fonctionner le menu d'accueil */
        while (1) {
        if (SourisCliquee()) {
            int x = _X;
            int y = _Y;
            if ((x >= 268 && x <= 539) && (y >= 281 && y <= 349)) {
                ChoisirEcran(0);
                break;
            } else if ((x >= 245 && x <= 574) && (y >= 442 && y <= 512)) {
                FermerGraphique();
                return EXIT_SUCCESS;
            }
        }
    }

    srand(time(NULL));
    fond_noir();
    initialiser_serpent(serpent, longueurSerpent);
    initialiser_obstacles(obstacles, 50, serpent, longueurSerpent, pastilles, nombre_pastilles);
    terrain_de_jeu(serpent, longueurSerpent, obstacles, 50);
    ChoisirCouleurDessin(CouleurParComposante(255, 255, 0));
    EcrireTexte(1000, 70, "Pause = espace", 1);

    ChoisirCouleurDessin(CouleurParComposante(255, 255, 0));
    EcrireTexte(1000, 130, "Quitter = Echap", 1);

    ChoisirCouleurDessin(CouleurParComposante(255, 255, 255));
    EcrireTexte(970, 425," Score : 0000000", 2);
    ChoisirCouleurDessin(CouleurParComposante(255, 255, 255));
    EcrireTexte(1010, 425, score_score, 2);

    debutJeu = Microsecondes();
    while (go_on) {
        ChoisirCouleurDessin(CouleurParComposante(0, 0, 0));
        RemplirRectangle(0, 0, 15, 15);
        tempsDebut =0;
        EffacerSerpent(serpent, longueurSerpent);
        dessiner_pastilles_aleatoires(pastilles, &nombre_pastilles, obstacles, 50);
        /* Si on mange une pastille */
        if (manger_pastille(serpent, pastilles, &nombre_pastilles, &longueurSerpent)) {
            longueurSerpent += 2;
            fond_noir();
            terrain_de_jeu(serpent, longueurSerpent, obstacles, 50);
            redessiner_pastilles(pastilles, nombre_pastilles);
            score+=5;
            sprintf(score_score, "%07d", score);
            ChoisirCouleurDessin(CouleurParComposante(255, 255, 255));
            EcrireTexte(970, 425," Score :", 2);
            ChoisirCouleurDessin(CouleurParComposante(255, 255, 255));
            EcrireTexte(1081, 425, score_score, 2);
            
             
            ChoisirCouleurDessin(CouleurParComposante(255, 255, 0));
            EcrireTexte(1000, 70, "Pause = espace", 1);

            ChoisirCouleurDessin(CouleurParComposante(255, 255, 0));
            EcrireTexte(1000, 130, "Quitter = Echap", 1);
        }

        DeplacerSerpent(serpent, direction, longueurSerpent, &collision_terrain, &collision_serpent, obstacles, 50);
        last_direction = direction;
        /* Si collision entre le terrain ou le serpent */
        if (!collision_terrain || !collision_serpent) {
            ChargerImage("./fin.png", 350, 150, 0, 0, 1000, 1000);
            ChoisirCouleurDessin(CouleurParComposante(255, 0, 0));
            EcrireTexte(490, 272, score_score, 1); 
            AfficherTempsPause(debutJeu); 
            go_on=0;
            /* On attend d'appuyer sur Echap pour quitter le programme */
            while (1) {
                if (ToucheEnAttente() == 1) {
                    switch (Touche()) {
                        case XK_Escape:
                            free(serpent);
                            FermerGraphique();
                            return EXIT_SUCCESS;
       
                    }
                }
            }
        }
        /* gère la gestion du temps */
        if (Microsecondes() > suivant){
            suivant = Microsecondes() + CYCLE;
            ChoisirCouleurDessin(CouleurParComposante(0, 0, 0));
            RemplirRectangle(1070,320 ,150,30);
            AfficherTemps(debutJeu);
            Temps(PAUSE);
        
        if (ToucheEnAttente() == 1) {
            switch (Touche()) {
                case XK_Right: /* Droite */
                    direction = 1;
                    if (last_direction == 2 && direction == 1) { /* Bloque le fait d'aller à gauche */
                        direction = last_direction;
                    }
                    break;
                case XK_Left: /* Gauche */
                    direction = 2;
                    if (last_direction == 1 && direction == 2) { /* Bloque le fait d'aller à droite */
                        direction = last_direction;
                    }
                    break;
                case XK_Up: /* Haut */
                    direction = 3;
                    if (last_direction == 4 && direction == 3) { /* Bloque le fait d'aller en bas */
                        direction = last_direction;
                    }
                    break;
                case XK_Down: /* Bas */
                    direction = 4;
                    if (last_direction == 3 && direction == 4) { /* Bloque le fait d'aller en haut */
                        direction = last_direction;
                    }
                    break;
                case XK_Escape: /* Quitte le programme si appuie sur touche espace */
                    free(serpent);
                    FermerGraphique();
                    return EXIT_SUCCESS;
                case XK_space: /* Si appuie sur la touche espace bloque le temps et affiche menu de pause */
                    tempsPause=Microsecondes();
                    ChargerImage("./menu.png", 350, 150, 0, 0, 1000, 1000);
                    ChoisirCouleurDessin(CouleurParComposante(255, 0, 0));
                    EcrireTexte(490, 272, score_score, 1); 
                    AfficherTempsPause(debutJeu); 

                    while(Touche() != XK_space){ /* Si rappuie sur la touche espace on reprend le jeu */
                        ToucheEnAttente(); 
                    }
                    terrain_de_jeu(serpent, longueurSerpent, obstacles, 50);
                    redessiner_pastilles(pastilles, nombre_pastilles); 
                    debutJeu += (Microsecondes() - tempsPause);       
        }
        
        }
    }
    }
    free(serpent);
    Touche();
    FermerGraphique();
    return EXIT_SUCCESS;
}