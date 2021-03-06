package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.RatWolf;

public class Grove extends AbstractRoad implements Serializable{

	private static final long serialVersionUID = -1881985447430094173L;
	private final int position;
	private int day = 0;
	
	/**
	 * Grove constructor
	 * Initialize the mobs, the mobs that can spawn, and his position on the path
	 * 
	 * @param position : his position on the board
	 * @param aliveMonster : the monsters on the cell before the card is place
	 * @param pos : the coord of the tile
	 */
	public Grove(int position,ArrayList<AbstractMonster> aliveMonster,Coord pos) {
		super("pictures/Tiles/grove.png", aliveMonster,pos);
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
		Objects.requireNonNull(board);
		day+=1;
		if(day%2==0) {
			RatWolf ratWolf= new RatWolf();
			double test = Math.random();
			
			AbstractRoad upperTile=null;
			AbstractRoad lowerTile=null;
			if(position<33){
				upperTile = (AbstractRoad) board.boardMatrix()[board.coordList().get(position+1).y()][board.coordList().get(position+1).x()];
			}
			if(position>1) {
				lowerTile = (AbstractRoad) board.boardMatrix()[board.coordList().get(position+1).y()][board.coordList().get(position+1).x()];
			}
			
			if(test>0.6 && lowerTile!=null && lowerTile.aliveMonster().size()<4){ 
				lowerTile.addMob(ratWolf);
			}else if(test>0.3 && upperTile!=null && upperTile.aliveMonster().size()<4) {
				upperTile.addMob(ratWolf);
			}else if(this.aliveMonster().size()<4) {
				this.addMob(ratWolf);
			}

		}
	}
	
	/**
	 * Add a branch to the hero inventory, each time he come on the grove
	 */
	@Override
	public void enteringEffect(GameData gameData) {
		Objects.requireNonNull(gameData);
		gameData.ressourcesInventory().addRessources("Branches",1 );
	}

}
