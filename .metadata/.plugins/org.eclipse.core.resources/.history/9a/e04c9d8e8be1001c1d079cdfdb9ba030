package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import theGame.GameData;
import theGame.entities.AbstractMonster;
import theGame.inventories.CardInventory;
import theGame.inventories.Item;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;

public class AbstractRoad extends AbstractTile implements Serializable{

	private final ArrayList<AbstractMonster> aliveMonster;
	
	/**
	 * Abstract Road constructor
	 * Initialize the tile picture, her type and the mob thaht can spawn on it
	 * 
	 * @param img The tile picture
	 * @param aliveMonster The mob alive on the tile, before we place the card on it
	 */
	public AbstractRoad(String img, ArrayList<AbstractMonster> aliveMonster) {
		super("Road",img);
		if (aliveMonster.isEmpty()) {
			this.aliveMonster = new ArrayList<AbstractMonster>();
		}else {
			this.aliveMonster = aliveMonster;
		}
		
	}
	
	
	/**
	 * Check if there is a monster on the tile to start fight
	 * 
	 * @return true if there is a fight, else false
	 */
	public boolean isFight() {
		return !aliveMonster.isEmpty();
	}
	
	/***
	 * Remove every mob on the tile at the end of a fight
	 * Give every loot, and the branches if it's a grove (because it's the only  card with this behavior)
	 * 
	 * @param lootList : All the resources collected by the hero
	 * @param cardInventory : All cards of the game, split in the deck, and the cards in the player's hand
	 * @param itemInventory : Inventory that contains all the equipment the hero has collected, which can now be equipped.
	 * @param loop : The current loop number
	 */
	public void clearMob(RessourcesInventory lootList,CardInventory cardInventory,ItemInventory itemInventory, int loop){
		int countBranches = 0;
		for(AbstractMonster mob:aliveMonster) {
			if(!mob.dropCard()) {
				
				Item item = Item.getAnItem(loop);
				itemInventory.add(item);
				
				
			}else {
				int nb = (int) Math.round(Math.random() * (cardInventory.deck().size()-1));
				cardInventory.cardList().add(cardInventory.deck().get(nb));
				cardInventory.overflowForbidden();
				cardInventory.deck().remove(nb);
			}
			if (Math.random()>0.5) {countBranches ++;}
			
			for(String loot:mob.drop()) { 
				lootList.addRessources(loot, 5);
			}
		}
				
		if (this instanceof Grove) {
			lootList.addRessources("Branches", 1+countBranches);
		}
		aliveMonster.clear();
	}
	
	/**
	 * aliveMonster accessor
	 * 
	 * @return the list of all living monster on this tile
	 */
	public ArrayList<AbstractMonster> aliveMonster(){
		 return aliveMonster;
	}
	
	/**
	 * Add a mob in the aliveMonster list
	 * 
	 * @param newMonster : the mob we want to add
	 */
	public void addMob(AbstractMonster newMonster){
        if(aliveMonster.size()<4) {
        	aliveMonster.add(newMonster);
        }
    }
	
	/**
	 * Deal with all effect that happened, when the hero come on a tile 
	 * 
	 * @param gameData : All the data used in the game
	 */
	public void enteringEffect(GameData gameData) {}
	
}
