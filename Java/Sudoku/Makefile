### VARAIBLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none

J = java

### REGLES ESSENTIELLES ###

Concepteur.class : Concepteur.java SudokuGridConcepteur.class
	${JC} ${JCFLAGS} Concepteur.java

Joueur.class : Joueur.java SudokuGridJoueur.class
	${JC} ${JCFLAGS} Joueur.java

SudokuGridConcepteur.class : SudokuGridConcepteur.java TextFilter.class SaveButton.class LoadButton.class 
	${JC} ${JCFLAGS} SudokuGridConcepteur.java

SudokuGridJoueur.class : SudokuGridJoueur.java TextFilter.class LoadButton.class SaveButton.class Resolver.class
	${JC} ${JCFLAGS} SudokuGridJoueur.java

TextFilter.class : TextFilter.java
	${JC} ${JCFLAGS} TextFilter.java

SaveButton.class : SaveButton.java
	${JC} ${JCFLAGS} SaveButton.java

LoadButton.class : LoadButton.java
	${JC} ${JCFLAGS} LoadButton.java

Resolver.class : Resolver.java
	${JC} ${JCFLAGS} Resolver.java

### REGLES OPTIONNELLES ###

Concepteur : Concepteur.class
	${J} Concepteur

Joueur : Joueur.class
	${J} Joueur

clean :
	-rm -f *.class *.java~ *~

mrproper : clean Main.class

### BUTS FACTISES ###

.PHONY : run clean mrproper

### FIN ### 
