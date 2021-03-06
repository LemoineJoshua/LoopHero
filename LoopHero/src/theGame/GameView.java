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
import theGame.cards.Card;
import theGame.entities.AbstractEntities;
import theGame.entities.Hero;
import theGame.entities.AbstractMonster;
import theGame.inventories.HeroStuff;
import theGame.inventories.Item;
import theGame.inventories.ItemInventory;
import theGame.tiles.AbstractRoad;
import theGame.tiles.AbstractTile;

public class GameView {
	private final float width;
	private final float heigth;
	private final int heigthPlayingZone;
	private final int squareSize;
	private final int widthPlayingZone;
	private final int xPlayingZone;
	private final int yPlayingZone;
	private Graphics2D graphics;
	private boolean saveExist=true; 
	
	/**
	 * GameView constructor : 
	 * Memorize the screen width and heigth
	 * The screen et heigth board, and his coords
	 * 
	 * @param ctx : Global context of the game
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
	 * Draw the whole game interface except the board and the cards
	 * 
	 * @param timeData : Data related to the course of time in the game
	 * @param gameData : All the data used in the game
	 */
	public void drawInterface(TimeData timeData, GameData gameData) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(0, heigth/10, 5*width/6, heigth));
		
		graphics.setColor(Color.DARK_GRAY);
		graphics.fill(new Rectangle2D.Float(0, 0, width, heigth/10));
		
		drawTimeBar(timeData, gameData);
		
		drawLogo();
		
		if (timeData.isStopped()) {
			BufferedImage img = stringToImage("pictures/HUD/stop.png"); 
			drawHero(5, -1, img);
			graphics.drawString("Planning Mode", xPlayingZone + 6*squareSize , yPlayingZone/2 + 30 );
		}		
		int fontSize = Math.round(((5*width/6) - (xPlayingZone+widthPlayingZone))/13);
		gameData.ressourcesInventory().drawRessources(xPlayingZone+21*squareSize+5, yPlayingZone+14, graphics, squareSize, fontSize);	

		drawHud(gameData);
	}
	
	/**
	 * Function which draw the logo at the top right of the screen
	 */
	private void drawLogo() {
		BufferedImage img = stringToImage("pictures/HUD/logo.png"); 
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(yPlayingZone / ((double) img.getWidth()), yPlayingZone / ((double) img.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, 21*squareSize, 0);
	}
	
	
	/**
	 * Draw the HUD on the right of the screen, with all the items and the hero's stats
	 * 
	 * @param gameData :  All the data used in the game
	 */
	public void drawHud(GameData gameData) {
		// Draw the cell, where the items will be placed 
		
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
		graphics.drawString("My equipment",(float) (5*width/6 +10),yPlayingZone-10);
		
		for (int i=0; i<4;i++) {
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6  + i*width/24),yPlayingZone);
		}
		
		double yStuffCell = yPlayingZone+2*cellSize;
		graphics.drawString("My Inventory",(float) (5*width/6 +10),(float) (yStuffCell-10));
		
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
		
		// Draw the red Zone with hero's stats
		double yStuffStat = yStuffCell+4*cellSize;				
		drawStats(gameData.board().hero(), yStuffStat, fontSize);	
	}
		
	/**
	 * Draw a square on the slot where the player can place the item he choose, and display the stats
	 * 
	 * @param gameData : All the data used in the game
	 * @param cellSize : The size of one item cell
	 * @param y : The y coord of the first line of cell, in the item inventory
	 */
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
		
	/**
	 * Display the stats of the item, that the player choose, and the stats of the item the player has already equipped  
	 *
	 * @param gameData : All the data used in the game
	 * @param y : The y coord of the first line of cell, in the item inventory
	 * @param cellSize : The size of one item cell
	 * @param type : The type of the item, the player has clicked
	 */
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
		graphics.drawString("=> Stuff ?quip? :",(float) x,(float) y+line*fontSizeTitle);
		line ++;
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeText));
		if (equippedStuff!=null) {
			graphics.drawString("- "+equippedStuff.type()+", rarity "+equippedStuff.rarity()+", lvl "+equippedStuff.loop(),(float) x,(float) y+line*fontSizeTitle);
			line++;
			for(Map.Entry<String,Double> entree : equippedStuff.stats().entrySet()){
				String statName=entree.getKey().toString();
				Double statValue=(Double)entree.getValue();
				if (statValue != 0) {
					graphics.drawString("- "+statName+": "+statValue,(float) x,(float) y+line*fontSizeTitle);
					line ++;
				}
			}
		}else {
			graphics.drawString("Pas d'objet ?quip?",(float) x,(float) y+line*fontSizeTitle);
			line ++;
		}
		line++;
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle));
		graphics.drawString("=> Stuff choisi :",(float) x,(float) y+line*fontSizeTitle);
		line ++;
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeText));
		Item selectionnedStuff = gameData.itemInventory().itemInventory().get(gameData.selectedItemIndex());
		graphics.drawString("- "+selectionnedStuff.type()+", rarity "+selectionnedStuff.rarity()+", lvl "+selectionnedStuff.loop(),(float) x,(float) y+line*fontSizeTitle);
		line++;
		for(Map.Entry<String,Double> entree : selectionnedStuff.stats().entrySet()){
			String statName=entree.getKey().toString();
			Double statValue=(Double)entree.getValue();
			if (statValue != 0) {
				graphics.drawString("- "+statName+": "+statValue,(float) x,(float) y+line*fontSizeTitle);
				line ++;
			}
		}
	}
	
	/**
	 * Display all the hero's stats
	 * 
	 * @param hero : The hero, his inventories and his stats
	 * @param y : the coord y of the beginning of the stats zone
	 * @param fontSize : the fontiSize we use
	 */
	private void drawStats(Hero hero, double y, int fontSize) {
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSize));
		BufferedImage img = stringToImage("pictures/HUD/Hud3.png");
		graphics.drawString("My statistics",(float) (5*width/6 +10),(float) y-10);
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance((width/6) / (double) img.getWidth(), (heigth-y) / (double) img.getHeight()),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int)Math.round(5*width/6), (int) Math.round(y));
		
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
	
	/**
	 * Draw every items, the player got in the inventory
	 * 
	 * @param itemInventory : Inventory that contains all the equipment the hero has collected, which can now be equipped.
	 * @param yStuffCell : the y coord of the first line of items inventory
	 */
	private void drawStuffInventory(ItemInventory itemInventory, double yStuffCell ) {
		int x=0;
		int y=0;
		double cellSize = (1*width/6)/4;
		BufferedImage img;
		AffineTransformOp scaling;
		for (Item item: itemInventory.itemInventory()) {
			getColorForItem(item);
			img = stringToImage(item.getImage());
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
	
	/**
	 * A switch to get the fine color about the item rarity
	 * 
	 * @param item : the item we need to choose the color
	 */
	private void getColorForItem(Item item) {
		switch (item.rarity()) {
		case 0 : 
			graphics.setColor(new Color(202, 202, 202));
			break;
		case 1 : 
			graphics.setColor(new Color(27, 175, 210));
			break;
		case 2 : 
			graphics.setColor(new Color(244, 214, 33));
			break;
		case 3 : 
			graphics.setColor(new Color(244, 167, 24));
			break;
		}
	}
	

	/**
	 * Draw every items, the player already equipped
	 * 
	 * @param stuff : The four items, the hero has equipped, 1 weapon, 1shield, 1ring and 1 armor
	 */
	private void drawStuffEquiped(HeroStuff stuff ) {
		double cellSize = (1*width/6)/4;
		Item item = stuff.get("weapon");
		BufferedImage img = stringToImage("pictures/Stuff/shield0.png");
		AffineTransformOp scaling;
		if (item!=null) {
			getColorForItem(item);
			img = stringToImage(item.getImage());
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 0*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 0*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		item = stuff.get("shield");
		if (item!=null) {	
			getColorForItem(item);
			img = stringToImage(item.getImage());;
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 1*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 1*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		item = stuff.get("armor");
		if (item!=null) {
			getColorForItem(item);
			img = stringToImage(item.getImage());
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 2*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 2*cellSize),(float) (yPlayingZone+cellSize/4));
		}
		item = stuff.get("ring");
		if (item!=null) {
			getColorForItem(item);
			img = stringToImage(item.getImage());
			scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(cellSize / (double) img.getWidth(), cellSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, (int)Math.round(5*width/6 + 3*cellSize), (int) Math.round(yPlayingZone));
			graphics.drawString(""+item.rarity(),(float) (5*width/6 + 3*cellSize),(float) (yPlayingZone+cellSize/4));
		}

		
	}
	
	
	/**
	 * Return the buffered image corresponding to the path given
	 * 
	 * @param pictureName : the picture path
	 * @return a buffered image of the path
	 */
	public static BufferedImage stringToImage(String pictureName) {
		if(pictureName=="") {
			return null;
		}
		
		Path path = Path.of(pictureName);
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			return img;
		} catch (IOException e) {
			throw new RuntimeException("probleme de dossier : " + path.getFileName());
		}
	}
	
	/**
	 * Draw a tile on the board
	 * 
	 *  
	 * @param x : the cell's column on the board
	 * @param y :the cell's line on the board
	 * @param img : the tile's picture
	 */
	public void drawATile(int x, int y, BufferedImage img) {		
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(squareSize / (double) img.getWidth(), squareSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, xPlayingZone + x * squareSize, yPlayingZone + y * squareSize);
	}
	
	/**
	 * Draw the hero on the board 
	 * 
	 * @param x : the hero's position column on the board
	 * @param y : the hero's position line on the board
	 * @param img : the hero's pictures
	 */
	public void drawHero(int x, int y, BufferedImage img) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(squareSize / ((double) img.getWidth()*2), squareSize / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int) Math.round(xPlayingZone + x * squareSize + squareSize*0.25), (int) Math.round(yPlayingZone + y * squareSize + squareSize * 0.25 ));
	}
	
	/**
	 * @param x : the monster's position column on the board
	 * @param y : the monster's position line on the board
	 * @param img : the monster's picture
	 * @param column : : the monster's position column on his cell
	 * @param line : the monster's position line on his cell
	 */
	public void drawAMonster(int x, int y, BufferedImage img, int column, int line) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(squareSize / ((double) img.getWidth()*3), squareSize / ((double) img.getHeight()*3)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, (int) Math.round(xPlayingZone + x * squareSize + (squareSize/2)*column) + squareSize/10, (int) Math.round(yPlayingZone + y * squareSize + (squareSize/2)*line )+ squareSize/10);
	}
	
	/**
	 * Draw all monsters that are on the board at this time
	 *
	 * @param gameData : All the data used in the game
	 */
	public void drawAllMob(GameData gameData) {
		for (Coord coord: gameData.board().coordList()) {	
			AbstractRoad caseAffiche = (AbstractRoad) gameData.board().boardMatrix()[coord.y()][coord.x()];
			int column = 0;
			int line = 0;
			for (AbstractMonster mob: caseAffiche.aliveMonster()) {
				if (column == 2) {
					column=0;
					line++;
				}
				BufferedImage img = mob.picture();
				drawAMonster(coord.x(),coord.y(),img,column,line);
				column ++;
			}
		}
	}
	
	/**
	 * Draw the time bar on the top of the board, and write the informations below
	 *  
	 * @param timeData : Data related to the course of time in the game
	 * @param gameData : All the data used in the game
	 */
	public void drawTimeBar(TimeData timeData, GameData gameData) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone * timeData.timeFraction(), 30));
		graphics.setColor(Color.BLACK);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone/2 - 30, widthPlayingZone, 30));
		
	
		BufferedImage img = stringToImage("pictures/HUD/hourglass.png"); 
		drawHero(0, -1, img);
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 30));		
		graphics.drawString("Loop : "+ gameData.board().loop(), xPlayingZone + squareSize , yPlayingZone/2 + 30 );	
		
		img = stringToImage("pictures/HUD/Speed1.png"); 
		drawHero(14, -1, img);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 30));		
		graphics.drawString("x"+Math.round(timeData.timeModifier()), xPlayingZone + 15*squareSize , yPlayingZone/2 + 30 );	
		
		img = stringToImage("pictures/HUD/Speed2.png"); 
		drawHero(18, -1, img);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 30));		
		graphics.drawString(+Math.round(timeData.fightModifier())+" sec", xPlayingZone + 19*squareSize , yPlayingZone/2 + 30 );	
	}
	
	/**
	 * Draw all the cards in the hands of the player
	 * 
	 * @param gameData : All the data used in the game
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
	 * Draw one card below the board
	 * 
	 * @param x : The card position on the x axis
	 * @param y : The card position on the y axis
	 * @param img : The card picture
	 */
	public void drawACard(int x, int y, BufferedImage img) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance( (width/10) / ((double) img.getWidth()*2), (heigth/4) / ((double) img.getHeight()*2)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, x, y);
	}
	
	/**
	 * Draw the whole game board
	 * 
	 * @param gameData : All the data used in the game
	 */
	public void drawBoard(GameData gameData) {
		
		graphics.setColor(Color.WHITE);
		graphics.draw(new Rectangle2D.Double(xPlayingZone, yPlayingZone, 21*squareSize, 12*squareSize));
		
		 for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if (!(gameData.board().boardMatrix()[y][x].picture()==null)) {
	        			AbstractTile tile = gameData.board().boardMatrix()[y][x];
	        			BufferedImage img=tile.picture();
	        			drawATile(x, y,img);
	        			if (tile.gotAMobWithAQuest()) {
	        				img=stringToImage("pictures/Tiles/quest.png");
		        			drawATile(x, y,img);
	        			}
	        			if (tile instanceof AbstractRoad && ((AbstractRoad) tile).isBeaconNearby(gameData.board())) {
	        				img=stringToImage("pictures/Tiles/beaconField.png");
		        			drawATile(x, y,img);
	        			}
	        			
	        		}
	        	}
	        }
		 
		BufferedImage img = stringToImage("pictures/Entities/heroB.png");
		drawHero(gameData.board().heroX(),gameData.board().heroY(),img);
		drawAllMob(gameData);
		drawCards(gameData);
		
		if (gameData.aCardIsSelected()) {
			Card myCard = gameData.cardInventory().cardList().get(gameData.selectedCardIndex());
			img = stringToImage("pictures/Tiles/selectRoadSide.png");
			for(int x=0;x<21;x++){
	        	for(int y=0;y<12;y++) {
	        		if (gameData.cardCanBePlaced(myCard, x, y)){
	        			drawATile(x, y, img);
	        		}
	        		
	        	}
	        }
		}	
    }
 
	
	/**
	 * Draw the whole screen
	 * 
	 * @param graphics : Draw item
	 * @param gameData : All the data used in the game
	 * @param timeData : Data related to the course of time in the game
	 */
	public void drawFrame(Graphics2D graphics, GameData gameData, TimeData timeData) {
		this.graphics = graphics;
		drawInterface(timeData, gameData);
		drawBoard(gameData);
	}
	
	/**
	 * Draw the whole screen
	 * 
	 * @param ctx : Global context of the game
	 * @param gameData : All the data used in the game
	 * @param timeData : Data related to the course of time in the game
	 */
	public void drawFrame(ApplicationContext ctx, GameData gameData, TimeData timeData) {
		ctx.renderFrame(graphics -> drawFrame(graphics, gameData, timeData)); 
	}
	
	/**
	 * Draw the whole screen
	 * 
	 * @param ctx : Global context of the game
	 * @param win : true if win, elsa false
	 * @param loose : true if loose, else false
	 */
	public void drawEnd(ApplicationContext ctx, boolean win, boolean loose, GameData gameData) {
		ctx.renderFrame(graphics -> drawEnd(graphics, win, loose, gameData));
	}
	
	public void drawEnd(Graphics2D graphics, Boolean win, Boolean loose, GameData gameData) {
		this.graphics=graphics;
		
		BufferedImage background = stringToImage("pictures/HUD/BackgroundIntro.png");
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance( width / ((double) background.getWidth()), heigth / ((double) background.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(background, scaling, 0, 0);
		
		BufferedImage menu = stringToImage("pictures/HUD/Menu.png");
		AffineTransformOp scaling2 = new AffineTransformOp(AffineTransform
				.getScaleInstance( (width/3) / ((double) menu.getWidth()), (heigth/3.5) / ((double) menu.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(menu, scaling2 ,Math.round(width/3),Math.round(heigth/3));		
		
		
		if (win) {
			graphics.setFont(new Font("Arial Black", Font.PLAIN, 40));
			graphics.setColor(Color.white);
			graphics.drawString("Victory!", (int)Math.round(width/2.15),(int)Math.round(heigth/2.1));
			graphics.setFont(new Font("Arial Black", Font.PLAIN, 25));
			graphics.drawString("Congratulations, well played !", (int)Math.round(width/2.6),(int)Math.round(heigth/1.85));			
			graphics.drawString("Your score was : "+gameData.ressourcesInventory().score(), (int)Math.round(width/2.4),(int)Math.round(heigth/1.65));
		}else if(loose) {
			graphics.setFont(new Font("Arial Black", Font.PLAIN, 40));
			graphics.setColor(Color.white);
			graphics.drawString("Loose ...", (int)Math.round(width/2.15),(int)Math.round(heigth/2.10));
			graphics.setFont(new Font("Arial Black", Font.PLAIN, 25));
			graphics.drawString("Good luck for the next one", (int)Math.round(width/2.45),(int)Math.round(heigth/1.85));
			graphics.drawString("Your score was : "+gameData.ressourcesInventory().score(), (int)Math.round(width/2.25),(int)Math.round(heigth/1.65));
		}
		
		
		
	}
	
	/**
	 * Draw the whole intro screen
	 * 
	 * @param ctx : Global context of the game
	 * @param newGame : check if player has selectionned to play a newGame
	 */
	public void drawIntro(ApplicationContext ctx, boolean newGame) {
		ctx.renderFrame(graphics -> drawIntro(graphics, newGame));
	}
	
	/**
	 * Draw the first screen of the game
	 * 
	 * @param graphics
	 * @param newGame : check if player has selectionned to play a newGame
	 */
	public void drawIntro(Graphics2D graphics, Boolean newGame) {
		this.graphics=graphics;
		
		BufferedImage background = stringToImage("pictures/HUD/BackgroundIntro.png");
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance( width / ((double) background.getWidth()), heigth / ((double) background.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(background, scaling, 0, 0);
		
		BufferedImage menu = stringToImage("pictures/HUD/Menu.png");
		AffineTransformOp scaling2 = new AffineTransformOp(AffineTransform
				.getScaleInstance( (width/3) / ((double) menu.getWidth()), (heigth/3.5) / ((double) menu.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(menu, scaling2 ,Math.round(width/3),Math.round(heigth/3));
		
		BufferedImage button = stringToImage("pictures/HUD/button.png");
		AffineTransformOp scaling3 = new AffineTransformOp(AffineTransform
				.getScaleInstance( (width/2) / ((double) menu.getWidth()), (heigth/4) / ((double) menu.getHeight())),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(button, scaling3 ,(int)Math.round(width/2.6),(int)Math.round(heigth/2.1));
		graphics.drawImage(button, scaling3 ,(int)Math.round(width/2.6),(int)Math.round(heigth/1.9));
		
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 15));
		graphics.setColor(Color.white);
		graphics.drawString("New Game", (int)Math.round(width/2.15),(int)Math.round(heigth/2));
		graphics.drawString("Continue", (int)Math.round(width/2.13),(int)Math.round(heigth/1.81));
		
		if(!saveExist) {
			graphics.drawString("There is no save file", (int)Math.round(width/2.3),(int)Math.round(heigth/1.68));
		}
		
		if(newGame) {
			graphics.setFont(new Font("Arial Black", Font.PLAIN, 15));
			graphics.setColor(Color.white);
			graphics.drawString("Choose a loop in the console", (int)Math.round(width/2.35),(int)Math.round(heigth/1.68) );
		}
		
	}
	
	/**
	 * inform that there is no save file
	 */
	public void noSave() {
		saveExist=false;
	}
	
	/**
	 * @param location : the location of the user click
	 * @return true if the player clicked on the Continue button, false otherwise
	 */
	public boolean onContinueButton(Point2D.Float location) {
		return (location.x>(width/2.6) && location.x<(width/2.6 + width/4.5) && (location.y>(heigth/1.9) && location.y<(heigth/1.9 + heigth/25)) );
	}
	
	/**
	 * @param location : the location of the user click
	 * @return true if the player clicked on the new game button, false otherwise
	 */
	public boolean onNewButton(Point2D.Float location) {
		return (location.x>(width/2.6) && location.x<(width/2.6 + width/4.5) && (location.y>(heigth/2.1) && location.y<(heigth/2.1 + heigth/25)) );
	}
	
	// Placing cards functions :
	
	
	/**
	 * Check if the player clicked in the card zone
	 * 
	 * @param location : Mouse click location 
	 * @return true if the player clicked in the card zone, else false
	 */
	public boolean clickInCardZone(Point2D.Float location) {
		return location.x < (4*width/5) && location.y >= heigth-(heigth/6);
	}
	
	/**
	 * Get the index of the card the player clicked
	 * 
	 * @param coordX : coord x of the player click
	 * @return the index of card the player clicked
	 */
	private int indexFromCardZone(float coordX) {
		int cardWidth = Math.round((4*width/5)/13);
		return (int) ((coordX) / cardWidth);
	}
	
	/**
	 * Get the index of the card the player clicked
	 * 
	 * @param coordX : coord x of the player click
	 * @return the index of card the player clicked (without giving access to the calculs)
	 */
	public int selectCard(float coordX) {
		return indexFromCardZone(coordX);
	}
	
	/**
	 * Check if the player clicked on the board
	 * 
	 * @param location : Mouse click location 
	 * @return true if the player clicked on the board, else false
	 */
	public boolean clickInBoardZone(Point2D.Float location) {
		return ((int) location.x < (xPlayingZone + 21*squareSize)) && ((int)location.x > (xPlayingZone)) && ((int)location.y< (yPlayingZone+12*squareSize)) && ((int)location.y > (yPlayingZone));
	}
	
	/**
	 * Return the line of the board where the player have clicked
	 * 
	 * @param coordY : coord y of the mouse click 
	 * @return the line the player clicked, to use it as an index in the board matrix
	 */
	public int selectLine(float coordY) {
		return (int) ((coordY-yPlayingZone) / (squareSize));
	}
	
	/**
	 * Return the column of the board where the player have clicked
	 * 
	 * @param coordX : coord x of the mouse click
	 * @return the column the player clicked, to use it as an index in the board matrix
	 */
	public int selectColumn(float coordX) {
		return (int) ((coordX-xPlayingZone) / (squareSize));
	}
	
	
	
	// Stuff equipping functions 
	
	/**
	 * Check if the player clicked in the item inventory zone
	 * 
	 * @param location : Mouse click location
	 * @return true if the player clicked in the item inventory zone
	 */
	public boolean clickInItemInventoryZone(Point2D.Float location) {
		return location.x > (5*width/6) && location.y >= yPlayingZone+2*((1*width/6)/4) && location.y<=yPlayingZone+5*((1*width/6)/4);
	}
	
	/**
	 * Get the index of the item, the player clicked
	 * 
	 * @param coordX : coord x of the mouse click
	 * @param coordY : coord y of the mouse click 
	 * @return the index of the item, the player clicked on
	 */
	private int indexFromItemInventory(float coordX, float coordY) {
		int cellSize = Math.round((1*width/6)/4);
		int x = (int)((coordX-(5*width/6)) / cellSize);
		int y = (int)((coordY-(yPlayingZone+2*cellSize)) / cellSize);
		int index = (x%4)+(4*y);
		return index;
	}
	
	/**
	 * Get the index of the item, the player clicked
	 * 
	 * @param coordX : coord x of the mouse click
	 * @param coordY : coord y of the mouse click 
	 * @return the index of the item, the player clicked on
	 */
	public int selectItemInInventory(float coordX, float coordY) {
		return indexFromItemInventory(coordX,coordY);
	}
	
	/**
	 * Check if the player clicked in the stuff zone
	 * 
	 * @param location : Mouse click location
	 * @return true if the player clicked in the stuff zone, else false
	 */
	public boolean clickInStuffZone(Point2D.Float location) {
		return location.x > (5*width/6) && location.y >= yPlayingZone && location.y<=yPlayingZone+1*((1*width/6)/4);
	}
	
	/**
	 * Get the type of item, of the cell where the player clicked
	 * 
	 * @param coordX : coord x of the mouse click
	 * @return the type of item, in the cell the player clicked
	 */
	public String getItemKey(float coordX) {
		int cellSize = Math.round((1*width/6)/4);
		int x = (int)((coordX-(5*width/6)) / cellSize);
		String key = "";
		
		switch (x) {
		case 0:
			key="weapon";
			break;
		case 1:
			key="shield";
			break;
		case 2:
			key="armor";
			break;
		case 3:
			key="ring";
			break;
		}
		
		return key;
	}
	
	
	// Fight Drawing 
	
	/**
	 * Draw the whole fight Zone
	 * 
	 * @param ctx : Global context of the game
	 * @param hero : The hero, his inventories and his stats
	 * @param mobs : The list of all monsters who are in the Fight
	 * @param fightProgress : A list of sentence which explained the fight
	 */
	public void drawFight(ApplicationContext ctx, Hero hero, ArrayList<AbstractMonster> mobs, ArrayList<String> fightProgress, int monsterIndexAttack, int attackerIndex) {
		ctx.renderFrame(graphics -> drawFight(graphics, hero, mobs, fightProgress, monsterIndexAttack, attackerIndex));
	}
	
	/**
	 * Draw the whole fight Zone
	 * 
	 * @param graphics
	 * @param hero : The hero, his inventories and his stats
	 * @param mobs : The list of all monsters who are in the Fight
	 * @param fightProgress : A list of sentence which explained the fight
	 * @param monsterIndexAttack : The index of the monster, the ennemy target
	 * @param attackerIndex : the index of the entity attacking
	 */
	public void drawFight(Graphics2D graphics, Hero hero, ArrayList<AbstractMonster> mobs, ArrayList<String> fightProgress, int monsterIndexAttack, int attackerIndex) {
		this.graphics = graphics;		
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
		
		
		drawFightZone(xFightZone, yFightZone, 2*widthFightZone/3, heigthFightZone, fontSizeTitle, fontSizeText, hero, mobs, monsterIndexAttack, attackerIndex);
		drawStatsMob(xStatFightZone, yFightZone, (widthFightZone/3), heigthFightZone, fontSizeTitle, fontSizeText, mobs);
		drawFightProgress(xStatFightZone, yTerminalZone, (widthFightZone/3), (heigthFightZone/3), fontSizeTitle, fontSizeText, fightProgress);
		graphics.setColor(Color.WHITE);
		graphics.drawLine(xStatFightZone, yFightZone, xStatFightZone, yFightZone+heigthFightZone);
		graphics.drawLine(xStatFightZone, yTerminalZone, xFightZone+widthFightZone, yTerminalZone);		
		drawStats(hero, yPlayingZone+6*((1*width/6)/4),(int)(((1*width/6)/4))/3);
	}
	
	/**
	 * Draw the left Rectangle of the fight zone, where the fight take place
	 * 
	 * @param x : coord x of the beginning of this rectangle
	 * @param y : coord y of the beginning of this rectangle
	 * @param widthZone : width of this zone
	 * @param heigthZone : heigth of this zone
	 * @param fontSizeTitle : font size used for the title
	 * @param fontSizeText : font size used for the classic text
	 * @param hero : The hero, his inventories and his stats
	 * @param mobs : The list of all monsters who are in the Fight
	 * @param monsterIndexAttack : The index of the monster, the ennemy target
	 * @param attackerIndex : the index of the entity attacking
	 */
	private void drawFightZone(int x,int y,int widthZone,int heigthZone, int fontSizeTitle, int fontSizeText, Hero hero, ArrayList<AbstractMonster> mobs, int monsterIndexAttack, int attackerIndex) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Double(x, y, widthZone, heigthZone));
		
		BufferedImage img = null;
		if (hero.isDead()) {
			img = stringToImage("pictures/Entities/hero3.png");		
		}else if(attackerIndex == -1) {
			img = stringToImage("pictures/Entities/hero2.png");		
		}else {
			img = stringToImage("pictures/Entities/hero.png");				
		}
		
		drawAnEntityInFight(x+(widthZone/5), y+2*heigthZone/5, img, heigthZone);
		drawAnHealthBar(x+10,y+(2*heigthZone/5),(widthZone/5)-20, (heigthZone/5),hero, fontSizeTitle);
		
		int i=0;
		int monsterNumber=1;
		for(AbstractMonster mob:mobs) {
			if (mob.isDead()) {
				img = mob.pictureFightDeath();
			}else if(attackerIndex == monsterNumber) {
				img = mob.pictureFightAtt();
			}else {
				img= mob.pictureFight();				
			}
			drawAnEntityInFight(x+(3*widthZone/5), y+i*heigthZone/5, img, heigthZone);
			if (i == monsterIndexAttack) {
				img = stringToImage("pictures/HUD/arrow.png");
				AffineTransformOp scaling = new AffineTransformOp(AffineTransform
						.getScaleInstance(heigthZone / ((double) img.getWidth()*15), heigthZone / ((double) img.getHeight()*15)),
						AffineTransformOp.TYPE_BILINEAR);
				graphics.drawImage(img, scaling,(int) (x+(2.33*widthZone/5)), (int)(y+i*heigthZone/5+0.33*heigthZone/5));
			}
			drawAnHealthBar((int)(x+(4*widthZone/5)+10), y+(i*heigthZone/5)+heigthZone/15, (widthZone/5)-20, (heigthZone/5),  mob, fontSizeTitle);
			graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSizeTitle*2));
			graphics.setColor(Color.WHITE);
			graphics.drawString(monsterNumber+"", x+(3*widthZone/5), y+(i)*heigthZone/5+fontSizeTitle*2);
			i++;
			monsterNumber++;
		}
	}

	/**
	 * Draw one entity in the fight
	 * 
	 * @param x : the coord x of the entity
	 * @param y : the coord y of the entity
	 * @param img : the entity picture
	 * @param heigthFightZone : the heigth of the left zone of the fight zone
	 */
	public void drawAnEntityInFight(int x, int y, BufferedImage img, int heigthFightZone) {
		AffineTransformOp scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(heigthFightZone / ((double) img.getWidth()*5), heigthFightZone / ((double) img.getHeight()*5)),
				AffineTransformOp.TYPE_BILINEAR);
		graphics.drawImage(img, scaling, x, y);
	}
	
	/**
	 * Draw the health bar and the health of an entity
	 * 
	 * @param x : the coord x of the health bar beginning
	 * @param y : the coord y of the health bar beginning
	 * @param width : the width of the health bar
	 * @param heigth : the heigth of the health bar
	 * @param entity : the entity to who belong the health bar
	 * @param fontSize : the font size used to draw the health below the bar
	 */
	private void drawAnHealthBar(int x, int y, int width, int heigth, AbstractEntities entity, int fontSize) {
		int heigthBar = heigth/5;
		int yBar = y+heigthBar;

		
		if (entity instanceof AbstractMonster) {
			AbstractMonster monster= (AbstractMonster) entity;
			if (monster.gotAQuest()) {
				graphics.setColor(new Color(226, 238, 51));
				graphics.fill(new Rectangle2D.Double(x-5, yBar-5, width+10, heigthBar+10));
			}
		}
		
		graphics.setColor(new Color(222, 239, 217));
		graphics.fill(new Rectangle2D.Double(x, yBar, width, heigthBar));
		
		graphics.setColor(new Color(75, 188, 36));
		graphics.fill(new Rectangle2D.Double(x, yBar, width*entity.hpPercentage(), heigthBar));
		
		
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSize));
		graphics.drawString(entity.hp()+"/"+entity.maxHp()+"HP", x, (y+3*heigth/5));
		
	}
	
	/**
	 * Draw the top right rectangle of the fight zone, where is displayed all monsters stats
	 * 
	 * @param x : the coord x of the beginning of the stats zone
	 * @param y : the coord y of the beginning of the stats zone
	 * @param widthZone : the width of the beginning of the stats zone
	 * @param heigthZone : the heigth of the beginning of the stats zone
	 * @param fontSizeTitle : font size used for the title
	 * @param fontSizeText : font size used for the classic text
	 * @param mobs : The list of all monsters who are in the Fight
	 */
	private void drawStatsMob(int x,int y,int widthZone,int heigthZone, int fontSizeTitle, int fontSizeText, ArrayList<AbstractMonster> mobs) {
		graphics.setColor(new Color(96, 34, 23));
		graphics.fill(new Rectangle2D.Double(x, y, widthZone, heigthZone));
		graphics.setColor(Color.WHITE);
		int line = 1;
		int monsterNumber = 1;
		for (AbstractMonster mob:mobs) {
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
	
	
	/**
	 * Draw the little terminal in the bottom right of the figth zone
	 * In this terminal is displayed all information about the fight progression
	 * 
	 * @param x : the coord x of the beginning of the terminal zone
	 * @param y : the coord y of the beginning of the terminal zone
	 * @param widthZone : the width of the beginning of the terminal zone
	 * @param heigthZone : the heigth of the beginning of the terminal zone
	 * @param fontSizeTitle : font size used for the title
	 * @param fontSizeText : font size used for the classic text
	 * @param fightProgress : A list of sentence which resume the progression of the figth
	 */
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
