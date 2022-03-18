package fr.umlv.zen5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;


public class Game {
	
	ApplicationContext ctx; //on stocke le contexte directement dans la classe pour pas avoir � l'appeler
	float width;
	float heigth;
	
	
	
	public void potiCare(Graphics2D graphics) {
		graphics.setColor(Color.RED);
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth));
	}
	
	public void menu(Graphics2D graphics) {
		graphics.setColor(Color.GRAY);
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth/10));
		graphics.fill(new Rectangle2D.Float(width-(width/5), 0, width/5, heigth));
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(0, heigth-(heigth/6), width-(width/5), heigth));
		
		
		
		float heigthPlayingZone = heigth-(heigth/6+heigth/10);
		float squareSize = heigthPlayingZone / 12;
		float widthPlayingZone = squareSize*21;
		float xPlayingZone = 0;
		float yPlayingZone =heigth/10;
		
		graphics.setColor(Color.WHITE);
		
		for (int i = 0; i <= 12; i++) {
			float line = yPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(xPlayingZone, line, widthPlayingZone, line));
		}

		for (int i = 0; i <= 21; i++) {
			float column = xPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(column, yPlayingZone, column, yPlayingZone+heigthPlayingZone));
		}
		
		
		//graphics.setColor(Color.BLUE);
		//graphics.fill(new Rectangle2D.Float(xPlayingZone,yPlayingZone,widthPlayingZone,heigthPlayingZone));
		
	}
	
	private void DrawFrame(Graphics2D graphics) {
		
		// on dessine un poti car�
		menu(graphics);

	}

	public void Run(ApplicationContext ctx) { //Application ctx c'est une variable rentr�e automatiquement par Application.run
		
		this.ctx = ctx;
		ScreenInfo screen = ctx.getScreenInfo();
		this.width = screen.getWidth();
		this.heigth = screen.getHeight();
		
		while(true) {
			
			Update(); // Fonction execute a chaque frame
			
		}
	}
	
	private void Update() {
		

		//en gros renderFrame est une methode de applicationContexte qui pr�pare le terrain � l'affichage
		//Puis affiche ta fonction (d'ou le fait qu'on mette une fonction en parametre)
		//Puis range proprement toutes les donn�es graphiques
		//le param�tre qu'il envoie en premier � ta fonction c'est un object Graphics2D
		this.ctx.renderFrame(this::DrawFrame); 
		
		Event e = ctx.pollOrWaitEvent(200);//on r�cup�re tout evenement sur la machine (temps d'attente max 200 millisecondes)
		
		System.out.println("un tour un");//un print � chaque tour
		
		if(e == null) return; //200 milliseconde c'est court du coup y'a moyen que l'event soit null et donc qu'on puisse pas en tirer de touche
		
		switch(e.getKey()) {//on r�cup�re une touche
		
			case SPACE -> { //si c'est espace on arrette tout (je sais pas pourquoi la fl�che)
				ctx.exit(0);
			}
			default -> System.out.println("touche inactive "+e.getKey()); //sinon on dit quelle touche on � appuyer
	
		}
		
	}
	
	
}
