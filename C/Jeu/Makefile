# CHAPITRE 1 : BUT FINAL
but : jeu_snake

# CHAPITRE 2 : VARIABLES
OFILES = option.o serpent.o obstacles_pommes.o menu.o temps.o main.o


CC = gcc
CFLAGS = -ansi -pedantic

# CHAPITRE 3 : DEPENDANCES (REGLES IMPLICITES)
serpent.o : option.h serpent.h obstacles_pommes.h 
obstacles_pommes.o : option.h obstacles_pommes.h serpent.h 
menu.o : option.h menu.h 
temps.o : temps.h
main.o : option.h serpent.h obstacles_pommes.h menu.h temps.h
  
# CHAPITRE 4 : DEPENDANCES AVEC COMMANDES
jeu_snake : $(OFILES)
	$(CC) $(CFLAGS) -o jeu_snake $(OFILES) -lgraph

# CHAPITRE 5 : NETTOYAGE DES FICHIERS GENERES
clean :
	-rm -f $(OFILES) jeu_snake

# CHAPITRE 6 : BUTS FACTICES
.PHONY : but clean

run : jeu_snake 
	./jeu_snake