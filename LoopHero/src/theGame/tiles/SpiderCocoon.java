package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.Monster;

public class SpiderCocoon extends AbstractTile {
	private final ArrayList<Coord> spawningTiles;
	
	/**
	 * SpiderCocoon constructor
	 * Initialize the mobs, the mobs that can spawn, and where the spiders can spawn
	 * 
	 * @param coord : it's coordinates on the board
	 * @param matrix : the board 
	 * @param aliveMonster : the monsters on the cell before the card is place
	 */
	public SpiderCocoon(Coord position,AbstractTile[][] matrix) {
		super("RoadSide","pictures/Tiles/SpiderCocoon.png");
		
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		ArrayList<Coord> spawningTiles = new ArrayList<>();
		
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof AbstractRoad) {
					spawningTiles.add(new Coord(position.x()+coord.x(),position.y()+coord.y()));
				}
			}
		}	
		
		this.spawningTiles=spawningTiles;
	} 
	
	/**
	 * Check if the cell is Empty, which mean we could place a card on it
	 * 
	 * @return true if the cell is empty, false else
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	
	/**
	 * Spawn a spider on  an adjacent tile, each days
	 */
	@Override
	public void dailyEffect(Board board) {
		ArrayList<String> drop = new ArrayList<>();
		drop.add("Living Fabric");
		Monster spider= new Monster(8,3.1,0.0,0.0,0.0,0.10,0.0,1,(float)0.55,drop,"pictures/Entities/spider.png", "pictures/Entities/spiderFight.png");
		
		Coord spawningTile = spawningTiles.get(Math.round((float) Math.random()*(spawningTiles.size()-1)));
		AbstractRoad tile = (AbstractRoad) board.boardMatrix()[spawningTile.y()][spawningTile.x()];
		tile.addMob(spider);		
	}
	
	

}
