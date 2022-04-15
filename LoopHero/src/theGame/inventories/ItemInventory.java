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
	 * ajoute un item et vérrifie la taille de la liste
	 */
	public void add(Item item) {
		System.out.println("le hero a gagné : "+item.type());
		inventory.add(item);
		overflowForbidden();
	}
	
	public void remove(int index){
        inventory.remove(index);
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
