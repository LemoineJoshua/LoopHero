package theGame;

import theGame.Cards.Card;
import theGame.boardGame.Board;
import theGame.inventories.CardInventory;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.Cemetery;
import theGame.tiles.Grove;
import theGame.tiles.Meadow;
import theGame.tiles.Rock;

public class GameData {
	private final Board board = new Board();
	private final CardInventory cardInventory = new CardInventory();
	private final RessourcesInventory ressourcesInventory = new RessourcesInventory();
	private Integer selectedCardIndex;
	private final ItemInventory itemInventory = new ItemInventory();
	private Integer selectedItemIndex;

	/**
	 * Call the function dailyEffect of all board case
	 */
	public void dailyEffect() {
		board.dailyEffect();
	}
	
	/**
	 * Call the function loopEffect of all board case
	 */
	public void loopEffect() {
		board.loopEffect();
	}
	
	
	/**
	 * Move the Hero on the board d=foolowing the Path
	 * 
	 * @return true if the hero pass on campfire, false else
	 */
	public boolean moveHero() {
		boolean heroHasMoved = board.moveHero();
		if (board.boardMatrix()[board.heroY()][board.heroX()] instanceof Grove) {
			Grove tile = (Grove) board.boardMatrix()[board.heroY()][board.heroX()];
			tile.enteringEffect(this);
		}
		
		return heroHasMoved;
	}
	
	/**
	 * Call the function fight from the board 
	 * 
	 * @return true if there is a fight, else false
	 */
	public boolean isFight() {
		return board.isFight(ressourcesInventory,cardInventory);
	}
	
	/**
	 * Check if an item is selected 
	 * 
	 * @return true if an item is selected 
	 */
	public boolean anItemIsSelected() {
		return selectedItemIndex != null;
	}
	
	/**
	 * Unselect the current selected item,  by setting the index to null
	 */
	public void unselectItem() {
		selectedItemIndex = null;
	}
	
	/**
	 * Select an item, by putting the value of selected item to the index of the item
	 * 
	 * @param index : The index of the item we want to select
	 */
	public void selectItem(int index) {
		selectedItemIndex = index;
	}
	
	/**
	 * Item Selected Index Accessor
	 * 
	 * @return the selected item index in the list
	 */
	public Integer selectedItemIndex() {
		return selectedItemIndex;
	}
	
	/**
	 * Check if a card is selected
	 * 
	 * @return true if a card is selected, else return false
	 */
	public boolean aCardIsSelected() {
		return selectedCardIndex != null;
	}
	
	/**
	 * Unselect the current selected card
	 */
	public void unselectCard() {
		selectedCardIndex = null;
	}
	
	/**
	 * Select the card at the indicated index
	 * 
	 * @param index : Index in the list of the card we want to select
	 */
	public void selectCard(int index) {
		selectedCardIndex = index;
	}
	
	/**
	 * Selected Card Index Accessor
	 * 
	 * @return the index of the current selected card
	 */
	public Integer selectedCardIndex() {
		return selectedCardIndex;
	}
	
	/**
	 * Place the selected card, on the board, if the card can be placed at the cell, the player choose.
	 * 
	 * @param indexY : The ligne of the cell, where we want to place the card
	 * @param indexX : The column of the cell, where we want to place the card
	 * @return true if the card is placed, else false
	 */
	public boolean placeACard(int indexY, int indexX) {
		Card myCard = cardInventory.cardList().get(selectedCardIndex);
		if ((myCard.type()==board.boardMatrix()[indexY][indexX].type()) && board.boardMatrix()[indexY][indexX].isEmpty()) {
			
			switch (myCard.name()) {
				case "rock":
					board.boardMatrix()[indexY][indexX]=new Rock(board,indexY, indexX);
					ressourcesInventory.addRessources("Pebbles", 1);
					break;
							
				case "meadow":
					board.boardMatrix()[indexY][indexX]=new Meadow(board,indexY, indexX);
					ressourcesInventory.addRessources("Ration", 1);
					break;
					
				case "grove":
					AbstractRoad tile= (AbstractRoad) board.boardMatrix()[indexY][indexX];
					board.boardMatrix()[indexY][indexX]=new Grove(board.getIndexInLoop(indexY, indexX), tile.aliveMonster());
					break;
					
				case "cemetery":
					AbstractRoad tile2= (AbstractRoad) board.boardMatrix()[indexY][indexX];
					board.boardMatrix()[indexY][indexX]=new Cemetery(board.getIndexInLoop(indexY, indexX), tile2.aliveMonster());
					break;
				}
			return true;
		}
		return false;
	}
	
	/**
	 * Board Accessor
	 * 
	 * @return the game board
	 */
	public Board board() {
		return board;
	}
	
	/**
	 * Ressources Inventory Acesory
	 * 
	 * @return the ressources inventory
	 */
	public RessourcesInventory ressourcesInventory() {
		return ressourcesInventory;
	}
	
	/**
	 * Card Inventory Accessor
	 * 
	 * @return the card inventory
	 */
	public CardInventory cardInventory() {
		return cardInventory;
	}
	
	/**
	 * Item Inventory Accessor
	 * 
	 * @return the item inventory
	 */
	public ItemInventory itemInventory() {
		return itemInventory;
	}

}
