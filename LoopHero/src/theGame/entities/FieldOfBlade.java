package theGame.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class FieldOfBlade extends AbstractMonster implements Serializable{

	public FieldOfBlade() {
		super(15, 5, 0, 0, 0, 0.05, 0, 1, 36, new ArrayList<String>(), "","pictures/Entities/fieldOfBlade.png","pictures/Entities/fieldOfBlade2.png","pictures/Entities/fieldOfBlade3.png", null);
		generateLootList();
	}
	
	private void generateLootList(){
		drop.add("Ration");
		drop.add("Living Fabric");
		drop.add("Shapeless Mass");
	}

	@Override
	public boolean hasASoul() {
		return true;
	}
}
