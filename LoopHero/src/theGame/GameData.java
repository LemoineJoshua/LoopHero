package theGame;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import theGame.Cards.Card;
import theGame.boardGame.Board;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;

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
		return board.moveHero();
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
	
	

}
