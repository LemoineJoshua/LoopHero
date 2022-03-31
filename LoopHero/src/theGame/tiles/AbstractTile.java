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
	 * Constructeur de la classe AbstracTile qui représente toute les cases du plateau de jeu
	 * 
	 * @param type type de la case (road/sideRoad/Field)
	 * @param pathToPicture Le chemin vers l'image de la case
	 */
	public AbstractTile(String type, String pathToPicture) {
		this.type = type;
		this.picture = stringToImage(pathToPicture);
		this.pathToPicture = pathToPicture;
	}
	
	
	/**
	 * Fonction permettant de récupérer l'image avec son chemin
	 * 
	 * @param pictureName chemin de l'image que l'on veut avoir
	 * @return Une image créee à partir du chemin fourni
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
	
	public BufferedImage picture() {
		return picture;
	}
	
	
	/**
	 * Regarde tout autour de la case si il y a une tuile 'Meadow'
	 * a faire fleurir (synergie)
	 * 
	 * @param board le plateau de jeu
	 * @param x la colonne de la case
	 * @param y la ligne de la case
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
	
	
	/**
	 * Fonction représentant l'effet du jour d'une tuile
	 * 
	 * @param board le plateau de jeu
	 */
	public void dailyEffect(Board board){}
	
	/**
	 * Fonction représentant l'effet d'un tour de boucle  d'une tuile
	 * 
	 * @param board le plateau de jeu
	 */
	public void loopEffect(Board board){}
	
	
	/**
	 * @return true si la case est vide false sinon
	 */
	public boolean isEmpty(){
		return true;
	}
	
	/**
	 * @return le type de la case (road/sideRoad/Field)
	 */
	public String type() {
		return type;
	}
}
