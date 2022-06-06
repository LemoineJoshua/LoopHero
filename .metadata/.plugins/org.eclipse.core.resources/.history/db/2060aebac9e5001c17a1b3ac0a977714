package theGame.inventories;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Item implements Serializable {
	
	private static final long serialVersionUID = -3780443853060191232L;
	private final HashMap<String,Double> stats;
	private final int rarity;
	private final String image;
	private final String type;
	private final int loop;
	
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
	 * @param loop: the loop number when we get the item
	 */
	private Item(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,int rarity,String image,String type, int loop) {
		this.stats=new HashMap<String,Double>() {{
			put("maxHp",(double) Math.round(hp));
			put("strength",(double) Math.round(strength));
			put("defense",(double) Math.round(defense));
			put("counterAttack", (double) round(counterAttack,2));
			put("regen",(double) round(regen,2));
			put("evade",(double) round(evade,2));
			put("vampirism",(double) round(vampirism,2));
		}};
		this.rarity=rarity;
		this.image=Objects.requireNonNull(image); 
		this.type=Objects.requireNonNull(type);
		this.loop=loop;
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
	public String getImage() {
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
	 * Loop Number accesor
	 * 
	 * @return the loop number of the item
	 */
	public int loop() {
		return loop;
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
	
	/**
	 * Choose a random rarity and create an item
	 * 
	 * @param loop : The loop number
	 * 
	 * @return a classic Item
	 */
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
	
	/**
	 * Choose a between 2 and 3 and create an item
	 * 
	 * @param loop : The loop number
	 * 
	 * @return a quest item (at least rarity 2)
	 */
	public static Item getAQuestItem(int loop) {
		Item item = null;
		if (loop <3 ) {
			item = generalItem(loop,2);
		}else {
			if (Math.random()>0.5) {
				item = generalItem(loop,2);
			}else {
				item = generalItem(loop,3);
			}
		}
		return item;
	}
		
	/**
	 * Select a type of item to create (Armor-Shield-Weapon-Ring), and return the item
	 * 
	 * @param loop : the loop number
	 * @param rarity : the rarirty of the item
	 * @return the created item
	 */
	static private Item generalItem(int loop, int rarity) {		
		double Itemtype = Math.random();
		if(Itemtype>0.75) {
			return classicItem(loop,rarity, "weapon");
		}else if(Itemtype>0.5){
			return classicItem(loop,rarity, "shield");
		}else if(Itemtype>0.25) {
			return classicItem(loop,rarity, "armor");	
		}else {
			return ringItem(loop, rarity);
		}		
	}
	/**
	 * Roll a stat, the item doesn't already have
	 * 
	 * @param statsSup : The list of stat the item already got
	 * 
	 * @return the list of stat with a new one
	 */
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
	
	/**
	 * This function create an item of type weapon, armor or shield
	 * 
	 * @param loop : The loop number
	 * @param rarity : the rarity of the item
	 * @param type : the type of the item 
	 * @return the created item
	 */
	static private Item classicItem(int loop, int rarity, String type) {
		double hp = 0;
		double strength = 0;
		double defense = 0;
		double counterAttack = 0;
		double regen = 0;
		double evade = 0;
		double vampirism = 0;
		ArrayList<String> statSup = new ArrayList<>();
		ArrayList<String> newStat = new ArrayList<>();
		String statSup2 = "";
		
		double percentage=0;
		double rarityMultiplier=1;
		
		switch(rarity) {				//Depending on the rarity of the item, the stats doesn't get the same multiplier
			case 0:
				percentage=1;
				break;
			case 1:
				percentage=0.9;
				rarityMultiplier=0.33;
				break;
			default:
				percentage=0.01*(Math.round(80 + (Math.random()*20))); 
				rarityMultiplier=0.5;
			}
			
		switch(type) {					// Initialize the main stat of the item, depending on its type
			case "weapon"-> {
				strength = percentage * ((loop*4)+Math.random()*(loop*6 - loop*4));
			}
			case "shield"->{
				defense =percentage* 4*loop;
				newStat.add("defense");
			}
			case "armor" ->{
				hp = percentage*((loop*80)+(Math.random()*(loop*100 - loop*80)));
			}
		}
		
		for(int i=0;(i<rarity&&i<2);i++) {		// Roll the secondary stats of the item
			String tmp = rollAStat(newStat);
			statSup.add(tmp);
			newStat.add(tmp);
		}
		
		if(statSup.contains("defense")) {		// Initialize all the stats depending on the different factors
			defense = rarityMultiplier*(loop * 1.5);
		}
		if(statSup.contains("counterAttack")) {
			counterAttack = 0.01*rarityMultiplier*(8 + (loop - 1) * 4);
		}
		if(statSup.contains("regen")) {
			regen = rarityMultiplier*(loop * 0.6);
		}
		if(statSup.contains("evade")) {
			evade = 0.01*rarityMultiplier*(8 + (loop - 1) * 2);
		}
		if(statSup.contains("vampirism")) {
			vampirism =0.01*rarityMultiplier*(8 + (loop - 1) * 1.5);
		}
		
		if(rarity==3) {					// Initialize the stat of the rarity 3 item
			statSup2 = rollAStat(newStat); 
			if(statSup2.equals("defense")) {
				defense = ((loop-2) * 1.5);
			}
			if(statSup2.equals("counterAttack")) {
				counterAttack = 0.01*(8 + (loop - 3) * 4);
			}
			if(statSup2.equals("regen")) {
				regen = ((loop-2) * 0.6);
			}
			if(statSup2.equals("evade")) {
				evade = 0.01*(8 + (loop - 3) * 2);
			}
			if(statSup2.equals("vampirism")) {
				vampirism = 0.01*(8 + (loop - 3) * 1.5);
			}
		}		
		return new Item(hp,strength,defense,counterAttack,regen,evade,vampirism,rarity,"pictures/Stuff/"+type+""+rarity+".png",type, loop); 
	}
	
	/**
	 * This function create a ring item
	 * 
	 * @param loop : The loop number
	 * @param rarity : the rarity of the item
	 * @return the created ring
	 */
	static private Item ringItem(int loop, int rarity) {
		double hp = 0;
		double strength = 0;
		double defense = 0;
		double counterAttack = 0;
		double regen = 0;
		double evade = 0;
		double vampirism = 0;
		String type= "ring";
		ArrayList<String> statSup = new ArrayList<>();
		ArrayList<String> newStat = new ArrayList<>();
		
		double stat = Math.random();
		if(stat>0.80) {
			defense = loop * 1.5;
			newStat.add("defense"); 
		}else if(stat>0.60) {
			counterAttack = 0.01*((8 + (loop - 1) * 4));
			newStat.add("counterAttack"); 
		}else if(stat>0.40) {
			regen = (loop * 0.6);
			newStat.add("regen"); 
		}else if(stat>0.20) {
			evade = 0.01*((8 + (loop - 1) * 2));
			newStat.add("evade"); 
		}else {
			vampirism =0.01*((8 + (loop - 1) * 1.5));
			newStat.add("vampirism");
		}
		
		for(int i=1;i<=rarity;i++) {
			String tmp = rollAStat(newStat);
			statSup.add(tmp);
			newStat.add(tmp);
		}
		
		if(statSup.contains("defense")) {
			defense = 0.5*(loop * 1.5);
		}
		if(statSup.contains("counterAttack")) {
			counterAttack = 0.01*0.5*(8 + (loop - 1) * 4);
		}
		if(statSup.contains("regen")) {
			regen = 0.5*(loop * 0.6);
		}
		if(statSup.contains("evade")) {
			evade = 0.01*0.5*(8 + (loop - 1) * 2);
		}
		if(statSup.contains("vampirism")) {
			vampirism =0.01*0.5*(8 + (loop - 1) * 1.5);
		}
		
		return new Item(hp,strength,defense,counterAttack,regen,evade,vampirism,rarity,"pictures/Stuff/"+type+""+rarity+".png",type, loop);
		
	}
	
	/**
	 * Round a value
	 * 
	 * @param value : the value we want to round
	 * @param places : the number of places after the dot
	 * @return the rounded value
	 */
	public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
	
}
