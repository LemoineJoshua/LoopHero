package theGame;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;

public class GameData {
	private Board board = new Board();
	private CardInventory cardInventory = new CardInventory();
	private RessourcesInventory ressourcesInventory = new RessourcesInventory();

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
}
