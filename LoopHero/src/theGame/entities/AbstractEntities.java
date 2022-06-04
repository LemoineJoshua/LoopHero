package theGame.entities;

import java.io.Serializable;
import java.util.HashMap;

public abstract class AbstractEntities implements Serializable{
	
	protected final HashMap<String,Double> stats;
	

	/**
	 * Abstract Entities Constructor
	 * 
	 * @param hp : The entity max hp and current hp at his creation
	 * @param strength : The entity strength
	 * @param defense : The entity defense
	 * @param counterAttack : The entity chance to counter attack
	 * @param regen : The entity regen
	 * @param evade : The entity chance to evade 
	 * @param vampirism : The entity vampirism percentage
	 */
	public AbstractEntities(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism) {
		this.stats=new HashMap<String,Double>() {{
			put("maxHp",hp);
			put("hp",hp);
			put("strength",strength);
			put("defense",defense);
			put("counterAttack",counterAttack);
			put("regen",regen);
			put("evade",evade);
			put("vampirism",vampirism);
		}};
	}
	
	
	/**
	 * Damage accessor
	 * 
	 * @return the entity damage
	 */
	public int damage() {
		return stats.get("strength").intValue();
	}
    
    /**
     * Get the hp the entity lost, calculated with the ennemy damage and entity defense
     * 
     * @param lostHP : the amount of hp the entity has lost
     */
    public int lossHp(int lostHP){
    	int damage=(int) ((lostHP)-stats.get("defense")) ;
    	if(damage<0) {
    		damage=0;
    	}
    	stats.put("hp",stats.get("hp")-damage);
    	return damage;
    }
    
    /*
     * Regen the entity with his vampirism
     * 
     * @param damage : the damage, the entity has made
     *
     */
    public int vampirismRegen(int damage) {
    	stats.put("hp",(double) Math.round(stats.get("hp")+damage*stats.get("vampirism")));
    	if (stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    	return (int) (damage*stats.get("vampirism"));
    }
    
    /**
     * Check if the entity has evade
     * 
     * @return true if the entity evade, else false
     */
    public boolean doEvade() {
    	return stats.get("evade")>Math.random();
    }
    
    /**
     * Check if the entity counter attack
     * 
     * @return true if the entity counter attack, else false
     */
    public boolean doCounter() {
    	return stats.get("counterAttack")>Math.random();
    }
    
    
    /**
     * Get the percentage of hp, the entity has
     * 
     * @return the percentage of hp of the entity
     */
    public double hpPercentage() {
    	return stats.get("hp")/stats.get("maxHp");
    }
    
    /**
     * Check if the entity is alive
     * 
     * @return true true if the entity is alive, else false
     */
    public boolean isDead() {
    	return stats.get("hp")<=0;
    }
    
    /**
     * Regen the hp, using the regen stat of the entity
     */
    public void regenTurn() {
    	stats.put("hp", (double) Math.round(stats.get("hp") + stats.get("regen")));
    	if(stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    }
    
    /**
     * MaxHp accessor
     * 
     * @return the maxHp of the entity
     */
    public long maxHp() {
    	return stats.get("maxHp").longValue();
    }
    
    /**
     * Hp accessor
     * 
     * @return the hp of the entity
     */
    public long hp() {
		return stats.get("hp").longValue();
	}
    
    /**
     * Strength accessor
     * 
     * @return the strength of the entity
     */
    public double strength() {
    	return stats.get("strength");
    }
    
    /**
     * Defense accessor
     * 
     * @return the defense of the entity
     */
    public double defense() {
    	return stats.get("defense");
    }
    
    /**
     * counterAtack Chance accessor
     * 
     * @return the percentage of chance the enity had to counter attack
     */
    public double counterAttack() {
    	return stats.get("counterAttack");
    }
    
    /**
     * Regen accessor
     * 
     * @return the regen of the entity
     */
    public double regen() {
    	return stats.get("regen");
    }
    
    /**
     * Evade Chance accessor
     * 
     * @return the percentage of chance the enity had to evade
     */
    public double evade() {
    	return stats.get("evade");
    }
    
    /**
     * Vampirism regen accessor
     * 
     * @return the vampirsm regen percentage of the entity
     */
    public double vampirism() {
    	return stats.get("vampirism");
    }
}
