package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class RatWolf extends AbstractMonster implements Serializable {

	public RatWolf() {
		super(16,3.6,0.0,0.0,0.05,0.10,0.0,(float)0.05,(float)0.6,new ArrayList<String>(),"pictures/Entities/ratWolf.png", "pictures/Entities/ratWolfFight.png");
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Living Fabric");
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
