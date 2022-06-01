package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Ghost extends AbstractMonster implements Serializable{

	public Ghost() {
		super(3,3,0.0,0.0,0.0,0.30,0.0,1,(float)1,new ArrayList<String>(),"", "pictures/Entities/ghostFight.png", null);
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
