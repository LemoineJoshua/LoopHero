package theGame;

import java.awt.geom.Point2D;

import fight.Fight;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;

public class Game {
	
	private ApplicationContext ctx; 
	private GameView gameView;
	private GameData gameData;
	private TimeData timeData;
	
	/**
	 * Fait bouger le hero sur le plateau, l'effet de loop si il passe sur le 
	 * feu de camp, et le combat si il y en a un
	 * 
	 * @param context contexte de l'Application
	 */
	private void moveHeroAndDraw(ApplicationContext context) {
		if (timeData.elapsedHero() >= TimeData.HERO_DELAY) {
			if(gameData.moveHero()) {
				gameData.loopEffect();
			}
			
			gameView.drawFrame(context, gameData, timeData);
			timeData.resetElapsedHero();
			
			if(gameData.isFight()) {
				
				Fight fight = new Fight(timeData,gameView,gameData.board(),gameData.cardInventory(),gameData.ressourcesInventory());
				if(!fight.doFight()) {
					System.out.println("Oh non le hero est mort, dommage");
					ctx.exit(0);
				}
			}
		}
	}
	
	/**
	 * Passe le jour si suffisament de temps s'est �coul�
	 * et applique l'effet du jour dans ce cas.
	 * 
	 * @param ctx contexte de l'Application
	 */
	private void dayAction(ApplicationContext ctx) {
		if(timeData.elapsedDay()>= TimeData.DAY_MILLISECONDS) {
			gameData.dailyEffect();
			gameView.drawFrame(ctx, gameData,timeData);
			timeData.resetelapsedDay();
		}
	}
	

	/**
	 * Fonction principale du jeu:
	 * cr�e les objets qui g�rent l'affichage/les donn�es/le temps
	 * Puis lance le jeux � travers la fonction Update (qui actualise l'�cran)
	 * 
	 * @param ctx le contexte de l'Application
	 */
	public void Run(ApplicationContext ctx) { 
			
		this.ctx=ctx;
		this.gameView = new GameView(ctx);		
		this.gameData = new GameData();
		this.timeData = new TimeData();
		
		while(true) {
			
			Update(); 
			
		}
	}
	
	/**
	 * Fonction qui d�clenche une action en fonction de la touche appuy�e
	 * 
	 * @param e touche appuy�e 
	 */
	private void doKeyAction(Event e) {
		switch(e.getKey()) {//on r�cup�re une touche
		
		case SPACE -> { //si c'est espace on arrette tout (je sais pas pourquoi la fl�che)
			ctx.exit(0);
		}
		
		case LEFT -> timeData.slower();
		
		case RIGHT -> timeData.faster();
		
		case S -> timeData.stop();
		
		case D -> startTime();
		
		default -> System.out.println("touche inactive "+e.getKey()); //pour le debug

		}
		
	}
	
	/**
	 * Fonction ui d�clenche une action en fonction de ou clique
	 * la sourie
	 * 
	 * @param e evenement de la sourie
	 */
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
				if (gameData.placeACard(indexY,indexX)) {
					gameData.cardInventory().addCardInDeck(gameData.cardInventory().cardList().get(gameData.selectedCardIndex()));
					gameData.cardInventory().remove(gameData.selectedCardIndex());
					}
				}		
				
			gameData.unselect();
		}
	}
	
	/**
	 * Fonction qui relance le passage du temps
	 */
	private void startTime() {
		timeData.start();
		gameData.unselect();
	}
	
	/**
	 * Fonction d'actualisation de l'�cran et des diff�rentes donn�es
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
	
	
}
