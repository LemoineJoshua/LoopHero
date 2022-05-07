package theGame.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import theGame.boardGame.Board;

public abstract class AbstractTile {
	private final  String type;
	protected  String pathToPicture;
	protected BufferedImage picture;
	
	/**
	 * AbstractTile constructor, which represent every tile on the gameboard
	 * 
	 * @param type : Type of the tile (road/roadside/field)
	 * @param pathToPicture : The path to the picture of the tile
	 */
	public AbstractTile(String type, String pathToPicture) {
		this.type = type;
		this.picture = stringToImage(pathToPicture);
		this.pathToPicture = pathToPicture;
	}
	
	
	/**
	 * 
	 * A function which transform the path of the picture to a buffered imagae
	 * 
	 * @param pictureName : the path to picture we want
	 * @return the buffered image at the precised path
	 */
	public BufferedImage stringToImage(String pictureName) {
		Path path = Path.of(pictureName);
		if (pictureName.equals("")) {return null;}
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			return img;
		} catch (IOException e) {
			throw new RuntimeException("probleme de dossier : " + path.getFileName());
		}
	}
	
	/**
	 * picture accessor
	 * 
	 * @return the picture of the tile
	 */
	public BufferedImage picture() {
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
}
