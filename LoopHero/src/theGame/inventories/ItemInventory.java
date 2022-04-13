package theGame.inventories;

import java.util.ArrayList;

public class ItemInventory {
	private final ArrayList<Item> inventory;
	
	/**
	 * Constructeur de ItemInventory
	 */
	public ItemInventory() {
		this.inventory=new ArrayList<>();
	}
	
	/**
	 * @return l'inventaire des items
	 */
	public ArrayList<Item> itemInventory(){
		return inventory;
	}
	
	/**
	 * @param item
	 * 
	 * ajoute un item et v�rrifie la taille de la liste
	 */
	public void add(Item item) {
		inventory.add(item);
		overflowForbidden();
	}
	
	
	/**
	 * enleve le premier item de l'inventaire si ce dernier est plein
	 */
	public void overflowForbidden() {
    	if (inventory.size()>12) {
    		inventory.remove(0);
    	}
    }
}
