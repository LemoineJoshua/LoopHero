package theGame.boardGame;

import java.util.ArrayList;

import theGame.entities.Hero;
import theGame.entities.Monster;
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
	private final Hero hero = new Hero(250.0,1.0,0.0,0.0,0.0,0.0,0.0);
	
	
	/**
	 * Board Constructor,
	 * Set the loop number to one and create the path and the board
	 */
	public Board() {
		this.loop=1;
        this.position=0;
		this.boardMatrix=initCases();
		this.coordList =initPath();
	}
	
	/**
	 * Set the board matrix
	 * 
	 * @return the matrix created
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
     * Initialize the path and the roadside around
     * 
     * @return the initialized path
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
        ArrayList<Monster> beginningMob = new ArrayList<Monster>();
        /*
        beginningMob.add(new Monster(16,3.6,0.0,0.0,0.05,0.10,0.00,(float)0.05,(float)0.6,drop,"pictures/Entities/ratWolf.png", "pictures/Entities/ratWolfFight.png"));
        beginningMob.add(new Monster(13,3.3,0.0,0.0,0.0,0.0,0.0,(float)0.05, (float) 0.65,drop,"pictures/Entities/slimeS.png", "pictures/Entities/slimeFight.png"));
        beginningMob.add(new Monster(13,3.3,0.0,0.0,0.0,0.0,0.0,(float)0.05, (float) 0.65,drop,"pictures/Entities/slimeS.png", "pictures/Entities/slimeFight.png"));
        beginningMob.add(new Monster(16,3.6,0.0,0.0,0.05,0.10,0.0,(float)0.05,(float)0.6,drop,"pictures/Entities/ratWolf.png", "pictures/Entities/ratWolfFight.png"));
        */
        //beginningMob.add(new Monster(13,3.3,0.0,0.0,0.0,0.0,0.0,(float)0.05, (float) 0.65,drop,"pictures/Entities/slimeS.png", "pictures/Entities/slimeFight.png"));
        
        boardMatrix[4][11] = new Wastelands(new ArrayList<>(beginningMob));
        
        boardMatrix[coordList[0].y()][coordList[0].x()] = new CampFire();        
        return coordList;
    }

       
    /**
     * Apply the day effect of each case of the board
     */
    public void dailyEffect() {
    	for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
        		boardMatrix[y][x].dailyEffect(this);
        	}
    	}
    }
    
    /**
     * Apply the loop effect of each case of the board
     */
    public void loopEffect() {
    	for(int x=0;x<21;x++){
        	for(int y=0;y<12;y++) {
        		boardMatrix[y][x].loopEffect(this);
        	}
    	}
    }
    
    /**
     * Return the index of the case, if it's in the path
     * 
     * @param indexY : the index of the line tested
     * @param indexX : the index of the column tested
     * @return the index of the path in the matrix, if it's in, else return -1
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
     * Load the fight, if the hero is on a cell where there is monsters
     * 
     * @param lootList : All the resources collected by the hero
     * @param CardList : All cards of the game, split in the deck, and the cards in the player's hand
     * @return true if there is a fight, else false
     */
    public boolean isFight(RessourcesInventory lootList, CardInventory CardList){
    	AbstractRoad heroTile = (AbstractRoad) boardMatrix[heroY()][heroX()]; 
        return heroTile.isFight();
    }

	/**
	 * Increase the loop counter by 1
	 */
	public void plusloop(){
		loop+=1;
	}
	
    /**
     * Change the index of the hero in the path to move him
     */
    public void move(){
        position+=1;
        
	}

	/**
	 * Get the x coord of the Hero
	 * 
	 * @return the x coord of the Hero
	 */
	public int heroX(){
		return coordList[position].x();
    }

	/**
	 * Get the y coord of the Hero
	 * 
	 * @return the y coord of the Hero
	 */
	public int heroY(){
		return coordList[position].y();
    }
	
	/**
	 * Get the list of path cell
	 * 
	 * @return the list of path cell
	 */
	public Coord[] coordList() {
		return coordList;
	}
	
	/**
	 * Move the Hero
	 * 
	 * @return true if the hero pass on the campfire, else false
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
	 * BoardMatrix Accessor
	 * 
	 * @return the board matrix
	 */
	public AbstractTile[][] boardMatrix(){
		return boardMatrix;
	}
	
	/**
	 * LoopCounter Accessor 
	 * 
	 * @return the loop counter
	 */
	public int loop() {
		return loop;
	}
	
	/**
	 * Heor Accessor
	 * 
	 * @return hero
	 */
	public Hero hero() {
		return hero;
	}
}
