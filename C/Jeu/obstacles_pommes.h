#ifndef OBSTACLES_POMMES_H
#define OBSTACLES_POMMES_H
#include "serpent.h"
#include "option.h"


void creation_obstacles(Obstacle obstacles[], int nombre_obstacles);
void initialiser_obstacles(Obstacle obstacles[], int nombre_obstacles, CaseSerpent serpent[], int longueurSerpent, Pastille pastilles[], int nombre_pastilles);
void dessiner_pastilles_aleatoires(Pastille pastilles[], int *nombre_pastilles, Obstacle obstacles[], int nombre_obstacles);
int collision_pastille(int x_serpent, int y_serpent, int x_pastille, int y_pastille);
int manger_pastille( CaseSerpent serpent[], Pastille pastilles[], int *nombre_pastilles, int *longueurSerpent);
int redessiner_pastilles(Pastille pastilles[], int nombre_pastilles);

#endif /* OBSTACLES_POMMES_H */
