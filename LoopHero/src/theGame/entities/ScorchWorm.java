package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ScorchWorm extends AbstractMonster implements Serializable{

	public ScorchWorm() {
		super(10,2.7,0.0,0.10,0.0,0.10,0.0,(float)0.00,(float)0.15,new ArrayList<String>(),"pictures/Entities/worm.png", "pictures/Entities/wormFight.png");
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
