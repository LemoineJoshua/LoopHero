package leJeu;

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

import leJeu.TimeData;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import leJeu.boardGame.Coord;
import leJeu.boardGame.Plateau;


public class Game {
	
	private ApplicationContext ctx; //on stocke le contexte directement dans la classe pour pas avoir � l'appeler
	private GameView gameView;
	private Plateau board;
	private TimeData timeData;
	
	private void moveHeroAndDraw(ApplicationContext context) {
		if (timeData.elapsedHero() >= TimeData.HERO_DELAY) {
			board.moveHero();
			gameView.drawFrame(context, board, timeData);
			timeData.resetElapsedHero();
		}
	}

	public void Run(ApplicationContext ctx) { //Application ctx c'est une variable rentr�e automatiquement par Application.run
		
		this.ctx = ctx;		
		this.gameView = new GameView(ctx);		
		this.board = new Plateau();
		this.timeData = new TimeData();
		
		gameView.drawFrame(ctx, board, timeData);		
		while(true) {
			
			Update(); // Fonction execute a chaque frame
			
		}
	}
	
	private void Update() {
		
		//en gros renderFrame est une methode de applicationContexte qui pr�pare le terrain � l'affichage
		//Puis affiche ta fonction (d'ou le fait qu'on mette une fonction en parametre)
		//Puis range proprement toutes les donn�es graphiques
		//le param�tre qu'il envoie en premier � ta fonction c'est un object Graphics2D
		
		moveHeroAndDraw(ctx);
		
		Event e = ctx.pollOrWaitEvent(200);//on r�cup�re tout evenement sur la machine (temps d'attente max 200 millisecondes)
		
		System.out.println("un tour un");//un print � chaque tour
		System.out.println(timeData.HERO_DELAY);
		
		if(e == null) return; //200 milliseconde c'est court du coup y'a moyen que l'event soit null et donc qu'on puisse pas en tirer de touche
		
		switch(e.getKey()) {//on r�cup�re une touche
		
			case SPACE -> { //si c'est espace on arrette tout (je sais pas pourquoi la fl�che)
				ctx.exit(0);
			}
			
			case LEFT -> timeData.slower();
			
			case RIGHT -> timeData.faster();
			
			case S -> timeData.stop();
			
			case D -> timeData.start();
			
			default -> System.out.println("touche inactive "+e.getKey()); //sinon on dit quelle touche on � appuyer
	
		}
		gameView.drawFrame(ctx, board, timeData);
		
	}
	
	
}
