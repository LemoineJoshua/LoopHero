package theGame;

import java.io.Serializable;

import theGame.Cards.Card;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.inventories.CardInventory;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.BattleField;
import theGame.tiles.Beacon;
import theGame.tiles.CampFire;
import theGame.tiles.Cemetery;
import theGame.tiles.EmptyField;
import theGame.tiles.EmptyRoadSide;
import theGame.tiles.Grove;
import theGame.tiles.Meadow;
import theGame.tiles.Rock;
import theGame.tiles.Ruins;
import theGame.tiles.SpiderCocoon;
import theGame.tiles.VampireMansion;
import theGame.tiles.Village;
import theGame.tiles.Wastelands;
import theGame.tiles.WheatFields;

public class GameData implements Serializable{
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
	public boolean moveHero(TimeData timeData) {
		boolean heroHasMoved = board.moveHero();
		AbstractRoad actual = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
		actual.beaconNearby(timeData, board);
		if ( actual instanceof Grove) {
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
		if ((myCard.type()==board.boardMatrix()[indexY][indexX].type()&& board.boardMatrix()[indexY][indexX].isEmpty())||(myCard.type()=="Oblivion" && board.boardMatrix()[indexY][indexX].isOblivionable())) {
			Coord position = new Coord(indexX,indexY);
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
					board.boardMatrix()[indexY][indexX]=new Grove(board.getIndexInLoop(indexY, indexX), tile.aliveMonster(),position);
					break;
					
				case "cemetery":
					AbstractRoad tile2= (AbstractRoad) board.boardMatrix()[indexY][indexX];
					board.boardMatrix()[indexY][indexX]=new Cemetery(tile2.aliveMonster(),position);
					break;
					
				case "ruins":
					AbstractRoad tile3= (AbstractRoad) board.boardMatrix()[indexY][indexX];
					board.boardMatrix()[indexY][indexX]=new Ruins(tile3.aliveMonster(),position);
					break;
					
				case "oblivion":
					board.boardMatrix()[indexY][indexX].removingEffect(board);
					if (board.boardMatrix()[indexY][indexX].type()=="Road") {
						board.boardMatrix()[indexY][indexX]=new Wastelands(position);
					}else if(board.boardMatrix()[indexY][indexX].type()=="RoadSide") {
						board.boardMatrix()[indexY][indexX]=new EmptyRoadSide();
					}else if(board.boardMatrix()[indexY][indexX].type()=="Field") {
						board.boardMatrix()[indexY][indexX]=new EmptyField();
					}
					board.boardMatrix()[indexY][indexX].searchMeadowtoUnbloom(board, indexX, indexY);
					break;
					
				case "spiderCocoon":
					board.boardMatrix()[indexY][indexX]=new SpiderCocoon(new Coord(indexX,indexY),board.boardMatrix());
					break;
					
				case "vampireMansion":
					board.boardMatrix()[indexY][indexX]=new VampireMansion();
					break;
				
				case "battleField":
					board.boardMatrix()[indexY][indexX]=new BattleField(new Coord(indexX,indexY),board.boardMatrix());
					break;
					
				case "village":
					board.boardMatrix()[indexY][indexX]=new Village(position);
					break;
				
				case "wheatFields":
					board.boardMatrix()[indexY][indexX]=new WheatFields(position);
					break;
				
				case "beacon":
					board.boardMatrix()[indexY][indexX]=new Beacon();
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
