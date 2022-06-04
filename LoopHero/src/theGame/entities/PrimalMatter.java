package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class PrimalMatter extends AbstractMonster implements Serializable{

	public PrimalMatter() {
		super(8,4.5,0.0,0.0,0.0,0.10,0.0,0,(float)1,new ArrayList<String>(),"", "pictures/Entities/primalMatterFight.png", "pictures/Entities/primalMatterFight2.png", "pictures/Entities/primalMatterFight3.png", null);
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
		return false;
	}

}
