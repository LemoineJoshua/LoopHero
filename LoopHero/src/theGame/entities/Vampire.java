package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Vampire extends AbstractMonster implements Serializable{

	private static final long serialVersionUID = -7831595932317950173L;

	/**
	 * Vampire constructor
	 */
	public Vampire() {
		super(18,5.8,0.0,0.0,0.0,0.10,0.0,1,(float)0.55,new ArrayList<String>(),"", "pictures/Entities/VampireFight.png", "pictures/Entities/VampireFight2.png", "pictures/Entities/VampireFight3.png", null);
		generateLootList();
		
	}
	
	/**
	 * Generate the lootList of the chest
	 */
	private void generateLootList(){
		drop.add("PitifulRemains");
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
		return true;
	}
}
