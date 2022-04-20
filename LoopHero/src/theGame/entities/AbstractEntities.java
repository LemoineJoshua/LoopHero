package theGame.entities;

import java.util.HashMap;

public abstract class AbstractEntities {
	
	protected final HashMap<String,Double> stats;
	
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
	
	
	public int damage() {
		return stats.get("strength").intValue();
	}
    
    /**
     * @param lostHP les hp que l'entite doit perdre
     */
    public int lossHp(int lostHP){
    	int damage=(int) ((lostHP)-stats.get("defense")) ;
    	if(damage<0) {
    		damage=0;
    	}
    	stats.put("hp",stats.get("hp")-damage);
    	return damage;
    }
    
    /**
     * @param damage les dégats que l'entite inflige
     * 
     * Regenere l'entite en fonction de son vampirisme
     */
    public int vampirismRegen(int damage) {
    	stats.put("hp",(double) Math.round(stats.get("hp")+damage*stats.get("vampirism")));
    	if (stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    	return (int) (damage*stats.get("vampirism"));
    }
    
    /**
     * @return true si l'entite esquive, false sinon
     */
    public boolean doEvade() {
    	return stats.get("evade")>Math.random();
    }
    
    /**
     * @return true si l'entite contre attaque, false sinon
     */
    public boolean doCounter() {
    	return stats.get("counterAttack")>Math.random();
    }
    
    
    /**
     * @return le pourcentage de pv restant
     */
    public double hpPercentage() {
    	return stats.get("hp")/stats.get("maxHp");
    }
    
    /**
     * @return true si l'entite est vivante, false sinon
     */
    public boolean isDead() {
    	return stats.get("hp")<=0;
    }
    
    /**
     * regenere les pv en fonction de la stat regen
     */
    public void regenTurn() {
    	stats.put("hp", (double) Math.round(stats.get("hp") + stats.get("hp")*(stats.get("regen"))));
    	if(stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    }
    public long maxHp() {
    	return stats.get("maxHp").longValue();
    }
    
    public long hp() {
		return stats.get("hp").longValue();
	}
    
    public double strength() {
    	return stats.get("strength");
    }
    
    public double defense() {
    	return stats.get("defense");
    }
    
    public double counterAttack() {
    	return stats.get("counterAttack");
    }
    
    public double regen() {
    	return stats.get("regen");
    }
    
    public double evade() {
    	return stats.get("evade");
    }
    
    public double vampirism() {
    	return stats.get("vampirism");
    }
}
