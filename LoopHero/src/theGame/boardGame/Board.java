package theGame.boardGame;

import java.util.ArrayList;

import theGame.entities.Hero;
import theGame.entities.Monster;
import theGame.entities.Stats;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.AbstractTile;
import theGame.tiles.CampFire;
import theGame.tiles.EmptyField;
import theGame.tiles.EmptyRoadSide;
import theGame.tiles.Wastelands;

public class Board {
	private int loop;
    private int position;
    private final Coord[] coordList;
	private final AbstractTile[][] boardMatrix;
	private final Hero hero = new Hero(new Stats(250,100,100,100,100,100,100));
	
	
	/**
	 * Constructeur du plateau,
	 * Initialise la loop à 0 crée le chemin et le plateau
	 */
	public Board() {
		this.loop=0;
        this.position=0;
		this.boardMatrix=initCases();
		this.coordList =initPath();
	}
	
	/**
	 * Initialise la matrice de jeu
	 * 
	 * @return la matrice initilisée
	 */
	private AbstractTile[][] initCases(){
		AbstractTile[][] boardMatrix = new AbstractTile[12][21];

        for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
            	boardMatrix[y][x]= new EmptyField();
        	}
        }
        return boardMatrix;
    }

    /**
     * Initialise le chemin et les roadSide
     * 
     * @return le chemin initialisé
     */
    private Coord[] initPath(){
        Coord[] coordList = 
        		{
        		new Coord(5,2),new Coord(6,2),new Coord(7,2),new Coord(7,3),new Coord(8,3),new Coord(8,4),new Coord(9,4),new Coord(10,4),
        		new Coord(11,4),new Coord(11,3),new Coord(11,2),new Coord(12,2),new Coord(13,2),new Coord(13,3),new Coord(14,3),
        		new Coord(14,4), new Coord(14,5),new Coord(14,6),new Coord(13,6),new Coord(13,7),new Coord(12,7),new Coord(11,7),
        		new Coord(11,8),new Coord(10,8),new Coord(9,8),new Coord(9,7),new Coord(8,7),new Coord(7,7),new Coord(6,7),new Coord(6,6),
        		new Coord(6,5),new Coord(5,5),new Coord(5,4),new Coord(5,3)
        		};
        
        boolean sideIsRoadSide=true;
    	boolean topIsRoadSide=true;
        
        for (Coord coord:coordList) {
        	boardMatrix[coord.y()][coord.x()]= new Wastelands();
      	
        	for (int n = -1; n<2 ; n+=2) {
        		sideIsRoadSide=true;
            	topIsRoadSide=true;
        		for (Coord coord2:coordList) {
        			if ((!sideIsRoadSide) && (!topIsRoadSide)) {break;}
        			
        			if (new Coord(coord.x()+n,coord.y()).equals(coord2)){
            			sideIsRoadSide = false;
            		}
        			if (new Coord(coord.x(),coord.y()+n).equals(coord2)){
        				topIsRoadSide = false;
            		}
        		}
        		
        		if (sideIsRoadSide) {
        			boardMatrix[coord.y()][coord.x()+n] = new EmptyRoadSide();
        		}
        		if (topIsRoadSide) {
        			boardMatrix[coord.y()+n][coord.x()] = new EmptyRoadSide();
        		}
        	}
        } 
        
        ArrayList<String> drop = new ArrayList<>();
 	    drop.add("Shapeless Mass");
 	    drop.add("Craft Fragment");
        ArrayList<Monster> beginningSlime = new ArrayList<Monster>();
        beginningSlime.add(new Monster(new Stats((long)13,3.3,0.0,0.0,0.0,0.0,0.0),(float)0.05, (float) 0.65,drop,"pictures/Entities/slimeS.png"));
        
        boardMatrix[4][11] = new Wastelands(new ArrayList<>(beginningSlime));
        boardMatrix[6][13] = new Wastelands(new ArrayList<>(beginningSlime));
        boardMatrix[3][7] = new Wastelands(new ArrayList<>(beginningSlime));
        
        boardMatrix[coordList[0].y()][coordList[0].x()] = new CampFire();        
        return coordList;
    }

       
    /**
     * applique les effets du jours de chaque case
     */
    public void dailyEffect() {
    	for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
        		boardMatrix[y][x].dailyEffect(this);
        	}
    	}
    }
    
    /**
     * applique les effets d'un tour de chaque case
     */
    public void loopEffect() {
    	for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
        		boardMatrix[y][x].loopEffect(this);
        	}
    	}
    }
    
    /**
     * Renvoie l'index de la case dans le chemin si il existe
     * 
     * @param indexY ligne testée
     * @param indexX colonne testée
     * @return l'index du chemin si la case est dedans, -1 sinon
     */
    public int getIndexInLoop(int indexY, int indexX) {
    	for (int i=0; i<coordList.length;i++) {
    		if (coordList[i].x()==indexX && coordList[i].y()==indexY) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * Effectue le combat si il y a un monstre sur la case ou est le héro
     * 
     * @param lootList l'inventaire des ressources
     * @param CardList l'inventaire des cartes
     * @return vrai si un combat à eu lieu
     */
    public boolean fight(RessourcesInventory lootList, CardInventory CardList){
    	AbstractRoad heroTile = (AbstractRoad) boardMatrix[heroY()][heroX()]; 
        if( heroTile.isCombat()){
        	heroTile.clearMob(lootList,CardList);
            hero.lossHP(6);
            return true;
        }
        return false;
    }

	/**
	 * Augmente le conteur de  tour de 1
	 */
	public void plusloop(){
		loop+=1;
	}
	
    /**
     * Change l'index du héro sur le chemin
     */
    public void move(){
        position+=1;
        
	}

	/**
	 * @return la coordonnée X du héro
	 */
	public int heroX(){
		return coordList[position].x();
    }

	/**
	 * @return la coordonnée Y du héro
	 */
	public int heroY(){
		return coordList[position].y();
    }
	
	/**
	 * @return la liste des coordonée du chemin
	 */
	public Coord[] coordList() {
		return coordList;
	}
	
	/**
	 * Bouge le héro
	 * 
	 * @return vrai si le héro passe au feux de camps
	 */
	public boolean moveHero() {
		position +=1;
		if (position == 34) {
			position =0;
			plusloop();
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return la matrice de case
	 */
	public AbstractTile[][] boardMatrix(){
		return boardMatrix;
	}
	
	/**
	 * @return le numéro de la boucle
	 */
	public int loop() {
		return loop;
	}
	
	/**
	 * @return le hero
	 */
	public Hero hero() {
		return hero;
	}
}
