package theGame.inventories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ItemInventory implements Serializable{
	private static final long serialVersionUID = -8227853852813932747L;
	private final ArrayList<Item> inventory;
	
	/**
	 * ItemInventory constructor 
	 */
	public ItemInventory() {
		this.inventory=Objects.requireNonNull(new ArrayList<Item>());
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
		Objects.requireNonNull(item);
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
