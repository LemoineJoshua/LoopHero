package theGame.inventories;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Item {
	
	private final HashMap<String,Double> stats;
	private final int rarity;
	private final BufferedImage image;
	private final String type;
	
	/**
	 * Item constructor
	 * 
	 * @param hp : The item max hp stat
	 * @param strength : The item strength
	 * @param defense : The item defense
	 * @param counterAttack : The item chance to counter attack
	 * @param regen : The item regen
	 * @param evade : The item chance to evade 
	 * @param vampirism : The item vampirism percentage
	 * @param rarity : The item rarity
	 * @param image : The path to the item picture
	 * @param type : the type of the item
	 */
	public Item(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,int rarity,String image,String type) {
		this.stats=new HashMap<String,Double>() {{
			put("maxHp",(double) Math.round(hp));
			put("strength",(double) Math.round(strength));
			put("defense",(double) Math.round(defense));
			put("counterAttack",(double) Math.round(counterAttack));
			put("regen",(double) Math.round(regen));
			put("evade",(double) Math.round(evade));
			put("vampirism",(double) Math.round(vampirism));
		}};
		this.rarity=rarity;
		this.image=stringToImage(image);
		this.type=type;
	}
	
	/**
	 * Change to path to the image to a bufferedImage
	 * 
	 * @param picturePath : the path of the item picture
	 * @return the picture at the indicated path
	 */
	public BufferedImage stringToImage(String picturePath) {
		Path path = Path.of(picturePath);
		if (picturePath.equals("")) {return null;}
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			return img;
		} catch (IOException e) {
			throw new RuntimeException("probleme de dossier : " + path.getFileName());
		}
	}

	/**
	 * Stats accessor
	 * 
	 * @return the item statistics
	 */
	public HashMap<String,Double> stats(){
		return stats;
	}

	/**
	 * Picture accessor
	 * 
	 * @return the picture of the item
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Type accessor
	 * 
	 * @return the type of the item
	 */
	public String type() {
		return type;
	}
	
	/**
	 * Rarity accessor
	 * 
	 * @return the rarity of the item
	 */
	public int rarity() {
		return rarity;
	}
	
	
	/**
	 * The function to print correctly the item
	 */
	@Override
	public String toString() {
		String retour = "";
		for(Map.Entry<String,Double> entree : stats.entrySet()) {
			retour+=(" stat : "+entree.getKey() + " | value : "+entree.getValue());
		}
		return retour;
	}
}
