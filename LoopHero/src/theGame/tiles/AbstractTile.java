package theGame.tiles;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.GameData;
import theGame.GameView;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;

public abstract class AbstractTile implements Serializable {
	private final  String type;
	protected String pathPicture;
	protected transient BufferedImage picture;
	
	/**
	 * AbstractTile constructor, which represent every tile on the gameboard
	 * 
	 * @param type : Type of the tile (road/roadside/field)
	 * @param pathToPicture : The path to the picture of the tile
	 */
	public AbstractTile(String type, String pathToPicture) {
		this.type = Objects.requireNonNull(type);
		this.pathPicture = Objects.requireNonNull(pathToPicture);
		this.picture=GameView.stringToImage(pathPicture);
	}
	
	/**
	 * picture accessor
	 * 
	 * @return the picture of the tile
	 */
	public BufferedImage picture() {
		if(picture==null) {
			this.picture=GameView.stringToImage(pathPicture);
		}
		return picture;
	}
	
	
	/**
	 * Check every cell around, to know if there is a Meadow tile to bloom (synergy)
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 * @param x : the column of the cell
	 * @param y : the line of the cell
	 */
	public void searchMeadowtoBloom(Board board, int x, int y) {
		Objects.requireNonNull(board);
		for (int i= x-1;i<x+2; i+=2) {
			if(i<0 || i>20) {continue;}
				if(board.boardMatrix()[y][i] instanceof Meadow){
					Meadow meadow = (Meadow) board.boardMatrix()[y][i];				
					meadow.becomingBloom();
				}
		}
		for (int j= y-1;j<y+2; j+=2) {
			if(j>11 || j<0 ) {continue;}
				if(board.boardMatrix()[j][x] instanceof Meadow){
					Meadow meadow = (Meadow) board.boardMatrix()[j][x];
					meadow.becomingBloom();
				}
		}
	}
	
	public void searchMeadowtoUnbloom(Board board, int x, int y) {
		Objects.requireNonNull(board);
		for (int i= x-1;i<x+2; i+=2) {
			if(i<0 || i>20) {continue;}
				if(board.boardMatrix()[y][i] instanceof Meadow){
					Meadow meadow = (Meadow) board.boardMatrix()[y][i];		
					meadow.becomingUnbloom();
					meadow.searchToBloom(i, y, board);;
				}
		}
		for (int j= y-1;j<y+2; j+=2) {
			if(j>11 || j<0 ) {continue;}
				if(board.boardMatrix()[j][x] instanceof Meadow){
					Meadow meadow = (Meadow) board.boardMatrix()[j][x];
					meadow.becomingUnbloom();
					meadow.searchToBloom(x, j, board);;
				}
		}
	}
	
	
	/**
	 * Apply the daily effect of the tile
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 */
	public void dailyEffect(Board board){}
	
	/**
	 * Apply the loop effect of the tile
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 */
	public void loopEffect(Board board){}
	
	/**
	 * Remove the effect of the tile, after an oblivion is used
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 */
	public void removingEffect(Board board) {}
	
	
	/**
	 * Deal with all effect that happened, when the hero come on a tile 
	 * 
	 * @param gameData : All the data used in the game
	 */
	public void enteringEffect(GameData gameData) {}
	
	/**
	 * Check if the cell is Empty, which mean we could place a card on it
	 * 
	 * @return true if the cell is empty, false else
	 */
	public boolean isEmpty(){
		return true;
	}
	
	/**
	 * Type accessor
	 * 
	 * @return the type of the tile (road/roadSide/field)
	 */
	public String type() {
		return type;
	}
	
	/**
	 * Check if we can the oblivion card on this tile
	 * 
	 * @return true if we can use it, false else.
	 */
	public boolean isOblivionable() {
		return true;
	}
	
	/**
	 * @return true if the tile is a beacon
	 */
	public boolean imABeacon() {
		return false;
	}
	
	/**
	 * Search for a village nearby in order to place a WheatField
	 * 
	 * @param matrix
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean wheatFieldCanBePlaced(AbstractTile[][] matrix, int x, int y) {
		Objects.requireNonNull(matrix);
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		if (!(matrix[y][x] instanceof AbstractRoad && matrix[y][x].isEmpty())) {
			return false;
		}
		for(Coord coord : posibilities) {
			if((y+coord.y()<12 && y+coord.y()>=0) && (x+coord.x()<21 && x+coord.x()>=0)) {
				if(matrix[y+coord.y()][x+coord.x()] instanceof Village) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean gotAMobWithAQuest() {
		if (!(this instanceof AbstractRoad)) {
			return false;
		}else {
			AbstractRoad tile = (AbstractRoad) this;
			for (AbstractMonster monster : tile.aliveMonster) {
				if (monster.gotAQuest()) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
