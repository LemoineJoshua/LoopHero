package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class GhostOfGhost extends AbstractMonster implements Serializable{

	private static final long serialVersionUID = -9008454403167128430L;

	/**
	 * GhostOfGhost constructor
	 */
	public GhostOfGhost() {
		super(6,4,0.0,0.0,0.0,0.20,0.0,0,(float)1,new ArrayList<String>(),"", "pictures/Entities/ghostOfGhostFight.png", "pictures/Entities/ghostOfGhostFight2.png", "pictures/Entities/ghostOfGhostFight3.png", null);
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
	
	@Override
	public boolean hasASoul() {
		return true;
	}

}
