package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class PrimalMatter extends AbstractMonster implements Serializable{

	/**
	 * PrimalMatter constructor
	 */
	public PrimalMatter() {
		super(8,4.5,0.0,0.0,0.0,0.10,0.0,0,(float)1,new ArrayList<String>(),"", "pictures/Entities/primalMatterFight.png", "pictures/Entities/primalMatterFight2.png", "pictures/Entities/primalMatterFight3.png", null);
		generateLootList();
		
	}
	
	/**
	 * Generate the lootList of the chest
	 */
	private void generateLootList(){
		drop.add("PitifulRemains");
		drop.add("Shapeless Mass");
	}
	
	/**
	 * Deal the effect if the monster is near a vampire mansion
	 */
	@Override
	public void vampireNearby() {
		stats.put("vampirism",stats.get("vampirism")+0.1);
	}
	
	/**
	 * Check if the monster got a soul
	 * 
	 * @return true if the monster got a soul, else false
	 */
	@Override
	public boolean hasASoul() {
		return false;
	}

}
