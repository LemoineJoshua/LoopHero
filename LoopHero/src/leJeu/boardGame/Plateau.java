package leJeu.boardGame;

import java.util.ArrayList;

import leJeu.entities.Hero;

public class Plateau {
	private int boucle;
    private int position;
    private final Coord[] listeCoord;
	private final Cases[][] matricePlateau;
	private final Hero hero = new Hero(100,100,100,100,100,100,100,"image");
	
	
	public Plateau() {
		this.boucle=0;
        this.position=0;
        this.listeCoord =initCoord();
		this.matricePlateau=initCases();
	}
	
	private Cases[][] initCases(){
        //initialise le plateau de jeu (vide avec une route)
        Cases[][] matricePlateau = new Cases[12][21];

        for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
            	matricePlateau[x][y]= new Cases();//m�me les cases de vide sont des cases
        	}
        }
        return matricePlateau;
    }

    private Coord[] initCoord(){
        //renvoie le chemin que doit parcourir le héro coordonées par coordonées
        Coord[] listCoord = new Coord[34];
        return listCoord;
    }

    public void combat(int x, int y){
		//Fonction appelee apres isCombat qui serait dans Case
        if(matricePlateau[heroX()][heroY()].isCombat()){
            hero.perteHP(6);
            Temps.combat();
        }
    }

	public void plusBoucle(){
		// Fonction appelée quand le Hero passe sur la case Camp
		boucle+=1;
	}
	
    public void deplacement(){
        //fait déplacer le hero en augmentant sa position dans les coordonnées
        position+=1;
	}

	private int heroX(){
        //facilite l'accès a la coordonnée X du héro
		return listeCoord[position].x();
    }

	private int heroY(){
        //facilite l'accès a la coordonnée Y du héro
		return listeCoord[position].y();
    }
}
