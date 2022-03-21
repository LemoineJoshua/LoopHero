package theGame;

import theGame.boardGame.Board;
import theGame.entities.Monster;
import theGame.inventories.InventaireCarte;
import theGame.inventories.InventaireRessources;
import tools.Dictionnary;

public class GameData {
	private Board board = new Board();
	private InventaireCarte cardInventory = new InventaireCarte();
	private InventaireRessources ressourcesInventory = new InventaireRessources();
	private Dictionnary mobs;
	
	public GameData() {
		this.mobs = new Dictionnary();
		initDictMob();
		initBoard();
		
	}
	
	private void initDictMob() {
		mobs.add("Slime", new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/slimeS.png",(float)0.05));
	}
	
	private void addSpawnable(Monster mob, int x, int y) {
		board.addSpawnable(mob, x, y);
	}
	
	private void initBoard() {
		Monster slime = mobs.get("Slime");
		board.initSlime(slime);
	}
	
	public void SpawningTime() {
		board.SpawningTime();
	}
	
	public Board board() {
		return board;
	}
	
	public void moveHero() {
		board.moveHero();
	}
	
	public void fight() {
		board.combat();
	}
}
