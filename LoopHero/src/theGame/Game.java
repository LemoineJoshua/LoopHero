package theGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import theGame.TimeData;
import theGame.boardGame.Coord;
import theGame.boardGame.Board;


public class Game {
	
	private ApplicationContext ctx; //on stocke le contexte directement dans la classe pour pas avoir à l'appeler
	private GameView gameView;
	private GameData gameData;
	private TimeData timeData;
	
	private void moveHeroAndDraw(ApplicationContext context) {
		if (gameData.board().hero().isDead()) {
			System.out.println("Oh non le hero est mort, dommage");
			ctx.exit(0);
		}
		if (timeData.elapsedHero() >= TimeData.HERO_DELAY) {
			if(gameData.moveHero()) {
				gameData.loopEffect();
			}
			
			gameView.drawFrame(context, gameData, timeData);
			timeData.resetElapsedHero();
			
			if(gameData.fight()) {
				timeData.fight();
			}
		}
	}
	
	private void dayAction(ApplicationContext ctx) {
		if(timeData.elapsedDay()>= TimeData.DAY_MILLISECONDS) {
			gameData.dailyEffect();
			gameView.drawFrame(ctx, gameData,timeData);
			timeData.resetelapsedDay();
		}
	}
	

	public void Run(ApplicationContext ctx) { //Application ctx c'est une variable rentrée automatiquement par Application.run
		
		this.ctx = ctx;		
		this.gameView = new GameView(ctx);		
		this.gameData = new GameData();
		this.timeData = new TimeData();
				
		while(true) {
			
			Update(); // Fonction execute a chaque frame
			
		}
	}
	
	private void doKeyAction(Event e) {
		switch(e.getKey()) {//on récupère une touche
		
		case SPACE -> { //si c'est espace on arrette tout (je sais pas pourquoi la flèche)
			ctx.exit(0);
		}
		
		case LEFT -> timeData.slower();
		
		case RIGHT -> timeData.faster();
		
		case S -> timeData.stop();
		
		case D -> startTime();
		
		default -> System.out.println("touche inactive "+e.getKey()); //sinon on dit quelle touche on à appuyer

		}
		
	}
	
	private void doMouseAction(Event e) {
		if (!gameData.aCardIsSelected()) { // no cell is selected
			Point2D.Float location = e.getLocation();
			if (gameView.clickInCardZone(location)) {
				gameData.select(gameView.selectCard(location.x));
				if(gameData.selectedCardIndex()>gameData.cardInventory().cardList().size()-1) {
					gameData.unselect();
				}
			}
		} else {
			Point2D.Float location = e.getLocation();
			if (gameView.clickInBoardZone(location)) {
				int indexY = gameView.selectLine(location.y);
				int indexX = gameView.selectColumn(location.x);
				gameData.placeACard(indexY,indexX);
				gameData.cardInventory().addCardInDeck(gameData.cardInventory().cardList().get(gameData.selectedCardIndex()));
				gameData.cardInventory().remove(gameData.selectedCardIndex());
				}		
				
			gameData.unselect();
		}
	}
	
	private void startTime() {
		timeData.start();
		gameData.unselect();
	}
	
	private void Update() {
		
		moveHeroAndDraw(ctx);
		dayAction(ctx);
		
		Event e = ctx.pollOrWaitEvent(200);//on récupère tout evenement sur la machine (temps d'attente max 200 millisecondes)
		
		if(e == null) return; //200 milliseconde c'est court du coup y'a moyen que l'event soit null et donc qu'on puisse pas en tirer de touche
		
		
		switch (e.getAction()) {
			case KEY_PRESSED:
				doKeyAction(e);
				break;
			case POINTER_DOWN:
				if (timeData.stopped()) {
					doMouseAction(e);
				}
				break;
		}
		
		gameView.drawFrame(ctx, gameData, timeData);
		
	}
	
	
}
