package theGame.entities;

import java.util.ArrayList;

public class Slime extends AbstractMonster{

	public Slime() {
		super(13,3.3,0.0,0.0,0.0,0.0,0.0,(float)0.05, (float) 0.65,new ArrayList<String>(),"pictures/Entities/slimeS.png", "pictures/Entities/slimeFight.png");
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Shapeless Mass");
		drop.add("Craft Fragment");
	}
}
