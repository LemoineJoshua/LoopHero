package theGame.inventories;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HeroStuff implements Serializable {
	private static final long serialVersionUID = 1514593066013996245L;
	private final HashMap<String,Item> inventory;
	
	/**
	 * Hero Stuff constructor
	 */
	public HeroStuff() {
		HashMap<String,Item> inventory = new HashMap<>();
		inventory.put("weapon", null);
		inventory.put("shield", null);
		inventory.put("armor", null);
		inventory.put("ring", null);
		
		this.inventory = inventory;
	}
	
	/**
	 * inventory accessor
	 * 
	 * @return the hero inventory
	 */
	public HashMap<String,Item> inventory(){
		return inventory;
	}
	
	/**
	 * Equip and item to the hero and modify the hero stats
	 * 
	 * @param stats : The hero current statistics
	 * @param newItem : The item, the hero is equipping
	 */
	public void equip(HashMap<String,Double> stats, Item newItem) {
		String toModify = newItem.type(); 
		Item oldItem = inventory.get(toModify);
		
		if(oldItem != null) {
			for(Map.Entry<String,Double> entree : stats.entrySet()){
				
				String statToModify=entree.getKey().toString();
				Double statToRemove=inventory.get(oldItem.type()).stats().get(statToModify);
				if(statToModify.equals("hp")) {
					continue;
				}
				stats.put(statToModify, stats.get(statToModify)-statToRemove);
			}
		}
		
		inventory.put(toModify, newItem);
		for(Map.Entry<String,Double> entree : stats.entrySet()){
			String statAModif=entree.getKey().toString();
			if(statAModif.equals("hp")) {
				continue;
			}
			Double statAAjouter=newItem.stats().get(statAModif);
			stats.put(statAModif, stats.get(statAModif)+statAAjouter);
		}
	}
	
	/**
	 * Get the item, at the indicated key
	 * 
	 * @param key : The key of the item we want to get
	 * 
	 * @return the item wanted
	 */
	public Item get(String key) {
		return inventory.get(key);
	}
	
}
