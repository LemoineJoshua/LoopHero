package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class FieldOfBlade extends AbstractMonster implements Serializable{

	public FieldOfBlade() {
		super(15, 5, 0, 0, 0, 0.05, 0, 1, 36, new ArrayList<String>(), "","pictures/Entities/fieldOfBlade.png");
		generateLootList();
	}
	
	private void generateLootList(){
		drop.add("Ration");
		drop.add("Living Fabric");
		drop.add("Shapeless Mass");
	}

}
