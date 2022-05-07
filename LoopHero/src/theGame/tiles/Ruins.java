package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Ruins extends AbstractRoad {
	private final int position;
	private int day = 0;
	private ArrayList<String> enteringDrop;
	

	/**
	 * @param position
	 * @param aliveMonster
	 */
	public Ruins(int position,ArrayList<Monster> aliveMonster) {
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
		ArrayList<String> drop = new ArrayList<>();
		drop.add("Living Fabric");		
		Monster worm= new Monster(10,2.7,0.0,0.10,0.0,0.10,0.0,(float)0.00,(float)0.15,drop,"pictures/Entities/worm.png", "pictures/Entities/wormFight.png");
		if(day%2==0) {
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
