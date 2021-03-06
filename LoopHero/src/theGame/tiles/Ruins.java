package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.ScorchWorm;

public class Ruins extends AbstractRoad implements Serializable{
	private static final long serialVersionUID = 7640178772584982252L;
	private int day = 0;
	private ArrayList<String> enteringDrop;
	
	/**
	 * Ruins constructor
	 * 
	 * @param aliveMonster : the monsters living on the tile
	 * @param position : the coords of the tile
	 */
	public Ruins(ArrayList<AbstractMonster> aliveMonster,Coord position) {
		super("pictures/Tiles/ruins.png", aliveMonster,position);
		
		ArrayList<String> enteringDrop = new ArrayList<String>();
		enteringDrop.add("Pebbles");
		enteringDrop.add("Branches");
		enteringDrop.add("ScrapMetal");
		enteringDrop.add("Ration");
		enteringDrop.add("Pebbles");
		enteringDrop.add("MemoryFragment");
		enteringDrop.add("NoticeableChange");
		enteringDrop.add("PitifulRemains");
		enteringDrop.add("Craft Fragment");
		enteringDrop.add("Living Fabric");
		this.enteringDrop=enteringDrop;
		
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
	 * Spawn a scorch worms in the ruins, each two days
	 */
	@Override
	public void dailyEffect(Board board) {	
		Objects.requireNonNull(board);
		day+=1;
		if(day%2==0) {
			ScorchWorm worm= new ScorchWorm();
			this.addMob(worm);
		}
	}
	
	/**
	 * Add a random ressource shard to the hero inventory, each time he come on the cemetery
	 */
	@Override
	public void enteringEffect(GameData gameData) {
		Objects.requireNonNull(gameData);
		int index = (int) (Math.random()*enteringDrop.size());
		gameData.ressourcesInventory().addRessources(enteringDrop.get(index),1 );
	}

}
