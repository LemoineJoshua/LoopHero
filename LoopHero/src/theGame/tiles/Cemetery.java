package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Cemetery extends AbstractRoad {
	private final int position;
	private int day = 0;
	

	/**
	 * @param position
	 * @param aliveMonster
	 */
	public Cemetery(int position,ArrayList<Monster> aliveMonster) {
		super("pictures/Tiles/cemetery.png", aliveMonster);
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
	 * Spawn a skeleton on the cemetery, each three days
	 */
	@Override
	public void dailyEffect(Board board) {

		ArrayList<String> drop = new ArrayList<>();
		drop.add("PitifulRemains");
		Monster skeleton= new Monster(12,9,0.0,0.0,0.0,0.05,0.0,(float)0.00,(float)0.15,drop,"pictures/Entities/skeleton.png", "pictures/Entities/skeletonFight.png");
		if(day%3==0) {
			this.addMob(skeleton);
		}
		day+=1;
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
