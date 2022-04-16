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
	
	public Item(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,int rarity,String image,String type) {
		this.stats=new HashMap<String,Double>() {{
			put("maxHp",hp);
			put("strength",strength);
			put("defense",defense);
			put("counterAttack",counterAttack);
			put("regen",regen);
			put("evade",evade);
			put("vampirism",vampirism);
		}};
		this.rarity=rarity;
		this.image=stringToImage(image);
		this.type=type;
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

	/**
	 * @return les stats de l'objet
	 */
	public HashMap<String,Double> stats(){
		return stats;
	}

	/**
	 * @return l'iamge de l'item
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	public String type() {
		return type;
	}
	
	public int rarity() {
		return rarity;
	}
	
	@Override
	public String toString() {
		String retour = "";
		for(Map.Entry entree : stats.entrySet()) {
			retour+=(" stat : "+entree.getKey() + " | value : "+entree.getValue());
		}
		return retour;
	}
}
