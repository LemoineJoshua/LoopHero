package fr.umlv.zen5;

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


public class Game {
	
	ApplicationContext ctx; //on stocke le contexte directement dans la classe pour pas avoir à l'appeler
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
		
		
		
		int heigthPlayingZone = Math.round(heigth-(heigth/6+heigth/10));
		int squareSize = Math.round(heigthPlayingZone/12);
		int widthPlayingZone = Math.round(squareSize*21);
		int xPlayingZone = 0;
		int yPlayingZone =Math.round(heigth/10);
		
		graphics.setColor(Color.WHITE);
		
		for (int i = 0; i <= 12; i++) {
			int line = yPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(xPlayingZone, line, widthPlayingZone, line));
		}

		for (int i = 0; i <= 21; i++) {
			int column = xPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(column, yPlayingZone, column, yPlayingZone+heigthPlayingZone));
		}
		
		//graphics.setColor(Color.BLUE);
		//graphics.fill(new Rectangle2D.Float(xPlayingZone,yPlayingZone,widthPlayingZone,heigthPlayingZone));
		
		String pictureName = "pictures/chemin.png";
		Path path = Path.of(pictureName);
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(squareSize / (double) img.getWidth(), squareSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, xPlayingZone + 3 * squareSize, yPlayingZone + 5 * squareSize);
		} catch (IOException e) {
			throw new RuntimeException("problÃ¨me d'affichage : " + path.getFileName());
		}
	}
		
		
	
	
	private void DrawFrame(Graphics2D graphics) {
		
		// on dessine un poti caré
		menu(graphics);

	}

	public void Run(ApplicationContext ctx) { //Application ctx c'est une variable rentrée automatiquement par Application.run
		
		this.ctx = ctx;
		ScreenInfo screen = ctx.getScreenInfo();
		this.width = screen.getWidth();
		this.heigth = screen.getHeight();
		
		while(true) {
			
			Update(); // Fonction execute a chaque frame
			
		}
	}
	
	private void Update() {
		

		//en gros renderFrame est une methode de applicationContexte qui prépare le terrain à l'affichage
		//Puis affiche ta fonction (d'ou le fait qu'on mette une fonction en parametre)
		//Puis range proprement toutes les données graphiques
		//le paramètre qu'il envoie en premier à ta fonction c'est un object Graphics2D
		this.ctx.renderFrame(this::DrawFrame); 
		
		Event e = ctx.pollOrWaitEvent(200);//on récupère tout evenement sur la machine (temps d'attente max 200 millisecondes)
		
		System.out.println("un tour un");//un print à chaque tour
		
		if(e == null) return; //200 milliseconde c'est court du coup y'a moyen que l'event soit null et donc qu'on puisse pas en tirer de touche
		
		switch(e.getKey()) {//on récupère une touche
		
			case SPACE -> { //si c'est espace on arrette tout (je sais pas pourquoi la flèche)
				ctx.exit(0);
			}
			default -> System.out.println("touche inactive "+e.getKey()); //sinon on dit quelle touche on à appuyer
	
		}
		
	}
	
	
}
