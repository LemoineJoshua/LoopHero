package theGame.inventories;

import java.util.HashMap;
import java.util.Map;

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
	
	public void equip(HashMap<String,Double> stats, Item aAjouter) {
		String aModifier = aAjouter.type();
		Item aVirer = inventory.get(aModifier);
		
		if(aVirer != null) {
			for(Map.Entry entree : stats.entrySet()){
				
				String statAModif=entree.getKey().toString();
				Double statAVirer=inventory.get(aVirer.type()).stats().get(statAModif);
				//System.out.println("la stats à modif est : "+statAModif+" | statAvirer : "+statAVirer);
				if(statAModif.equals("hp")) {
					continue;
				}
				stats.put(statAModif, stats.get(statAModif)-(int) Math.round(statAVirer));
				//System.out.println("la stat " + statAModif +" après modification : "+stats.get(statAModif));
			}
		}
		
		inventory.put(aModifier, aAjouter);
		for(Map.Entry entree : stats.entrySet()){
			//System.out.println(aAjouter);
			//System.out.println("la clef : " + entree.getKey() + "| la stat : "+entree.getValue());
			String statAModif=entree.getKey().toString();
			if(statAModif.equals("hp")) {
				continue;
			}
			Double statAAjouter=aAjouter.stats().get(statAModif);
			stats.put(statAModif, stats.get(statAModif)+(int) Math.round(statAAjouter));
		}
	}
	
	public Item get(String key) {
		return inventory.get(key);
	}
	
}
