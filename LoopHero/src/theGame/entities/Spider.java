package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Spider extends AbstractMonster implements Serializable{

	/**
	 * Spider constructor
	 */
	public Spider() {
		super(8,3.1,0.0,0.0,0.0,0.10,0.0,1,(float)0.55,new ArrayList<String>(),"pictures/Entities/spider.png", "pictures/Entities/spiderFight.png", "pictures/Entities/spiderFight2.png", "pictures/Entities/spiderFight3.png", null);
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

}
