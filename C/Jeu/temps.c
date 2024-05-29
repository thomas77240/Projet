#include <stdlib.h>
#include <stdio.h>
#include <graph.h>
#include "temps.h"

/* Permet de faire une pause d'une durée spécifiée en microsecondes */
void Temps(unsigned long us) {
    unsigned long attente = Microsecondes() + us;
    while (Microsecondes() < attente) {}
}

/* Permet d'afficher le temps dans le fond noir */
void AfficherTemps(unsigned long tempsDebut) { 
    unsigned long Microsecondes();
    unsigned long tempsActuel = Microsecondes();
    unsigned long tempsEcoule = tempsActuel - tempsDebut;
    char tempsMinutes[10], tempsSecondes[10];

    int minutes = tempsEcoule / 60000000; 
    int secondes = (tempsEcoule % 60000000) / 1000000;
    
    ChoisirCouleurDessin(CouleurParComposante(255, 255, 255));
    EcrireTexte(970, 350, "Temps :", 2);

    sprintf(tempsMinutes, "%02d :", minutes);
    sprintf(tempsSecondes, "%02d", secondes);

    EcrireTexte(1110, 350, tempsMinutes, 2);
    EcrireTexte(1170, 350, tempsSecondes, 2);
}

/* Permet d'afficher le temps dans le menu de pause et de fin */
void AfficherTempsPause(unsigned long tempsDebut) { 
    unsigned long Microsecondes();
    unsigned long tempsActuel = Microsecondes();
    unsigned long tempsEcoule = tempsActuel - tempsDebut;
    char tempsMinutes[10], tempsSecondes[10];

    int minutes = tempsEcoule / 60000000; 
    int secondes = (tempsEcoule % 60000000) / 1000000;

    sprintf(tempsMinutes, "%02d :", minutes);
    sprintf(tempsSecondes, "%02d", secondes);

    EcrireTexte(500, 330, tempsMinutes, 1);
    EcrireTexte(535, 330, tempsSecondes, 1);
}