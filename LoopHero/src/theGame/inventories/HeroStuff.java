package theGame.inventories;

import java.util.HashMap;

public class HeroStuff {
	private final HashMap<String,Item> inventory;
	
	public HeroStuff() {
		
		HashMap<String,Item> inventory = new HashMap<>();
		inventory.put("weapon", null);
		inventory.put("shield", null);
		inventory.put("armor", null);
		inventory.put("ring", null);
		
		this.inventory = inventory;
	}
	
	public HashMap<String,Item> inventory(){
		return inventory;
	}
}
