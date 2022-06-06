package theGame.boardGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import theGame.entities.Hero;
import theGame.Game;
import theGame.TimeData;
import theGame.entities.AbstractMonster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.AbstractTile;
import theGame.tiles.CampFire;
import theGame.tiles.EmptyField;
import theGame.tiles.EmptyRoadSide;
import theGame.tiles.Wastelands;

public class Board implements Serializable{
	private int loop;
    private int position;
    private final ArrayList<Coord> coordList;
	private final AbstractTile[][] boardMatrix;
	private final Hero hero = new Hero(250.0,1.0,0.0,0.0,0.0,0.0,0.0);
	
	
	/**
	 * Board Constructor,
	 * Set the loop number to one and create the path and the board
	 */
	public Board(String path) {
		this.loop=1;
        this.position=0;
		this.boardMatrix= Objects.requireNonNull(initCases()) ;
		this.coordList =Objects.requireNonNull(initPath(path));
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
    private ArrayList<Coord> initPath(String path){
    	ArrayList<Coord> coordList = new ArrayList<>();
    	
    	try(BufferedReader reader = Files.newBufferedReader(Path.of(path))){
    		String line;
			while((line=reader.readLine())!=null) {
				String[] coords = line.split(",");
				coordList.add(new Coord(Integer.valueOf(coords[0]),Integer.valueOf(coords[1])));
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
        
        boolean sideIsRoadSide=true;
    	boolean topIsRoadSide=true;
        
        for (Coord coord:coordList) {
        	boardMatrix[coord.y()][coord.x()]= new Wastelands(coord);
      	
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
        
        boardMatrix[coordList.get(0).y()][coordList.get(0).x()] = new CampFire(coordList.get(0));        
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
    	for (int i=0; i<coordList.size();i++) {
    		if (coordList.get(i).x()==indexX && coordList.get(i).y()==indexY) {
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
	 * Get the x coord of the Hero
	 * 
	 * @return the x coord of the Hero
	 */
	public int heroX(){
		return coordList.get(position).x();
    }

	/**
	 * Get the y coord of the Hero
	 * 
	 * @return the y coord of the Hero
	 */
	public int heroY(){
		return coordList.get(position).y();
    }
	
	/**
	 * Get the list of path cell
	 * 
	 * @return the list of path cell
	 */
	public ArrayList<Coord> coordList() {
		return coordList;
	}
	
	/**
	 * Move the Hero
	 * 
	 * @return true if the hero pass on the campfire, else false
	 */
	public boolean moveHero() {
		position +=1;
		hero.regenTurn();
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
	
	public static String chooseALoop() {
		Scanner scanner = new Scanner(System.in);
		String fileName = "";
		String file ="";
		do {
	        System.out.print("Indiquer le nom de la boucle que vous souhaitez utiliser :");

	        fileName = scanner.next();
	        file = "functional/"+fileName;
	        File f = new File(file);
			if(f.isFile())
			{ 
				break;
			}
		}while(true);
		return file;
	}
}
