package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Chest extends AbstractMonster implements Serializable{

	/**
	 * Chest constructor
	 */
	public Chest() {
		super(11,0,0.0,0.0,0.0,0.0,0.0,0,(float)0.0,new ArrayList<String>(),"pictures/Entities/mimic.png", "pictures/Entities/chestFight.png", "pictures/Entities/chestFight.png", "pictures/Entities/chestFight3.png", null);
		generateLootList();
		
	}
	
	/**
	 * Generate the lootList of the chest
	 */
	private void generateLootList(){
		drop.add("Craft Fragment");
		drop.add("Branches");
	}

}
