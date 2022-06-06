package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Ghost extends AbstractMonster implements Serializable{

	private static final long serialVersionUID = 6485040656708639498L;

	/**
	 * Ghost constructor
	 */
	public Ghost() {
		super(3,3,0.0,0.0,0.0,0.30,0.0,1,(float)1,new ArrayList<String>(),"", "pictures/Entities/ghostFight.png", "pictures/Entities/ghostFight2.png", "pictures/Entities/ghostFight3.png", null);
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
