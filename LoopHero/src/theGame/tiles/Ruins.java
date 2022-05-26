package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.entities.AbstractMonster;
import theGame.entities.ScorchWorm;

public class Ruins extends AbstractRoad {
	private final int position;
	private int day = 0;
	private ArrayList<String> enteringDrop;
	

	/**
	 * @param position
	 * @param aliveMonster
	 */
	public Ruins(int position,ArrayList<AbstractMonster> aliveMonster) {
		super("pictures/Tiles/ruins.png", aliveMonster);
		
		this.position = position;
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
		if(day%2==0) {
			ScorchWorm worm= new ScorchWorm();
			this.addMob(worm);
		}
		day+=1;
	}
	
	/**
	 * Add a random ressource shard to the hero inventory, each time he come on the cemetery
	 */
	@Override
	public void enteringEffect(GameData gameData) {

		int index = (int) (Math.random()*enteringDrop.size());
		gameData.ressourcesInventory().addRessources(enteringDrop.get(index),1 );
	}

}
