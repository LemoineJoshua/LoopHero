package theGame.Cards;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class Card {
	private final String name;
	private final String type;
	private final BufferedImage img;
	
	/**
	 * @param name nom de la carte
	 * @param type type de la carte
	 * @param path chemin vers l'image 
	 */
	public Card(String name,String type,String path) {
		this.name=name;
		this.type=type;
		this.img=stringToImage(path);
	}
	
	/**
	 * Crée une image à partir d'un chemin
	 * 
	 * @param pictureName chemin de l'image
	 * @return image crée à partir du chemin
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
	 * @return le type
	 */
	public String type() {
		return type;
	}
	
	/**
	 * @return le nom
	 */
	public String name() {
		return name;
	}
	
	/**
	 * @return l'image
	 */
	public BufferedImage img(){
		return img;
	}
}
