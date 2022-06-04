package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Mimic extends AbstractMonster implements Serializable{

	public Mimic() {
		super(26,7,0.0,0.05,0.3,0.05,0.0,0,(float)0.60,new ArrayList<String>(),"pictures/Entities/mimic.png", "pictures/Entities/mimicFight.png", "pictures/Entities/mimicFight2.png", "pictures/Entities/mimicFight3.png", null);
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Living Fabric");
		drop.add("Branches");
	}
	
	@Override
	public void vampireNearby() {
		stats.put("vampirism",stats.get("vampirism")+0.1);
	}

}
