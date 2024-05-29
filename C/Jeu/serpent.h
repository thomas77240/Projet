#ifndef SERPENT_H
#define SERPENT_H

#include "obstacles_pommes.h"
#include "option.h"

void creation_serpent(CaseSerpent serpent[], int longueurSerpent);
void initialiser_serpent(CaseSerpent serpent[], int longueurSerpent);
void EffacerSerpent(CaseSerpent serpent[], int longueurSerpent);
void DeplacerSerpent(CaseSerpent serpent[], int direction, int longueurSerpent, int *collision_terrain, int *collision_serpent, Obstacle obstacles[], int nombre_obstacles);

#endif /* SERPENT_H */