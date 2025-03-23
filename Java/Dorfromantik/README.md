# SAE31_2024 - Jeu Hexagonal

## Description

Ce projet est un jeu de tuiles hexagonales inspiré de **Dorfromantik**, où le joueur doit construire un paysage harmonieux en assemblant des tuiles hexagonales. Le jeu inclut :

- Cinq types de terrains : mer, champ, prairie, forêt et montagne.
- Un système de score basé sur la connexion des régions de terrain.
- Une base de données pour stocker les séries de jeu et les scores des joueurs.

Le projet suit l’architecture **MVC** et est entièrement implémenté en Java.

---

## Fonctionnalités

### Gameplay

1. Les joueurs placent des tuiles hexagonales adjacentes à celles déjà posées.
2. Une tuile peut contenir un ou deux types de terrains, répartis sur ses côtés.
3. Une partie se termine après avoir posé 50 tuiles.
4. Le score est calculé en additionnant les carrés des tailles des régions connectées d’un même terrain.

### Intégration avec la base de données

- Un serveur MariaDB stocke les séries prédéfinies et les scores des joueurs.
- Les scores sont anonymisés et classés pour permettre des comparaisons.

---

## Auteurs

- **Raphael Hochlaf**
- **Thomas Follea**
- **Basile Legrelle**

---

## Comment cloner le projet

Exécutez la commande suivante pour cloner le dépôt :

```bash
git clone --depth 1 gitea@grond.iut-fbleau.fr:legrelleb/SAE31_2024.git
```

---

## Compilation et Exécution

### Utilisation du Makefile

Pour compiler et exécuter le projet :

1. Compiler le projet :
   ```bash
   make
   ```
2. Lancer le jeu (depuis le répertoire de génération) :
   ```bash
   make run
   ```
3. Nettoyer les fichiers de build :
   ```bash
   make clean
   ```

### Compilation manuelle

Vous pouvez également compiler manuellement avec :
```bash
javac -d build -cp build -sourcepath "src" -encoding UTF-8 src/main/java/SAE31_2024/Main.java
```
Pour exécuter le programme :
```bash
java -cp build SAE31_2024.Main
```
Pour exécuter l'archive .jar :
```bash
java -jar LofiTiles.jar
```

---

## Structure du projet

### Code source

- `SAE31_2024.Main` - Point d’entrée du programme.
- `SAE31_2024.controller.*` - Contrôleurs pour la logique du jeu et les interactions utilisateur.
- `SAE31_2024.model.*` - Modèles représentant les entités du jeu, comme les tuiles et les régions de terrain.
- `SAE31_2024.view.*` - Composants de l’interface utilisateur.
- `SAE31_2024.toolbox.Toolbox` - Une boîte à outils permettant d'avoir des constantes globales à tout le projet.

### Ressources

- **Images** : le dossier `res/` contient les logos et autres images du projet.
- _~~**Audio** : le dossier `res/audio/Tracks/` contient des musiques.~~_ (pour des raisons de compatibilité avec la distribution en .jar, cette fonctionalité ne sera disponible que par l'ajout du dossier `res/audio/Tracks/`)

---

## Directives de développement

1. **Dépôt Git** : Le code source et les ressources sont hébergés sur le serveur Gitea.
2. **Documentation** : Chaque classe et méthode est documentée avec Javadoc.
3. **Makefile** : Un Makefile est fourni pour simplifier la compilation et la création d’un fichier JAR.

---

## Rapport

Un rapport PDF accompagne le projet et contient :

- Une introduction au concept du jeu.
- Des captures d’écran illustrant les fonctionnalités.
- Des diagrammes de classes et des explications d’algorithmes.
- Des réflexions personnelles des membres du groupe.
