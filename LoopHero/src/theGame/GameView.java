package theGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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
import theGame.tiles.AbstractRoad;
import theGame.Cards.Card;
import theGame.boardGame.Board;

public class GameView {
	private final float width;
	private final float heigth;
	private final int heigthPlayingZone;
	private final int squareSize;
	private final int widthPlayingZone;
	private final int xPlayingZone;
	private final int yPlayingZone;
	
	/**
	 * Constructeur de GameView,
	 * memorise la largeur et la hauteur de la fenetre
	 * Ainsi que la largeur et la hauteur de la grille de jeu, et ses coordonnées
	 * 
	 * @param ctx contexte de l'application
	 */
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
	
	
	/**
	 * Dessinne l'interface du jeu (hors grille de jeu et cartes)
	 * 
	 * @param graphics Objet de dessin
	 * @param timeData Données temporelles
	 * @param gameData Données du jeu
	 */
	public void drawInterface(Graphics2D graphics, TimeData timeData, GameData gameData) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(0, heigth/10, 4*width/5, heigth));
		
		graphics.setColor(new Color(104, 111, 111));
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth/10));
		
		drawTimeBar(graphics, timeData.timeFraction(), gameData);
		
		drawLogo(graphics);
		
		if (timeData.isStopped()) {
			graphics.drawString("Mode Plannification", xPlayingZone + 7*squareSize , yPlayingZone/2 + 30 );
		}		
		
		drawHud(graphics,gameData.board());
		gameData.ressourcesInventory().afficheRessource(xPlayingZone+21*squareSize+5, yPlayingZone+14, graphics, squareSize);
		
	}
	
	/**
	 * Fonction qui dessine le logo en haut à droite de l'écran
	 * 
	 * @param graphics Objet de dessin
	 */
	private void drawLogo(Graphics2D graphics) {
		BufferedImage img = stringToImage("pictures/HUD/logo.png"); 
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(yPlayingZone / ((double) img.getWidth()), yPlayingZone / ((double) img.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, 21*squareSize, 0);
	}
	
	
	/**
	 * Dessine le HUD sur la droite ainsi que les PV du Hero
	 * 
	 * @param graphics Objet de dessin
	 * @param board plateau du jeu
	 */
	public void drawHud(Graphics2D graphics, Board board) {
		
		graphics.setColor(Color.WHITE);
		graphics.drawString(board.hero().hp()+"/"+board.hero().maxHp()+"HP",(float) (4.2*width/5), heigthPlayingZone);
		
		
	}
	
	
	/**
	 * @param pictureName chemin de l'image
	 * @return l'image créée avec le chemin de l'image
	 */
	public BufferedImage stringToImage(String pictureName) {
		Path path = Path.of(pictureName);
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			return img;
		} catch (IOException e) {
			throw new RuntimeException("probleme de dossier : " + path.getFileName());
		}
	}
	
	/**
	 * Dessine une tuile sur le plateau de jeu
	 * 
	 * @param graphics Objet de dessin
	 * @param x colonne du plateau
	 * @param y ligne du plateau
	 * @param img image de la case à dessiner
	 */
	public void drawATile(Graphics2D graphics, int x, int y, BufferedImage img) {		
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(squareSize / (double) img.getWidth(), squareSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, xPlayingZone + x * squareSize, yPlayingZone + y * squareSize);
	}
	
	/**
	 * Dessine un monstre sur le plateau de jeu
	 * 
	 * @param graphics Objet de dessin
	 * @param x colonne du plateau
	 * @param y ligne du plateau
	 * @param img image de la case à dessiner
	 */
	public void drawAnEntity(Graphics2D graphics, int x, int y, BufferedImage img) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(squareSize / ((double) img.getWidth()*2), squareSize / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int) Math.round(xPlayingZone + x * squareSize + squareSize*0.25), (int) Math.round(yPlayingZone + y * squareSize + squareSize * 0.25 ));
	}
	
	/**
	 * Dessine tout les monstres du jeux
	 * 
	 * @param graphics Objet de dessin
	 * @param gameData Données du jeux
	 */
	public void drawAllMob(Graphics2D graphics, GameData gameData) {
		for (Coord coord: gameData.board().coordList()) {	
			AbstractRoad caseAffiche = (AbstractRoad) gameData.board().boardMatrix()[coord.y()][coord.x()];
			for (Monster mob: caseAffiche.aliveMonster()) {
				BufferedImage img = stringToImage(mob.picture());
				drawAnEntity(graphics, coord.x(),coord.y(),img);
				
			}
			
		}
		
	}
	
	/**
	 * Dessine la barre de temps sur le haut du plateau, et écrit les indications en dessous
	 * 
	 * @param graphics Objet de Dessin
	 * @param timeFraction fraction de journée écoulée
	 * @param gameData Données du jeu
	 */
	public void drawTimeBar(Graphics2D graphics, double timeFraction, GameData gameData) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone * timeFraction, 30));
		graphics.setColor(Color.BLACK);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		
	
		BufferedImage img = stringToImage("pictures/HUD/hourglass.png"); 
		drawAnEntity(graphics, 0, -1, img);
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 30));		
		graphics.drawString("Boucle : "+ gameData.board().loop(), xPlayingZone + squareSize , yPlayingZone/2 + 30 );		
	}
	
	/**
	 * Dessine les cartes de la mains du joueur
	 * 
	 * @param graphics graphics Objet de Dessin
	 * @param gameData données du jeux
	 */
	public void drawCards(Graphics2D graphics,GameData gameData) {
		int y = Math.round(heigth-(heigth/6));
		int x = 0;
		int cardWidth = Math.round((4*width/5)/13);
		
		for(Card card:gameData.cardInventory().cardList()) {
			drawACard(graphics,x,y,card.img());
			x+=cardWidth;
		}
		
		if (gameData.aCardIsSelected()) {
			BufferedImage img = stringToImage("pictures/HUD/cursor.png");			
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(squareSize / ((double) img.getWidth()*2), squareSize / ((double) img.getHeight()*2)),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int) Math.round(cardWidth*gameData.selectedCardIndex()+cardWidth/3), (int) Math.round(heigth-(heigth/6)+(squareSize/2)));
		}		
	}
	
	/**
	 * Dessine une carte sur le plateau
	 * 
	 * @param graphics Objet de Dessin
	 * @param x colonne du plateau
	 * @param y ligne du plateau
	 * @param img image de la carte
	 */
	public void drawACard(Graphics2D graphics, int x, int y, BufferedImage img) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance( (width/10) / ((double) img.getWidth()*2), (heigth/4) / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, x, y);
	}
	
	/**
	 * Dessine le plateau de jeu en entier
	 * 
	 * @param graphics Objet de Dessin
	 * @param gameData Donnée du jeux
	 */
	public void drawBoard(Graphics2D graphics, GameData gameData) {
		
		graphics.setColor(Color.WHITE);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone, 21*squareSize, 12*squareSize));
		
		 for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if (!(gameData.board().boardMatrix()[y][x].picture()==null)) {
	        			drawATile(graphics, x, y, gameData.board().boardMatrix()[y][x].picture());
	        		}
	        		
	        	}
	        }
		 
		BufferedImage img = stringToImage("pictures/Entities/heroB.png");
		drawAnEntity(graphics,gameData.board().heroX(),gameData.board().heroY(),img);
		drawAllMob(graphics,gameData);
		drawCards(graphics,gameData);
		
		if (gameData.aCardIsSelected()) {
			Card myCard = gameData.cardInventory().cardList().get(gameData.selectedCardIndex());
			img = stringToImage("pictures/Tiles/selectRoadSide.png");
			for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if ((myCard.type()==gameData.board().boardMatrix()[y][x].type())&&gameData.board().boardMatrix()[y][x].isEmpty()) {
	        			drawATile(graphics, x, y, img);
	        		}
	        		
	        	}
	        }
		}	
    }
 
	
	
	/**
	 * Dessine tout l'écran
	 * 
	 * @param graphics Objet de Dessin
	 * @param gameData données du jeux
	 * @param timeData données temporelles du jeux
	 */
	public void drawFrame(Graphics2D graphics, GameData gameData, TimeData timeData) {
		drawInterface(graphics, timeData, gameData);
		drawBoard(graphics, gameData);
	}
	
	public void drawFrame(ApplicationContext ctx, GameData gameData, TimeData timeData) {
		ctx.renderFrame(graphics -> drawFrame(graphics, gameData, timeData)); 
	}
	
	
	// Fonctions pour placer des cartes :
	
	
	/**
	 * @param location location du clique de la sourie
	 * @return true si la sourie clique dans la mains du joueur, false sinon
	 */
	public boolean clickInCardZone(Point2D.Float location) {
		return location.x < (4*width/5) && location.y >= heigth-(heigth/6);
	}
	
	/**
	 * @param coordX coordonée X du clique de la sourie
	 * @return la coordonnée de la colonne ou le sourie à cliquee
	 */
	private int indexFromCardZone(float coordX) {
		int cardWidth = Math.round((4*width/5)/13);
		return (int) ((coordX) / cardWidth);
	}
	
	/**
	 * @param coordX coordonée X du clique de la sourie
	 * @return la coordonnée de la colonne ou le sourie à cliquee (sans donner accès au calcul)
	 */
	public int selectCard(float coordX) {
		return indexFromCardZone(coordX);
	}
	
	/**
	 * @param location location du clique de la sourie
	 * @return true si la sourie a cliquee sur le plateau de jeu
	 */
	public boolean clickInBoardZone(Point2D.Float location) {
		return ((int) location.x < (xPlayingZone + 21*squareSize)) && ((int)location.x > (xPlayingZone)) && ((int)location.y< (yPlayingZone+12*squareSize)) && ((int)location.y > (yPlayingZone));
	}
	
	/**
	 * @param coordY coordonee Y du clique de la sourie
	 * @return la ligne sur laquelle à cliquee la sourie
	 */
	public int selectLine(float coordY) {
		return (int) ((coordY-yPlayingZone) / (squareSize));
	}
	
	/**
	 * @param coordX coordonee X du clique de la sourie
	 * @return la colonne sur laquelle à cliquee la sourie
	 */
	public int selectColumn(float coordX) {
		return (int) ((coordX-xPlayingZone) / (squareSize));
	}
	
	
}
