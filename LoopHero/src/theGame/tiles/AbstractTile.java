package theGame.tiles;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public abstract class AbstractTile {
	private final  String type;
	protected  String pathToPicture;
	protected BufferedImage picture;
	
	public AbstractTile(String type, String pathToPicture) {
		this.type = type;
		this.picture = stringToImage(pathToPicture);
		this.pathToPicture = pathToPicture;
	}
	
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
	
	public void searchMeadowtoBloom(Board board, int x, int y) {
		for (int i= x-1;i<x+2; i+=2) {
			if(board.boardMatrix()[y][i] instanceof Meadow){
				Meadow meadow = (Meadow) board.boardMatrix()[y][i];
				System.out.println("bmoo");
				meadow.becomingBloom();
			}
		}
		for (int j= y-1;j<y+2; j+=2) {
			if(board.boardMatrix()[j][x] instanceof Meadow){
				Meadow meadow = (Meadow) board.boardMatrix()[j][x];
				System.out.println("bmoo");
				meadow.becomingBloom();
			}
		}
	}
	
	public void dailyEffect(Board board){}
	public void loopEffect(Board board){}
	
	public boolean isEmpty(){
		return true;
	}
	
	public String type() {
		return type;
	}
}
