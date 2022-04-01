package theGame;

import theGame.Cards.Card;
import theGame.boardGame.Board;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.Grove;
import theGame.tiles.Meadow;
import theGame.tiles.Rock;

public class GameData {
	private final Board board = new Board();
	private final CardInventory cardInventory = new CardInventory();
	private final RessourcesInventory ressourcesInventory = new RessourcesInventory();
	private Integer selectedCardIndex;

	/**
	 * appelle la fonction dailyEffect des cases stockées dans le board
	 */
	public void dailyEffect() {
		board.dailyEffect();
	}
	
	/**
	 * appelle la fonction loopEffect des cases stockées dans le board
	 */
	public void loopEffect() {
		board.loopEffect();
	}
	
	
	/**
	 * Bouge le hero sur la boucle
	 * 
	 * @return true si le hero passe sur le feu de camp, false sinon
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
	 * appelle la fonction fight du board
	 * 
	 * @return true si il y a eu un combat, false sinon
	 */
	public boolean fight() {
		return board.fight(ressourcesInventory,cardInventory);
	}
	
	/**
	 * @return true si une carte est sélectionée, false sinon
	 */
	public boolean aCardIsSelected() {
		return selectedCardIndex != null;
	}
	
	/**
	 * Deselectionne les cartes
	 */
	public void unselect() {
		selectedCardIndex = null;
	}
	
	/**
	 * Actualise la carte selectionnee
	 * 
	 * @param index position de la carte dans la main du joueur
	 */
	public void select(int index) {
		selectedCardIndex = index;
	}
	
	/**
	 * @return l'index, de la carte selectionee, dans la main du joueur
	 */
	public Integer selectedCardIndex() {
		return selectedCardIndex;
	}
	
	/**
	 * Place la carte selectionnee sur le plateau de jeu si elle peut être placée à l'endroit sélectionné, et renvoie true. 
	 * Sinon renvoie false,
	 * 
	 * @param indexY ligne de placement dans le plateau
	 * @param indexX colonne de placement dans le plateau
	 * 
	 * @return true si la carte est placée, false sinon.
	 */
	public boolean placeACard(int indexY, int indexX) {
		Card myCard = cardInventory.cardList().get(selectedCardIndex);
		if ((myCard.type()==board.boardMatrix()[indexY][indexX].type()) && board.boardMatrix()[indexY][indexX].isEmpty()) {
			
			switch (myCard.name()) {
				case "rock":
					board.boardMatrix()[indexY][indexX]=new Rock(board,indexY, indexX);
					break;
							
				case "meadow":
					board.boardMatrix()[indexY][indexX]=new Meadow(board,indexY, indexX);
					break;
					
				case "grove":
					AbstractRoad tile= (AbstractRoad) board.boardMatrix()[indexY][indexX];
					board.boardMatrix()[indexY][indexX]=new Grove(board.getIndexInLoop(indexY, indexX), tile.aliveMonster());
					break;
				}
			return true;
		}
		return false;
	}
	
	/**
	 * @return renvoie le plateau de jeu
	 */
	public Board board() {
		return board;
	}
	
	/**
	 * @return l'inventaire des ressources
	 */
	public RessourcesInventory ressourcesInventory() {
		return ressourcesInventory;
	}
	
	/**
	 * @return l'inventaire des cartes
	 */
	public CardInventory cardInventory() {
		return cardInventory;
	}

}
