package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Skeleton extends AbstractMonster implements Serializable{

	public Skeleton() {
		super(12,9,0.0,0.0,0.0,0.05,0.0,(float)0.00,(float)0.15,new ArrayList<String>(),"pictures/Entities/skeleton.png", "pictures/Entities/skeletonFight.png");
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
