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

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import leJeu.boardGame.Coord;
import leJeu.boardGame.Plateau;


public class Game {
	
	private ApplicationContext ctx; //on stocke le contexte directement dans la classe pour pas avoir à l'appeler
	
	private float width;
	private float heigth;
	
	private int heigthPlayingZone;
	private int squareSize;
	private int widthPlayingZone;
	private int xPlayingZone;
	private int yPlayingZone;
	
	
	private Plateau Board = new Plateau();
	
	
	public void drawInterface(Graphics2D graphics) {
		graphics.setColor(Color.GRAY);
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth/10));
		graphics.fill(new Rectangle2D.Float(width-(width/5), 0, width/5, heigth));
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(0, heigth-(heigth/6), width-(width/5), heigth));
	}
	
	public BufferedImage stringToImage(String pictureName) {
		Path path = Path.of(pictureName);
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			return img;
		} catch (IOException e) {
			throw new RuntimeException("probleme de dossier : " + path.getFileName());
		}
	}
	
	public void drawATile(Graphics2D graphics, int x, int y, BufferedImage img) {
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(squareSize / (double) img.getWidth(), squareSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, xPlayingZone + x * squareSize, yPlayingZone + y * squareSize);
	}
		
	public void drawBoard(Graphics2D graphics) {
		
		graphics.setColor(Color.WHITE);
		
		for (int i = 0; i <= 12; i++) {
			int line = yPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(xPlayingZone, line, widthPlayingZone, line));
		}

		for (int i = 0; i <= 21; i++) {
			int column = xPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(column, yPlayingZone, column, yPlayingZone+heigthPlayingZone));
		}
		
		BufferedImage img = stringToImage("pictures/chemin.png");
		for(Coord coord : Board.listeCoord()) {
			drawATile(graphics,coord.x(),coord.y(),img);
		}
		
    }
 
	private void DrawFrame(Graphics2D graphics,int test) {
		
		// on dessine un poti caré
		drawInterface(graphics);
		drawBoard(graphics);

	}

	public void Run(ApplicationContext ctx) { //Application ctx c'est une variable rentrée automatiquement par Application.run
		
		this.ctx = ctx;
		ScreenInfo screen = ctx.getScreenInfo();
		this.width = screen.getWidth();
		this.heigth = screen.getHeight();
		
		this.heigthPlayingZone = Math.round(heigth-(heigth/6+heigth/10));
		this.squareSize = Math.round(heigthPlayingZone/12);
		this.widthPlayingZone = Math.round(squareSize*21);
		this.xPlayingZone = 0;
		this.yPlayingZone =Math.round(heigth/10);
		
		while(true) {
			
			Update(); // Fonction execute a chaque frame
			
		}
	}
	
	private void Update() {
		

		//en gros renderFrame est une methode de applicationContexte qui prépare le terrain à l'affichage
		//Puis affiche ta fonction (d'ou le fait qu'on mette une fonction en parametre)
		//Puis range proprement toutes les données graphiques
		//le paramètre qu'il envoie en premier à ta fonction c'est un object Graphics2D
		this.ctx.renderFrame(graphics -> DrawFrame(graphics,2)); 
		
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
