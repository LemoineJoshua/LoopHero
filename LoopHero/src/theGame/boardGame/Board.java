package theGame.boardGame;

import theGame.entities.Hero;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.AbstractTile;
import theGame.tiles.CampFire;
import theGame.tiles.EmptyField;
import theGame.tiles.EmptyRoadSide;
import theGame.tiles.Grove;
import theGame.tiles.Meadow;
import theGame.tiles.Rock;
import theGame.tiles.Wastelands;

public class Board {
	private int loop;
    private int position;
    private final Coord[] coordList;
	private final AbstractTile[][] boardMatrix;
	private final Hero hero = new Hero(100,100,100,100,100,100,100,"NoImage");
	
	
	public Board() {
		this.loop=0;
        this.position=0;
		this.boardMatrix=initCases();
		this.coordList =initPath();
	}
	
	private AbstractTile[][] initCases(){
        //initialise le plateau de jeu (vide avec une route)
		AbstractTile[][] boardMatrix = new AbstractTile[12][21];

        for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
            	boardMatrix[y][x]= new EmptyField();//m�me les cases de vide sont des cases
        	}
        }
        
        return boardMatrix;
    }

    private Coord[] initPath(){
        //renvoie le chemin que doit parcourir le hero coordonees par coordonées
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
        
        boardMatrix[coordList[0].y()][coordList[0].x()] = new CampFire();
        
        boardMatrix[4][3] = new Meadow();
        boardMatrix[4][2] = new Rock(this,4,2);
        boardMatrix[4][9] = new Grove(6);
        
        return coordList;
    }

       
    public void dailyEffect() {
    	for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
        		boardMatrix[y][x].dailyEffect(this);
        	}
    	}
    }
    
    public void loopEffect() {
    	for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
        		boardMatrix[y][x].loopEffect(this);
        	}
    	}
    }
    
    public boolean fight(RessourcesInventory lootList,CardInventory CardList){
		//Fonction appelee apres isCombat qui serait dans Case
    	AbstractRoad heroTile = (AbstractRoad) boardMatrix[heroY()][heroX()]; 
        if( heroTile.isCombat()){
        	heroTile.clearMob(lootList,CardList);
            hero.lossHP(6);
            System.out.println(hero.hp); //faut remettre les hps en protected
            return true;
        }
        return false;
    }

	public void plusloop(){
		// Fonction appelée quand le Hero passe sur la case Camp
		loop+=1;
	}
	
    public void move(){
        //fait déplacer le hero en augmentant sa position dans les coordonnées
        position+=1;
	}

	public int heroX(){
        //facilite l'accès a la coordonnée X du héro
		return coordList[position].x();
    }

	public int heroY(){
        //facilite l'accès a la coordonnée Y du héro
		return coordList[position].y();
    }
	
	public Coord[] coordList() {
		return coordList;
	}
	
	public boolean moveHero() {
		position +=1;
		if (position == 34) {
			position =0;
			plusloop();
			return true;
		}
		return false;
	}
	
	
	public AbstractTile[][] boardMatrix(){
		return boardMatrix;
	}
	
	public int loop() {
		return loop;
	}
	
	public Hero hero() {
		return hero;
	}
}
