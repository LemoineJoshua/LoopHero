package theGame;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import theGame.Cards.Card;
import theGame.boardGame.Board;
import theGame.entities.Monster;
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

	public void dailyEffect() {
		board.dailyEffect();
	}
	
	public void loopEffect() {
		board.loopEffect();
	}
	
	public Board board() {
		return board;
	}
	
	public boolean moveHero() {
		boolean heroHasMoved = board.moveHero();
		if (board.boardMatrix()[board.heroY()][board.heroX()] instanceof Grove) {
			Grove tile = (Grove) board.boardMatrix()[board.heroY()][board.heroX()];
			tile.enteringEffect(this);
		}
		
		return heroHasMoved;
	}
	
	public boolean fight() {
		return board.fight(ressourcesInventory,cardInventory);
	}
	
	public CardInventory cardInventory() {
		return cardInventory;
	}
	
	public boolean aCardIsSelected() {
		return selectedCardIndex != null;
	}
	
	public void unselect() {
		selectedCardIndex = null;
	}
	
	public void select(int index) {
		selectedCardIndex = index;
	}
	
	public Integer selectedCardIndex() {
		return selectedCardIndex;
	}
	
	public void placeACard(int indexY, int indexX) {
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
		}
	}
	
	public RessourcesInventory ressourcesInventory() {
		return ressourcesInventory;
	}

}
