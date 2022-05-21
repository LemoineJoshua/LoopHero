package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Grove extends AbstractRoad {
	private final int position;
	private int day = 0;
	
	/**
	 * Grove constructor
	 * Initialize the mobs, the mobs that can spawn, and his position on the path
	 * 
	 * @param position : his position on the board
	 * @param aliveMonster : the monsters on the cell before the card is place
	 */
	public Grove(int position,ArrayList<Monster> aliveMonster) {
		super("pictures/Tiles/grove.png", aliveMonster);
		this.position = position;
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
	 * Spawn a ratwolf on the grove or an adjacent tile, each two days
	 */
	@Override
	public void dailyEffect(Board board) {
		ArrayList<String> drop = new ArrayList<>();
		drop.add("Living Fabric");
		Monster ratWolf= new Monster(16,3.6,0.0,0.0,0.05,0.10,0.0,(float)0.05,(float)0.6,drop,"pictures/Entities/ratWolf.png", "pictures/Entities/ratWolfFight.png");
		if(day%2==0) {
			double test = Math.random();
			
			AbstractRoad upperTile=null;
			AbstractRoad lowerTile=null;
			if(position<33){
				upperTile = (AbstractRoad) board.boardMatrix()[board.coordList()[position+1].y()][board.coordList()[position+1].x()];
			}
			if(position>1) {
				lowerTile = (AbstractRoad) board.boardMatrix()[board.coordList()[position-1].y()][board.coordList()[position-1].x()];
			}
			
			if(test>0.6 && lowerTile!=null && lowerTile.aliveMonster().size()<4){ 
				lowerTile.addMob(ratWolf);
			}else if(test>0.3 && upperTile!=null && upperTile.aliveMonster().size()<4) {
				upperTile.addMob(ratWolf);
			}else if(this.aliveMonster().size()<4) {
				this.addMob(ratWolf);
			}

		}
		day+=1;
	}
	
	/**
	 * Add a branch to the hero inventory, each time he come on the grove
	 */
	@Override
	public void enteringEffect(GameData gameData) {
		gameData.ressourcesInventory().addRessources("Branches",1 );
	}

}
