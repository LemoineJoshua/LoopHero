package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Skeleton extends AbstractMonster implements Serializable{

	private static final long serialVersionUID = -8409607437734396624L;

	public Skeleton() {
		super(12,9,0.0,0.0,0.0,0.05,0.0,(float)0.00,(float)0.15,new ArrayList<String>(),"pictures/Entities/skeleton.png", "pictures/Entities/skeletonFight.png", "pictures/Entities/skeletonFight2.png", "pictures/Entities/skeletonFight3.png", null);
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
