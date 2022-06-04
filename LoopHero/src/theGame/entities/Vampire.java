package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Vampire extends AbstractMonster implements Serializable{

	public Vampire() {
		super(18,5.8,0.0,0.0,0.0,0.10,0.0,1,(float)0.55,new ArrayList<String>(),"", "pictures/Entities/VampireFight.png", "pictures/Entities/VampireFight2.png", "pictures/Entities/VampireFight3.png", null);
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("PitifulRemains");
	}
	
	@Override
	public void vampireNearby() {
		stats.put("vampirism",stats.get("vampirism")+0.1);
	}
	
	@Override
	public boolean hasASoul() {
		return true;
	}
}
