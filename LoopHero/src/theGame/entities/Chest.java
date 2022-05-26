package theGame.entities;

import java.util.ArrayList;

public class Chest extends AbstractMonster{

	public Chest() {
		super(11,0.6,0.0,0.0,0.0,0.0,0.0,0,(float)0.0,new ArrayList<String>(),"pictures/Entities/mimic.png", "pictures/Entities/chestFight.png");
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Craft Fragment");
		drop.add("Branches");
	}

}
