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
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import theGame.boardGame.Coord;
import theGame.entities.AbstractEntities;
import theGame.entities.Hero;
import theGame.entities.Monster;
import theGame.inventories.HeroStuff;
import theGame.inventories.Item;
import theGame.inventories.ItemInventory;
import theGame.tiles.AbstractRoad;
import theGame.Cards.Card;

public class GameView {
	private final float width;
	private final float heigth;
	private final int heigthPlayingZone;
	private final int squareSize;
	private final int widthPlayingZone;
	private final int xPlayingZone;
	private final int yPlayingZone;
	private Graphics2D graphics;
	
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
	public void drawInterface(TimeData timeData, GameData gameData) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(0, heigth/10, 5*width/6, heigth));
		
		graphics.setColor(Color.DARK_GRAY);
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth/10));
		
		drawTimeBar(timeData.timeFraction(), gameData);
		
		drawLogo();
		
		if (timeData.isStopped()) {
			graphics.drawString("Mode Plannification", xPlayingZone + 7*squareSize , yPlayingZone/2 + 30 );
		}		
		int fontSize = Math.round(((5*width/6) - (xPlayingZone+widthPlayingZone))/13);
		gameData.ressourcesInventory().drawRessources(xPlayingZone+21*squareSize+5, yPlayingZone+14, graphics, squareSize, fontSize);	

		drawHud(gameData);
	}
	
	/**
	 * Fonction qui dessine le logo en haut à droite de l'écran
	 * 
	 * @param graphics Objet de dessin
	 */
	private void drawLogo() {
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
	public void drawHud(GameData gameData) {
		// Dessins des cases vides pour équipement
		
		graphics.setColor(Color.DARK_GRAY);
		graphics.fill(new Rectangle2D.Float(5*width/6, 0, width/6, heigth));
		
		BufferedImage img = stringToImage("pictures/HUD/EquipementVide.png");
		double cellSize = (1*width/6)/4;
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
				AffineTransformOp.TYPE_BILINEAR);
		
		graphics.setColor(Color.WHITE);
		int fontSize = Math.round((int)cellSize/3);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSize));		
		graphics.drawString("Mon Équipement",(float) (5*width/6 +10),yPlayingZone-10);
		
		for (int i=0; i<4;i++) {
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6  + i*width/24),yPlayingZone);
		}
		
		double yStuffCell = yPlayingZone+2*cellSize;
		graphics.drawString("Mon Inventaire",(float) (5*width/6 +10),(float) (yStuffCell-10));
		
		for (int j=0;j<3;j++) {
			for (int i=0; i<4;i++) {
				graphics.drawImage(img, scaling, (int)Math.round(5*width/6  + i*width/24), (int) Math.round(yStuffCell + cellSize*j));
			}
		}
		
		drawStuffInventory(gameData.itemInventory(), yStuffCell);
		drawStuffEquiped(gameData.board().hero().stuff());
		if (gameData.anItemIsSelected()) {
			drawItemSelection(gameData, cellSize, yStuffCell);
		}
		
		// Dessin de la zone rouge pour les stats
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSize));
		img = stringToImage("pictures/HUD/Hud3.png");
		double yStuffStat = yStuffCell+4*cellSize;
		
		graphics.drawString("Mes Statistiques",(float) (5*width/6 +10),(float) yStuffStat-10);
		scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance((width/6) / (double) img.getWidth(), (heigth-yStuffStat) / (double) img.getHeight()),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int)Math.round(5*width/6), (int) Math.round(yStuffStat));		
		drawStats(gameData.board().hero(), yStuffStat);
			
	}
		
	private void drawItemSelection(GameData gameData , double cellSize, double y) {
		BufferedImage img = stringToImage("pictures/HUD/cursor.png");			
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(cellSize / ((double) img.getWidth()*2), cellSize / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		int indexTmp = gameData.selectedItemIndex();
		graphics.drawImage(img, scaling, (int) Math.round((5*width/6) + cellSize*((indexTmp%4))+cellSize/3), (int) Math.round(y+cellSize*((int) (indexTmp/4))+(cellSize/2)));
		
		img = stringToImage("pictures/Tiles/selectFields.png");			
		scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(cellSize / ((double) img.getWidth()), cellSize / ((double) img.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		
		String type = gameData.itemInventory().itemInventory().get(indexTmp).type();
		
		switch (type) {
		case "weapon": 
			graphics.drawImage(img, scaling, (int) Math.round((5*width/6) + cellSize*0), (int) Math.round(yPlayingZone));
			break;
		case "shield": 
			graphics.drawImage(img, scaling, (int) Math.round((5*width/6) + cellSize*1), (int) Math.round(yPlayingZone));
			break;
		case "armor": 
			graphics.drawImage(img, scaling, (int) Math.round((5*width/6) + cellSize*2), (int) Math.round(yPlayingZone));
			break;
		case "ring": 
			graphics.drawImage(img, scaling, (int) Math.round((5*width/6) + cellSize*3), (int) Math.round(yPlayingZone));
			break;
		}
		
		drawItemStats(gameData, y, cellSize, type);
	}
		
	private void drawItemStats(GameData gameData,double y, double cellSize, String type) {
		graphics.setColor(new Color(62, 59, 59));
		graphics.fill(new Rectangle2D.Float(widthPlayingZone,(float) y, (5*width/6)-widthPlayingZone, (float) (4*cellSize)));
		
		int line = 1;
		int x = xPlayingZone+21*squareSize+5;
		int fontSizeText = Math.round(((5*width/6) - (x))/13);
		int fontSizeTitle = Math.round(((5*width/6) - (x))/10);		
		graphics.setColor(Color.WHITE);
		Item equippedStuff = gameData.board().hero().stuff().get(type);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle));
		graphics.drawString("=> Stuff équipé :",(float) x,(float) y+line*fontSizeTitle);
		line ++;
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeText));
		if (equippedStuff!=null) {
			graphics.drawString("- "+equippedStuff.type()+" "+equippedStuff.rarity(),(float) x,(float) y+line*fontSizeTitle);
			line++;
			for(Map.Entry entree : equippedStuff.stats().entrySet()){
				String statName=entree.getKey().toString();
				Double statValue=(Double)entree.getValue();
				if (statValue != 0) {
					graphics.drawString("- "+statName+": "+statValue,(float) x,(float) y+line*fontSizeTitle);
					line ++;
				}
			}
		}else {
			graphics.drawString("Pas d'objet équipé",(float) x,(float) y+line*fontSizeTitle);
			line ++;
		}
		line++;
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle));
		graphics.drawString("=> Stuff choisi :",(float) x,(float) y+line*fontSizeTitle);
		line ++;
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeText));
		Item selectionnedStuff = gameData.itemInventory().itemInventory().get(gameData.selectedItemIndex());
		graphics.drawString("- "+selectionnedStuff.type()+" "+selectionnedStuff.rarity(),(float) x,(float) y+line*fontSizeTitle);
		line++;
		for(Map.Entry entree : selectionnedStuff.stats().entrySet()){
			String statName=entree.getKey().toString();
			Double statValue=(Double)entree.getValue();
			if (statValue != 0) {
				graphics.drawString("- "+statName+": "+statValue,(float) x,(float) y+line*fontSizeTitle);
				line ++;
			}
		}
	}
	
	private void drawStats(Hero hero, double y) {
		int zoneHeigth = (int) (heigth-y);
		graphics.setColor(Color.WHITE);
		graphics.drawString("HP : " +hero.hp()+"/"+hero.maxHp()+"HP",(float) (5*width/6 + 20),(float) y+1*zoneHeigth/8);
		graphics.drawString("Strength : " +hero.strength(),(float) (5*width/6 + 20),(float) y+2*zoneHeigth/8);
		graphics.drawString("Defense : " +hero.defense(),(float) (5*width/6 + 20),(float) y+3*zoneHeigth/8);
		graphics.drawString("CounterAttack : " +hero.counterAttack(),(float) (5*width/6 + 20),(float) y+4*zoneHeigth/8);
		graphics.drawString("Regen : " +hero.regen(),(float) (5*width/6 + 20),(float) y+5*zoneHeigth/8);
		graphics.drawString("Evade : " +hero.evade(),(float) (5*width/6 + 20),(float) y+6*zoneHeigth/8);
		graphics.drawString("Vampirism : " +hero.vampirism(),(float) (5*width/6 + 20),(float) y+7*zoneHeigth/8);
	}
	
	private void drawStuffInventory(ItemInventory itemInventory, double yStuffCell ) {
		int x=0;
		int y=0;
		double cellSize = (1*width/6)/4;
		BufferedImage img;
		AffineTransformOp scaling;
		for (Item item: itemInventory.itemInventory()) {
			img = stringToImage("pictures/Stuff/"+item.type().toString()+".png");
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + x*cellSize), (int) Math.round(yStuffCell + y*cellSize));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + x*cellSize),(float) (yStuffCell + y*cellSize +cellSize/4));
			x++;
			if(x>=4) {
				y++;
				x=0;
			}
		}
	}
	

	private void drawStuffEquiped(HeroStuff stuff ) {
		double cellSize = (1*width/6)/4;
		Item item = stuff.get("weapon");
		BufferedImage img = stringToImage("pictures/Stuff/shield.png");
		AffineTransformOp scaling;
		if (item!=null) {
			img = stringToImage("pictures/Stuff/"+item.type()+".png");
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 0*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 0*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		item = stuff.get("shield");
		if (item!=null) {			
			img = stringToImage("pictures/Stuff/"+item.type()+".png");
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 1*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 1*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		item = stuff.get("armor");
		if (item!=null) {
			img = stringToImage("pictures/Stuff/"+item.type()+".png");
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 2*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 2*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		item = stuff.get("ring");
		if (item!=null) {
			img = stringToImage("pictures/Stuff/"+item.type()+".png");
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 3*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 3*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		
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
	public void drawATile(int x, int y, BufferedImage img) {		
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
	public void drawAnEntity(int x, int y, BufferedImage img) {
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
	public void drawAllMob(GameData gameData) {
		for (Coord coord: gameData.board().coordList()) {	
			AbstractRoad caseAffiche = (AbstractRoad) gameData.board().boardMatrix()[coord.y()][coord.x()];
			for (Monster mob: caseAffiche.aliveMonster()) {
				BufferedImage img = stringToImage(mob.picture());
				drawAnEntity(coord.x(),coord.y(),img);
				
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
	public void drawTimeBar(double timeFraction, GameData gameData) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone * timeFraction, 30));
		graphics.setColor(Color.BLACK);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		
	
		BufferedImage img = stringToImage("pictures/HUD/hourglass.png"); 
		drawAnEntity(0, -1, img);
		
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
	public void drawCards(GameData gameData) {
		int y = Math.round(heigth-(heigth/6));
		int x = 0;
		int cardWidth = Math.round((4*width/5)/13);
		
		for(Card card:gameData.cardInventory().cardList()) {
			drawACard(x,y,card.img());
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
	public void drawACard(int x, int y, BufferedImage img) {
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
	public void drawBoard(GameData gameData) {
		
		graphics.setColor(Color.WHITE);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone, 21*squareSize, 12*squareSize));
		
		 for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if (!(gameData.board().boardMatrix()[y][x].picture()==null)) {
	        			drawATile(x, y, gameData.board().boardMatrix()[y][x].picture());
	        		}
	        		
	        	}
	        }
		 
		BufferedImage img = stringToImage("pictures/Entities/heroB.png");
		drawAnEntity(gameData.board().heroX(),gameData.board().heroY(),img);
		drawAllMob(gameData);
		drawCards(gameData);
		
		if (gameData.aCardIsSelected()) {
			Card myCard = gameData.cardInventory().cardList().get(gameData.selectedCardIndex());
			img = stringToImage("pictures/Tiles/selectRoadSide.png");
			for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if ((myCard.type()==gameData.board().boardMatrix()[y][x].type())&&gameData.board().boardMatrix()[y][x].isEmpty()) {
	        			drawATile(x, y, img);
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
		this.graphics = graphics;
		drawInterface(timeData, gameData);
		drawBoard(gameData);
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
	
	// Fonctions pour équiper son stuff 
	
	public boolean clickInItemInventoryZone(Point2D.Float location) {
		return location.x > (5*width/6) && location.y >= yPlayingZone+2*((1*width/6)/4) && location.y<=yPlayingZone+5*((1*width/6)/4);
	}
	
	private int indexFromItemInventory(float coordX, float coordY) {
		int cellSize = Math.round((1*width/6)/4);
		int x = (int)((coordX-(5*width/6)) / cellSize);
		int y = (int)((coordY-(yPlayingZone+2*cellSize)) / cellSize);
		int index = (x%4)+(4*y);
		System.out.println("X vaut : "+x);
		System.out.println("Y vaut : "+y);
		System.out.println("Calcul d'index : "+index);
		return index;
	}
	
	public int selectItemInInventory(float coordX, float coordY) {
		return indexFromItemInventory(coordX,coordY);
	}
	
	public boolean clickInStuffZone(Point2D.Float location) {
		return location.x > (5*width/6) && location.y >= yPlayingZone && location.y<=yPlayingZone+1*((1*width/6)/4);
	}
	
	public String getItemKey(float coordX) {
		int cellSize = Math.round((1*width/6)/4);
		int x = (int)((coordX-(5*width/6)) / cellSize);
		String key = "";
		
		switch (x) {
		case 0:
			key="weapon";
			System.out.println("Clic dans Weapon");
			break;
		case 1:
			key="shield";
			System.out.println("Clic dans Shield");
			break;
		case 2:
			key="armor";
			System.out.println("Clic dans Armor");
			break;
		case 3:
			key="ring";
			System.out.println("Clic dans Ring");
			break;
		}
		
		return key;
	}
	// Gestion de Dessin pour le Combat 
	
	public void drawFight(ApplicationContext ctx, Hero hero, ArrayList<Monster> mobs, ArrayList<String> fightProgress) {
		ctx.renderFrame(graphics -> drawFight(graphics, hero, mobs, fightProgress));
	}
	
	public void drawFight(Graphics2D graphics, Hero hero, ArrayList<Monster> mobs, ArrayList<String> fightProgress) {
		this.graphics = graphics;
		//Rectangle de Fond:
		
		int xFightZone = xPlayingZone+15;
		int yFightZone = yPlayingZone +15;
		int widthFightZone = widthPlayingZone-30;
		int heigthFightZone = 12*squareSize-30;
		int xStatFightZone = xFightZone +(2*widthFightZone/3);		
		int yTerminalZone = yFightZone + (2*heigthFightZone/3);
		
		int fontSizeText = Math.round((widthFightZone/6)/13);
		int fontSizeTitle = Math.round((widthFightZone/6)/10);	
		
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Double(xFightZone-3, yFightZone-3, widthFightZone+6, heigthFightZone+6));
		
		
		drawFightZone(xFightZone, yFightZone, 2*widthFightZone/3, heigthFightZone, fontSizeTitle, fontSizeText, hero, mobs);
		drawStatsMob(xStatFightZone, yFightZone, (widthFightZone/3), heigthFightZone, fontSizeTitle, fontSizeText, mobs);
		drawFightProgress(xStatFightZone, yTerminalZone, (widthFightZone/3), (heigthFightZone/3), fontSizeTitle, fontSizeText, fightProgress);
		graphics.setColor(Color.WHITE);
		graphics.drawLine(xStatFightZone, yFightZone, xStatFightZone, yFightZone+heigthFightZone);
		graphics.drawLine(xStatFightZone, yTerminalZone, xFightZone+widthFightZone, yTerminalZone);
		
	}
	
	private void drawFightZone(int x,int y,int widthZone,int heigthZone, int fontSizeTitle, int fontSizeText, Hero hero, ArrayList<Monster> mobs) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Double(x, y, widthZone, heigthZone));
		
		BufferedImage img = stringToImage("pictures/Entities/hero.png");
		
		drawAnEntityInFight(x+(widthZone/5), y+2*heigthZone/5, img, heigthZone);
		drawAnHealthBar(x+10,y+(2*heigthZone/5),(widthZone/5)-20, (heigthZone/5),hero, fontSizeTitle);
		
		int i=0;
		int monsterNumber=1;
		for(Monster mob:mobs) {
			img= stringToImage(mob.pictureFight());
			drawAnEntityInFight(x+(3*widthZone/5), y+i*heigthZone/5, img, heigthZone);
			
			drawAnHealthBar((int)(x+(4*widthZone/5)+10), y+(i*heigthZone/5)+heigthZone/15, (widthZone/5)-20, (heigthZone/5),  mob, fontSizeTitle);
			graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle*2));
			graphics.setColor(Color.WHITE);
			graphics.drawString(monsterNumber+"", x+(3*widthZone/5), y+(i)*heigthZone/5+fontSizeTitle*2);
			i++;
			monsterNumber++;
		}
	}

	public void drawAnEntityInFight(int x, int y, BufferedImage img, int heigthFightZone) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(heigthFightZone / ((double) img.getWidth()*5), heigthFightZone / ((double) img.getHeight()*5)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, x, y);
	}
	
	private void drawAnHealthBar(int x, int y, int width, int heigth, AbstractEntities entity, int fontSize) {
		int heigthBar = heigth/5;
		int yBar = y+heigthBar;

		graphics.setColor(new Color(222, 239, 217));
		graphics.fill(new Rectangle2D.Double(x, yBar, width, heigthBar));
		
		graphics.setColor(new Color(75, 188, 36));
		graphics.fill(new Rectangle2D.Double(x, yBar, width*entity.hpPercentage(), heigthBar));
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSize));
		graphics.drawString(entity.hp()+"/"+entity.maxHp()+"HP", x, (y+3*heigth/5));
		
	}
	
	private void drawStatsMob(int x,int y,int widthZone,int heigthZone, int fontSizeTitle, int fontSizeText, ArrayList<Monster> mobs) {
		graphics.setColor(new Color(96, 34, 23));
		graphics.fill(new Rectangle2D.Double(x, y, widthZone, heigthZone));
		graphics.setColor(Color.WHITE);
		int line = 1;
		int monsterNumber = 1;
		for (Monster mob:mobs) {
			if (monsterNumber==3) {
				x+=widthZone/2;
				line=1;
			}
			if (monsterNumber==5) {
				x-=widthZone/4;
			}
			graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle));
			graphics.drawString("-- Monstre "+monsterNumber+" --", x+5, y+(line*fontSizeTitle));
			line++;
			
			graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeText));
			graphics.drawString("-Damage: "+mob.damage(), x+5, y+(line*fontSizeTitle));
			line++;
			graphics.drawString("-Defense: "+mob.defense(), x+5, y+(line*fontSizeTitle));
			line++;
			graphics.drawString(" -Counter Attack: "+mob.counterAttack(), x, y+(line*fontSizeTitle));
			line++;
			graphics.drawString("-Evade: "+mob.evade(), x+5, y+(line*fontSizeTitle));
			line++;
			graphics.drawString("-Vampirism: "+mob.vampirism(), x+5, y+(line*fontSizeTitle));
			line++;
			graphics.drawString("-Regen: "+mob.regen(), x+5, y+(line*fontSizeTitle));
			line+=2;
			monsterNumber++;
		}
		
		
	}
	
	
	private void drawFightProgress(int x,int y,int widthZone,int heigthZone, int fontSizeTitle, int fontSizeText, ArrayList<String> fightProgress) {
		graphics.setColor(Color.DARK_GRAY);
		graphics.fill(new Rectangle2D.Double(x, y, widthZone, heigthZone));
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle));
		int line=1;
		for (String sentence:fightProgress) {
			graphics.drawString(""+sentence, x+5, y+(line*fontSizeTitle));
			line++;
		}
	}
	
	
	
}
