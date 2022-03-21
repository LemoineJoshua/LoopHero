package theGame.boardGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import theGame.entities.Hero;
import theGame.entities.Monster;

public class Board {
	private int boucle;
    private int position;
    private final Coord[] listeCoord;
	private final Cases[][] matricePlateau;
	private final Hero hero = new Hero(100,100,100,100,100,100,100,"NoImage");
	
	
	public Board() {
		this.boucle=0;
        this.position=0;
        this.listeCoord =initPath();
		this.matricePlateau=initCases();
	}
	
	private Cases[][] initCases(){
        //initialise le plateau de jeu (vide avec une route)
        Cases[][] matricePlateau = new Cases[12][21];

        for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
            	matricePlateau[y][x]= new Cases();//m�me les cases de vide sont des cases
        	}
        }
        
        return matricePlateau;
    }

    private Coord[] initPath(){
        //renvoie le chemin que doit parcourir le hero coordonees par coordonées
        Coord[] listeCoord = 
        		{
        		new Coord(5,2),new Coord(6,2),new Coord(7,2),new Coord(7,3),new Coord(8,3),new Coord(8,4),new Coord(9,4),new Coord(10,4),
        		new Coord(11,4),new Coord(11,3),new Coord(11,2),new Coord(12,2),new Coord(13,2),new Coord(13,3),new Coord(14,3),
        		new Coord(14,4), new Coord(14,5),new Coord(14,6),new Coord(13,6),new Coord(13,7),new Coord(12,7),new Coord(11,7),
        		new Coord(11,8),new Coord(10,8),new Coord(9,8),new Coord(9,7),new Coord(8,7),new Coord(7,7),new Coord(6,7),new Coord(6,6),
        		new Coord(6,5),new Coord(5,5),new Coord(5,4),new Coord(5,3)
        		};
        return listeCoord;
    }

    public void initSlime(Monster mob) {
    	for(Coord coord: listeCoord) {
    		matricePlateau[coord.y()][coord.x()].addSpawnable(mob);
    	}
    }
    
    public void SpawningTime() {
    	for(Coord coord: listeCoord) {
    		matricePlateau[coord.y()][coord.x()].SpawnTour();
    	}
    }
    
    public void combat(){
		//Fonction appelee apres isCombat qui serait dans Case
        if(matricePlateau[heroY()][heroX()].isCombat()){
        	matricePlateau[heroY()][heroX()].clearMob();
            hero.perteHP(6);
            System.out.println(hero.hp); //faut remettre les hps en protected
            //Temps.combat();
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

	public int heroX(){
        //facilite l'accès a la coordonnée X du héro
		return listeCoord[position].x();
    }

	public int heroY(){
        //facilite l'accès a la coordonnée Y du héro
		return listeCoord[position].y();
    }
	
	public Coord[] listeCoord() {
		return listeCoord;
	}
	
	public void moveHero() {
		position +=1;
		if (position == 34) {
			position =0;
		}
	}
	
	public void addSpawnable(Monster mob, int x, int y) {
		matricePlateau[y][x].addSpawnable(mob);
	}
	
	public Cases[][] matricePlateau(){
		return matricePlateau;
	}
	
	
	
}