package theGame.inventories;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
	private Item(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,int rarity,String image,String type) {
		this.stats=new HashMap<String,Double>() {{
			put("maxHp",(double) Math.round(hp));
			put("strength",(double) Math.round(strength));
			put("defense",(double) Math.round(defense));
			put("counterAttack",counterAttack);
			put("regen",(double) regen);
			put("evade",(double) evade);
			put("vampirism",(double) vampirism);
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
	
	public static Item getAnItem(int loop) {
		double prob[] = {0,0,0,0};
		if(loop<3) {
			prob[0]=0.66;
			prob[1]=0.33;
			prob[2]=0;
			prob[3]=1;
		}else {
			prob[0]=0.65;
			prob[1]=0.35;
			prob[2]=0.15;
			prob[3]=0;
		}
		
		Item item = null;
		double rarity = Math.random();
		if(rarity>prob[0]) {
			item = generalItem(loop,0);
		}else if(rarity>prob[1]){
			item = generalItem(loop,1);
		}else if(rarity>prob[2]) {
			item = generalItem(loop,2);
		}else if(rarity>prob[3]) {
			item = generalItem(loop,3);
		}	
		return item;
	}
	
	static private String rollAStat(ArrayList<String> statsSup){
		
		ArrayList<String> statList = new ArrayList<>();
		statList.add("defense");
		statList.add("counterAttack");
		statList.add("regen");
		statList.add("evade");
		statList.add("vampirism");
		
		String newStat = statList.get((int)Math.round(Math.random()*4));
		while(statsSup.contains(newStat)) {
			newStat = statList.get((int)Math.round(Math.random()*4));
		}
		
		return newStat;
	}
	
	static private Item generalItem(int loop, int rarity) {
		double hp = 0;
		double strength = 0;
		double defense = 0;
		double counterAttack = 0;
		double regen = 0;
		double evade = 0;
		double vampirism = 0;
		String type;
		ArrayList<String> statSup = new ArrayList<>();
		ArrayList<String> newStat = new ArrayList<>();
		String statSup2 = "";
		double percentage=0;
		
		switch(rarity) {
			case 0:
				percentage=1;
				break;
			case 1:
				percentage=0.9;
				break;
			default:
				percentage=0.01*(Math.round(80 + (Math.random()*10)));
		}
		
		
		double Itemtype = Math.random();
		if(Itemtype>0.75) {
			type = "weapon";
			strength =percentage* ((loop*4)+Math.random()*(loop*6 - loop*4));
		}else if(Itemtype>0.5){
			type = "shield";
			defense =percentage* 4*loop;
			newStat.add("defense");
		}else if(Itemtype>0.25) {
			type = "armor";
			hp = percentage*((loop*80)+(Math.random()*(loop*100 - loop*80)));	
		}else {
			type = "ring";
			double stat = Math.random();
			System.out.println("stat :"+stat+"\n");
			if(stat>0.80) {
				defense = percentage*loop * 1.5;
				newStat.add("defense"); 
			}else if(stat>0.60) {
				counterAttack = 0.01*(percentage*(8 + (loop - 1) * 4));
				newStat.add("counterAttack"); 
			}else if(stat>0.40) {
				regen = 0.01*(percentage*loop * 0.6);
				newStat.add("regen"); 
			}else if(stat>0.20) {
				evade = 0.01*(percentage*(8 + (loop - 1) * 2));
				newStat.add("evade"); 
			}else {
				vampirism =0.01* percentage*((8 + (loop - 1) * 1.5));
				newStat.add("vampirism");
			}
		}
		
		for(int i=0;i<rarity;i++) {
			String tmp = rollAStat(newStat);
			statSup.add(tmp);
			newStat.add(tmp);
		}
		statSup2 = rollAStat(newStat); 
		
		if(statSup.contains("defense")) {
			defense = 0.5*(loop * 1.5);
		}
		if(statSup.contains("counterAttack")) {
			counterAttack = 0.05*(8 + (loop - 1) * 4);
		}
		if(statSup.contains("regen")) {
			regen = 0.05*(loop * 0.6);
		}
		if(statSup.contains("evade")) {
			evade = 0.05*(8 + (loop - 1) * 2);
		}
		if(statSup.contains("vampirism")) {
			vampirism =0.05* (8 + (loop - 1) * 1.5);
		}
		
		if(rarity==3) {
			if(statSup2.equals("defense")) {
				defense = (loop * 1.5);
			}
			if(statSup2.equals("counterAttack")) {
				counterAttack = 0.01*(8 + (loop - 3) * 4);
			}
			if(statSup2.equals("regen")) {
				regen = 0.01*(loop-2 * 0.6);
			}
			if(statSup2.equals("evade")) {
				evade = 0.01*(8 + (loop - 3) * 2);
			}
			if(statSup2.equals("vampirism")) {
				vampirism = 0.01*(8 + (loop - 3) * 1.5);
			}
		}
		
		return new Item(hp,strength,defense,counterAttack,regen,evade,vampirism,rarity,"pictures/Stuff/"+type+""+rarity+".png",type);
	}
	
}
