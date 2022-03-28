package theGame;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;

public class GameData {
	private final Board board = new Board();
	private final CardInventory cardInventory = new CardInventory();
	private final RessourcesInventory ressourcesInventory = new RessourcesInventory();

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
}
