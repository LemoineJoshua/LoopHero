package theGame.inventories;

import java.util.ArrayList;

public class ItemInventory {
	private final ArrayList<Item> inventory;
	
	/**
	 * ItemInventory constructor 
	 */
	public ItemInventory() {
		this.inventory=new ArrayList<>();
	}
	
	/**
	 * Item Inventory accessor
	 * 
	 * @return the item inventory
	 */
	public ArrayList<Item> itemInventory(){
		return inventory;
	}
	
	/**
	 * add an item and verify the length of the list
	 *  
	 * @param item : the item to add
	 */
	public void add(Item item) {
		System.out.println("le hero a gagné : "+item.type());
		inventory.add(item);
		overflowForbidden();
	}
	
	/**
	 * Remove the item at the indicated index
	 * 
	 * @param index : the index of the item to remove
	 */
	public void remove(int index){
        inventory.remove(index);
    }
	
	
	/**
	 * Remove the first item of the inventory if the inventory is full
	 */
	public void overflowForbidden() {
    	if (inventory.size()>12) {
    		inventory.remove(0);
    	}
    }
	
}
