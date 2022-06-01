package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ScareCrow extends AbstractMonster implements Serializable{

	public ScareCrow() {
		super(18,8.25,0.0,0.0,0.0,0.0,0.0,(float)0.00,(float)0.0,new ArrayList<String>(),"pictures/Entities/scareCrow.png", "pictures/Entities/scareCrowFight.png");
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Branches");
		drop.add("Ration");
		drop.add("Craft Fragment");
	}


}