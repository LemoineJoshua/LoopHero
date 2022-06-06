package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ScorchWorm extends AbstractMonster implements Serializable{

	private static final long serialVersionUID = 2310854785152805768L;

	/**
	 * ScorchWorm constructor
	 */
	public ScorchWorm() {
		super(10,2.7,0.0,0.10,0.0,0.10,0.0,(float)0.00,(float)0.15,new ArrayList<String>(),"pictures/Entities/worm.png", "pictures/Entities/wormFight.png", "pictures/Entities/wormFight2.png", "pictures/Entities/wormFight3.png", null);
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
