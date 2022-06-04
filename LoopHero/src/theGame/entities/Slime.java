package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Slime extends AbstractMonster implements Serializable{

	/**
	 * Slime constructor
	 */
	public Slime() {
		super(13,3.3,0.0,0.0,0.0,0.0,0.0,(float)0.05, (float) 0.65,new ArrayList<String>(),"pictures/Entities/slimeS.png", "pictures/Entities/slimeFight.png", "pictures/Entities/slimeFight2.png", "pictures/Entities/slimeFight3.png", null);
		generateLootList();
		
	}
	
	/**
	 * Generate the lootList of the chest
	 */
	private void generateLootList(){
		drop.add("Shapeless Mass");
		drop.add("Craft Fragment");
	}
}
