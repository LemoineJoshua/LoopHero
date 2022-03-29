package theGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import theGame.boardGame.Coord;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.AbstractTile;
import theGame.tiles.EmptyField;
import theGame.Cards.Card;
import theGame.boardGame.Board;

public class GameView {
	private float width;
	private float heigth;
	
	private int heigthPlayingZone;
	private int squareSize;
	private int widthPlayingZone;
	private int xPlayingZone;
	private int yPlayingZone;
	
	public GameView(ApplicationContext ctx) {
		ScreenInfo screen = ctx.getScreenInfo();
		this.width = screen.getWidth();
		this.heigth = screen.getHeight();
		
		this.heigthPlayingZone = Math.round(heigth-(heigth/6+heigth/10));
		this.squareSize = Math.round(heigthPlayingZone/12);
		this.widthPlayingZone = Math.round(squareSize*21);
		this.xPlayingZone = 0;
		this.yPlayingZone =Math.round(heigth/10);
	}
	
	
	public void drawInterface(Graphics2D graphics, double timeFraction, int loop) {
		graphics.setColor(Color.GRAY);
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth/10));
		
		
		
		drawTimeBar(graphics, timeFraction);
				
		BufferedImage img = stringToImage("pictures/hourglass.png"); // Dessin du sablier fait un peu à l'arrache
		drawAnEntity(graphics, 0, -1, img);
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 30));		
		graphics.drawString("Boucle : "+ loop, xPlayingZone + squareSize , yPlayingZone/2 + 30 );
		
		drawHud(graphics, loop);
		
		
	}
	
	public void drawHud(Graphics2D graphics, int loop) {
		BufferedImage img = stringToImage("pictures/Hud2.png");
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance((width/5) / (double) img.getWidth(), heigth / (double) img.getHeight()),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int)Math.round((4*width)/5),0);
		
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 30));		
		graphics.drawString(""+loop, (int)Math.round((4.475*width)/5), 5*squareSize/6 );
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
	
	public void drawAnEntity(Graphics2D graphics, int x, int y, BufferedImage img) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(squareSize / ((double) img.getWidth()*2), squareSize / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int) Math.round(xPlayingZone + x * squareSize + squareSize*0.25), (int) Math.round(yPlayingZone + y * squareSize + squareSize * 0.25 ));
	}
	
	public void drawAllMob(Graphics2D graphics, GameData gameData) {
		for (Coord coord: gameData.board().coordList()) {	
			AbstractRoad caseAffiche = (AbstractRoad) gameData.board().boardMatrix()[coord.y()][coord.x()];
			for (Monster mob: caseAffiche.aliveMonster()) {
				BufferedImage img = stringToImage(mob.picture());
				drawAnEntity(graphics, coord.x(),coord.y(),img);
				
			}
			
		}
		
	}
	
	public void drawTimeBar(Graphics2D graphics, double timeFraction) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone * timeFraction, 30));
		graphics.setColor(Color.BLACK);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
	}
	
	public void drawCards(Graphics2D graphics,CardInventory cards) {
		int y = Math.round(heigth-(heigth/6));
		int x = 0;
		int cardWidth = Math.round((4*width/5)/13);
		
		for(Card card:cards.cardList()) {
			drawACard(graphics,x,y,card.img());
			x+=cardWidth;
		}
	}
	
	public void drawACard(Graphics2D graphics, int x, int y, BufferedImage img) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance( (width/10) / ((double) img.getWidth()*2), (heigth/4) / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, x, y);
	}
	
	public void drawBoard(Graphics2D graphics, GameData gameData) {
		
		graphics.setColor(Color.WHITE);
		
		for (int i = 0; i <= 12; i++) {
			int line = yPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(xPlayingZone, line, widthPlayingZone, line));
		}

		for (int i = 0; i <= 21; i++) {
			int column = xPlayingZone + i * squareSize;
			graphics.draw(new Line2D.Float(column, yPlayingZone, column, yPlayingZone+heigthPlayingZone));
		}
		
		 for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if (!(gameData.board().boardMatrix()[y][x].picture()==null)) {
	        			drawATile(graphics, x, y, gameData.board().boardMatrix()[y][x].picture());
	        		}
	        		
	        	}
	        }
		 
		BufferedImage img = stringToImage("pictures/heroB.png");
		drawAnEntity(graphics,gameData.board().heroX(),gameData.board().heroY(),img);
		drawAllMob(graphics,gameData);
		drawCards(graphics,gameData.cardInventory());
		
		
    }
 
	public void drawFrame(Graphics2D graphics, GameData gameData, TimeData timeData) {
		
		// on dessine un poti caré
		drawInterface(graphics, timeData.timeFraction(), gameData.board().loop());
		drawBoard(graphics, gameData);

	}
	
	public void drawFrame(ApplicationContext ctx, GameData gameData, TimeData timeData) {
		ctx.renderFrame(graphics -> drawFrame(graphics, gameData, timeData)); 
	}
}
