package theGame.entities;

import theGame.inventories.HeroStuff;
import theGame.inventories.Item;

public class Hero extends AbstractEntities{
	
	private final HeroStuff stuff;
	
	/**
	 * Hero Constructor
	 * 
	 * @param hp : The hero max hp and current hp at his creation
	 * @param strength : The hero strength
	 * @param defense : The hero defense
	 * @param counterAttack : The hero chance to counter attack
	 * @param regen : The hero regen
	 * @param evade : The hero chance to evade 
	 * @param vampirism : The hero vampirism percentage
	 */
    public Hero(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism){
    	super(hp,strength,defense,counterAttack,regen,evade,vampirism);
    	this.stuff= new HeroStuff();
    }

    /**
     * Regen the hp hero at the end of a loop
     */    
    public void regenHPloop(){
    	stats.put("hp", (double) Math.round(stats.get("hp")+stats.get("hp")*0.2));
    	if(stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    }
    
    /**
     * Regen the hp of the hero each day
     * 
     * @param heal : Number of hp, the hero go to regen
     */
    public void regenHPdaily(int heal) {
    	stats.put("hp", (double) Math.round(stats.get("hp")+heal));
    	if(stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    }
	
	/**
	 * Modify the maxHp of the hero
	 * 
	 * @param modif : the maxHp, we got to add to the hero
	 */
	public void modifMaxHP(float modif) {
		stats.put("maxHp",(double) Math.round( stats.get("maxHp")+modif));
    }
    
	/**
	 * Damage Accessor
	 * 
     * @return the damage of the hero
     */
	@Override
    public int damage() {
    	int strength = stats.get("strength").intValue();
		return (int) ((strength*4)+(Math.random()*(strength*6 - strength*4)));
	}
    
	
	/**
	 * Equip the new item in the hero stuff
	 * 
	 * @param item : The item, we want to equip on the hero
	 */
	public void equip(Item item) {
		stuff.equip(stats,item);
	}
	
	/**
	 * Stuff accessor
	 * 
	 * @return the stuff of the hero
	 */
	public HeroStuff stuff() {
		return stuff;
	}
    
}
