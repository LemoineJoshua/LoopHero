package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class RatWolf extends AbstractMonster implements Serializable {

	private static final long serialVersionUID = -1333887583665839591L;

	/**
	 * RatWolf constructor
	 */
	public RatWolf() {
		super(16,3.6,0.0,0.0,0.05,0.10,0.0,(float)0.05,(float)0.6,new ArrayList<String>(),"pictures/Entities/ratWolf.png", "pictures/Entities/ratWolfFight.png", "pictures/Entities/ratWolfFight2.png", "pictures/Entities/ratWolfFight3.png", null);
		generateLootList();
		
	}
	
	/**
	 * Generate the lootList of the chest
	 */
	private void generateLootList(){
		drop.add("Living Fabric");
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
