package theGame;

import theGame.boardGame.Board;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;
import tools.Dictionnary;

public class GameData {
	private Board board = new Board();
	private CardInventory cardInventory = new CardInventory();
	private RessourcesInventory ressourcesInventory = new RessourcesInventory();
	private Dictionnary mobs;
	
	public GameData() {
		this.mobs = new Dictionnary();
		initDictMob();
		
	}
	
	private void initDictMob() {
		mobs.add("Slime", new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/slimeS.png",(float)0.05));
	}
	

	public void dailyEffect() {
		board.dailyEffect();
	}
	
	public Board board() {
		return board;
	}
	
	public void moveHero() {
		board.moveHero();
	}
	
	public boolean fight() {
		return board.combat();
	}
}
