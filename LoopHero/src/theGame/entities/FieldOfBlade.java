package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class FieldOfBlade extends AbstractMonster implements Serializable{

	/**
	 * FieldOfBlade constructor
	 */
	public FieldOfBlade() {
		super(15, 5, 0, 0, 0, 0.05, 0, 1, 36, new ArrayList<String>(), "","pictures/Entities/fieldOfBlade.png","pictures/Entities/fieldOfBlade2.png","pictures/Entities/fieldOfBlade3.png", null);
		generateLootList();
	}
	
	/**
	 * Generate the lootList of the chest
	 */
	private void generateLootList(){
		drop.add("Ration");
		drop.add("Living Fabric");
		drop.add("Shapeless Mass");
	}

	/**
	 * Check if the monster got a soul
	 * 
	 * @return true if the monster got a soul, else false
	 */
	@Override
	public boolean hasASoul() {
		return true;
	}
}
