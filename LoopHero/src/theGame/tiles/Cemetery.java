package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.Skeleton;

public class Cemetery extends AbstractRoad implements Serializable{

	private static final long serialVersionUID = -452076734928560442L;
	private int day = 0;
	

	/**
	 * The Cemetery constructor
	 * 	
	 * @param aliveMonster : the monster living on the tile
	 * @param position : the coords of the tile 
	 */
	public Cemetery(ArrayList<AbstractMonster> aliveMonster,Coord position) {
		super("pictures/Tiles/cemetery.png", aliveMonster,position);

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
	 * Spawn a skeleton on the cemetery, each three days
	 */
	@Override
	public void dailyEffect(Board board) {
		day+=1;
		if(day%3==0) {
			Skeleton skeleton= new Skeleton();
			this.addMob(skeleton);
		}
	}
	
	/**
	 * Add a pebbles and a memory fragment to the hero inventory, each time he come on the cemetery
	 */
	@Override
	public void enteringEffect(GameData gameData) {
		gameData.ressourcesInventory().addRessources("Pebbles",1 );
		gameData.ressourcesInventory().addRessources("MemoryFragment",1 );
	}

}
