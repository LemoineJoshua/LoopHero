package theGame;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import fight.Fight;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import theGame.boardGame.Coord;
import theGame.inventories.Item;

public class Game {
	
	private ApplicationContext ctx; 
	private GameView gameView;
	private GameData gameData;
	private TimeData timeData;
	private boolean doIntro = true;
	private boolean newGame;
	
	/**
	 * Main function of the Game : 
	 * Create objects which manage the display, the data and the time
	 * Then start the Game, with the funtion update (which update the screen)
	 * 
	 * @param ctx : Global context of the game
	 */
	public void Run(ApplicationContext ctx) { 
			
		this.ctx=ctx;
		this.gameView = new GameView(ctx);		
		this.timeData = new TimeData();
		
		
		while(doIntro) {
			gameView.drawIntro(ctx);
			doIntroAction();
		}
		
		if(newGame) {
			this.gameData = new GameData();
		}else {
			this.gameData = getting();
		}
		
		
		
		while(true) {
			Update(); 
		}
		
	}
	
	/**
	 * Update the screen display, and the differents data
	 */
	private void Update() {
		
		moveHeroAndDraw(ctx);
		dayAction(ctx);
		
		Event e = ctx.pollOrWaitEvent(200);
		
		if(e == null) return; 
		
		
		switch (e.getAction()) {
			case KEY_PRESSED:
				doKeyAction(e);
				break;
			case POINTER_DOWN:
				if (timeData.isStopped()) {
					doMouseAction(e);
				}
				break;
			default:
				break;
		}
		
		gameView.drawFrame(ctx, gameData, timeData);
		
	}
	
	
	
	/**
	 * Move the Hero on the board, 
	 * activate the loop effect, if the hero pass on the campfire
	 * and launch the fight, if there is one
	 * 
	 * @param context : Global context of the game
	 */
	private void moveHeroAndDraw(ApplicationContext context) {
		if (timeData.elapsedHero() >= TimeData.HERO_DELAY) {
			if(gameData.moveHero(timeData)) {
				gameData.loopEffect();
				saving(gameData);
			}
			
			gameView.drawFrame(context, gameData, timeData);
			timeData.resetElapsedHero();
			
			if(gameData.isFight()) {
				
				Fight fight = new Fight(gameView,gameData.board(),gameData.cardInventory(),gameData.ressourcesInventory(),gameData.itemInventory(),ctx);
				if(!fight.doFight((int) timeData.fightModifier())) {
					System.out.println("Oh non le hero est mort, dommage");
					ctx.exit(0);
				}
				timeData.elapsedFight();
			}
		}
	}
	
	/**
	 * Pass to the next day, if enough time has passed
	 * and activate the day effect if needed
	 * 
	 * @param ctx : Global context of the game
	 */
	private void dayAction(ApplicationContext ctx) {
		if(timeData.elapsedDay()>= TimeData.DAY_MILLISECONDS) {
			gameData.dailyEffect();
			gameView.drawFrame(ctx, gameData,timeData);
			timeData.resetelapsedDay();
		}
	}
	

	
	
	/**
	 * Function which trigger an action based on the key pressed
	 * 
	 * @param e : an event which represents the key pressed
	 */
	private void doKeyAction(Event e) {
		switch(e.getKey()) {// we get the key pressed
		
		case SPACE -> { // if it's the space bar, we stop the game
			ctx.exit(0);
		}
		
		case LEFT -> timeData.slower();
		
		case RIGHT -> timeData.faster();
		
		case S -> timeData.stop();
		
		case D -> startTime();
		
		case A -> timeData.setFightPrintSpeed(1);
		
		case Z -> timeData.setFightPrintSpeed(2);
		
		case E -> timeData.setFightPrintSpeed(3);
		
		default -> System.out.println("touche inactive "+e.getKey()); 

		}
		
	}
	
	/**
	 * Function which trigger an action based on where the player clicked
	 * 
	 * @param e : an event which represents the mouse pressed
	 */
	private void doMouseAction(Event e) {
		if (!gameData.aCardIsSelected()) { // If player hasn't selected a card before
			Point2D.Float location = e.getLocation();
			if (gameView.clickInCardZone(location)) {	// if the click is in the zone where are stocked the card
				gameData.selectCard(gameView.selectCard(location.x));
				if(gameData.selectedCardIndex()>gameData.cardInventory().cardList().size()-1) {
					gameData.unselectCard();
					gameData.unselectItem();
				}
			}
		}else {					// If the player has selected a card before
			Point2D.Float location = e.getLocation();
			if (gameView.clickInBoardZone(location)) { // Check if the click of the player is somewhere on the board, in order to place the card
				int indexY = gameView.selectLine(location.y);
				int indexX = gameView.selectColumn(location.x);
				if (gameData.placeACard(indexY,indexX)) {
					gameData.cardInventory().addCardInDeck(gameData.cardInventory().cardList().get(gameData.selectedCardIndex()));
					gameData.cardInventory().remove(gameData.selectedCardIndex());
					}
				}		
				
			gameData.unselectCard();
			gameData.unselectItem();
		}
		
		if (!gameData.anItemIsSelected()) { // If player hasn't selected an item to equip before
			Point2D.Float location = e.getLocation();
			if (gameView.clickInItemInventoryZone(location)) {	// if the click is in the zone where are stocked the items
				gameData.selectItem(gameView.selectItemInInventory(location.x, location.y)); 
				if(gameData.selectedItemIndex()>gameData.itemInventory().itemInventory().size()-1) {
					gameData.unselectItem();
					gameData.unselectCard();
				}
			}
		}else {				// If the player has selected an item before
			System.out.println("Item sélectionné");
			Point2D.Float location = e.getLocation();
			if (gameView.clickInStuffZone(location)) { 	// Check if the click of the player is in the hero stuff, in order to equip the item
				String key = gameView.getItemKey(location.x);
				Item selectedItem = gameData.itemInventory().itemInventory().get(gameData.selectedItemIndex());
				if (key.equals(selectedItem.type())){
					gameData.board().hero().equip(selectedItem);
					gameData.itemInventory().remove(gameData.selectedItemIndex());
				}			
			}
			gameData.unselectItem();
			gameData.unselectCard();
		}			
	}
	
	/**
	 * Function that relaunch the passage of time 
	 */
	private void startTime() {
		timeData.start();
		gameData.unselectCard();
		gameData.unselectItem();
	}
	
	
	
	/**
	 * save the game in a file
	 * 
	 * @param gameData the actual gameData
	 */
	private void saving(GameData gameData) {
		try (OutputStream back = Files.newOutputStream(Path.of("functional/save"));
				ObjectOutputStream out = new ObjectOutputStream(back)){
					out.writeObject(gameData);
			}catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Fonction used to get a save
	 * 
	 * @return a gamedata from the save file
	 */
	private GameData getting() {
		GameData retour=null;
		
		try( InputStream back = Files.newInputStream(Path.of("functional/save"));
			ObjectInputStream in = new ObjectInputStream(back)){
			
				retour = (GameData) in.readObject();
				
			}catch(IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		return retour;
	}
	
	public void doIntroAction() {
		Event e = ctx.pollOrWaitEvent(200);
		
		if(e == null) return; 
		
		switch (e.getAction()) {
			case POINTER_DOWN:
				Point2D.Float location = e.getLocation();
				if(gameView.onContinueButton(location)) {
					if(Files.exists(Path.of("functional/save"))) {
						doIntro=false;
						newGame=false;
					}else {
						gameView.noSave();
					}
				}else if(gameView.onNewButton(location)) {
						doIntro=false;
						newGame=true;
				}
				break;
			case KEY_PRESSED:
				if(e.getKey().equals(KeyboardKey.SPACE)) {
					ctx.exit(0);
				}
				break;
			default:
				break;
		}		
	}
}
