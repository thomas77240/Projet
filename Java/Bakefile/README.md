# Bake - Un utilitaire de compilation simplifié

## Description
Bake est une implémentation simplifiée de la commande `make` permettant de générer et maintenir à jour des fichiers en fonction de leurs sources. Il repose sur un fichier de configuration nommé `Bakefile`, similaire au `Makefile` mais avec une complexité réduite.

## Objectifs du projet
Ce projet est réalisé dans le cadre du BUT Informatique et a pour but de créer un outil capable de :
- Lire et analyser un fichier `Bakefile` contenant des règles de compilation.
- Déterminer les fichiers nécessitant une mise à jour.
- Exécuter les commandes associées en utilisant `ProcessBuilder`.
- Gérer les dépendances et détecter les cycles afin d'éviter les boucles infinies.
- Fournir un mode débogage détaillé via l'option `-d`.

## Fonctionnalités principales
- Lecture et analyse du `Bakefile`.
- Gestion des règles de compilation et des dépendances.
- Exécution des commandes bash définies dans les recettes.
- Gestion des variables définies dans le `Bakefile`.
- Détection des dépendances circulaires.
- Mode debug avec affichage détaillé du processus de compilation.

## Format du fichier `Bakefile`
Le `Bakefile` contient des règles de compilation sous la forme suivante :

```
cible1 cible2 : dépendance1 dépendance2
	commande1
	commande2
```

Les commentaires sont précédés par `#`.

Une variable peut être définie avec `=` et utilisée dans les recettes.

Exemple :
```
CC=gcc

main.o: main.c
	$(CC) -c main.c -o main.o
```

## Installation et utilisation
### Prérequis
- Java 17 ou supérieur
- Un système compatible avec bash

### Compilation
Utiliser `make` pour compiler le projet :
```sh
make
```

### Exécution
```sh
java -jar Bake.jar [options] [cibles]
```
Options disponibles :
- `-d` : Active le mode débogage
- `-l` : Active l'extraction des logs dans un fichier `logfile.log`

Si aucune cible n'est spécifiée, la première cible du `Bakefile` sera exécutée.

### Exemples d'utilisation
```sh
java -jar Bake.jar my_target
java -jar Bake.jar -d
```

## Tests
Plusieurs scénarios de test sont inclus dans le dossier `tests/`. Ils couvrent :
- Une compilation depuis zéro.
- Une compilation avec un résultat déjà existant.
- Une compilation après modification d'une source.
- Une compilation avec dépendances circulaires.
