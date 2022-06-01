package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Chest extends AbstractMonster implements Serializable{

	public Chest() {
		super(11,0.6,0.0,0.0,0.0,0.0,0.0,0,(float)0.0,new ArrayList<String>(),"pictures/Entities/mimic.png", "pictures/Entities/chestFight.png", null);
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Craft Fragment");
		drop.add("Branches");
	}

}
