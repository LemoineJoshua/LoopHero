package theGame.inventories;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class Item {
	
	private final long hp; 
	private final double strength;
	private final double defense;
	private final double counterAttack;
	private final double regen;
	private final double evade;
	private final double vampirism;
	private final String rarity;
	private final BufferedImage image;
	
	public Item(long hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,String rarity,String image) {
		this.hp=hp;
		this.strength=strength;
		this.defense=defense;
		this.counterAttack=counterAttack;
		this.regen=regen;
		this.evade=evade;
		this.vampirism=vampirism;
		this.rarity=rarity;
		this.image=stringToImage(image);
	}
	
	/**
	 * @param pictureName
	 * @return l'image contenue dans le dossier 'pictureName'
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

	public long getHp() {
		return hp;
	}

	public double getStrength() {
		return strength;
	}

	public double getDefense() {
		return defense;
	}

	public double getCounterAttack() {
		return counterAttack;
	}

	public double getRegen() {
		return regen;
	}

	public double getEvade() {
		return evade;
	}

	public double getVampirism() {
		return vampirism;
	}

	public String getRarity() {
		return rarity;
	}

	public BufferedImage getImage() {
		return image;
	}
	
	
}
