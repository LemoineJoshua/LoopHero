package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import theGame.TimeData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.inventories.CardInventory;
import theGame.inventories.Item;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;

public class AbstractRoad extends AbstractTile implements Serializable{

	private static final long serialVersionUID = 1L;
	protected final ArrayList<AbstractMonster> aliveMonster;
	protected final Coord position;
	
	/**
	 * Abstract Road constructor
	 * Initialize the tile picture, her type and the mob thaht can spawn on it
	 * 
	 * @param img The tile picture
	 * @param aliveMonster The mob alive on the tile, before we place the card on it
	 */
	public AbstractRoad(String img, ArrayList<AbstractMonster> aliveMonster,Coord position) {
		super("Road",img);
		if (aliveMonster.isEmpty()) {
			this.aliveMonster = Objects.requireNonNull(new ArrayList<AbstractMonster>());
		}else {
			this.aliveMonster = Objects.requireNonNull(aliveMonster);
		}
		this.position = Objects.requireNonNull(position);
		
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
		Objects.requireNonNull(lootList);
		Objects.requireNonNull(cardInventory);
		Objects.requireNonNull(itemInventory);
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
		Objects.requireNonNull(newMonster);
        if(aliveMonster.size()<4) {
        	aliveMonster.add(newMonster);
        }
    }
	
	/**
	 * Check if there is a beacon nearby 
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 * @return true if there is a beacon nearby
	 */
	public boolean isBeaconNearby(Board board) {
		Objects.requireNonNull(board);
		Coord[] posibilities = {
				new Coord(position.x()-1,position.y()-2),new Coord(position.x(),position.y()-2),new Coord(position.x()+1,position.y()-2),
				new Coord(position.x()-2,position.y()-1),new Coord(position.x()-1,position.y()-1),new Coord(position.x(),position.y()-1),new Coord(position.x()+1,position.y()-1),new Coord(position.x()+2,position.y()-1),
				new Coord(position.x()-2,position.y()),new Coord(position.x()-1,position.y()),new Coord(position.x()+1,position.y()),new Coord(position.x()+2,position.y()),
				new Coord(position.x()-2,position.y()+1),new Coord(position.x()-1,position.y()+1),new Coord(position.x(),position.y()+1),new Coord(position.x()+1,position.y()+1),new Coord(position.x()+2,position.y()+1),
				new Coord(position.x()-1,position.y()+2),new Coord(position.x(),position.y()+2),new Coord(position.x()+1,position.y()+2),
		};
		
		for(Coord coord:posibilities) {		
			if((coord.y()>=0 && coord.y()<12) && (coord.x()>=0 && coord.x()<21)) {
				if(board.boardMatrix()[coord.y()][coord.x()].imABeacon()) {
					return true;
				}
			}	
		}
		
		return false;
	}
	
	/**
	 * Apply beacon effect if there is one nearby
	 * 
	 * @param timeData: Data related to the course of time in the game
	 * @param board: The board of the game, with data such as the hero, the board matrix, or the hero position
	 */
	public void beaconNearby(TimeData timeData,Board board) {
		Objects.requireNonNull(timeData);
		Objects.requireNonNull(board);
		if (isBeaconNearby(board)) {
			timeData.thereIsABeacon();
		}else {
			timeData.thereIsNoBeacon();
		}
	}
	
}
