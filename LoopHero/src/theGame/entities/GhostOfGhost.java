package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class GhostOfGhost extends AbstractMonster implements Serializable{

	public GhostOfGhost() {
		super(6,4,0.0,0.0,0.0,0.20,0.0,0,(float)1,new ArrayList<String>(),"", "pictures/Entities/ghostOfGhostFight.png", "pictures/Entities/ghostOfGhostFight2.png", "pictures/Entities/ghostOfGhostFight3.png", null);
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("PitifulRemains");
		drop.add("Shapeless Mass");
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
