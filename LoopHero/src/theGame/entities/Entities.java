package theGame.entities;

public abstract class Entities {
	
	protected final StatsEntites stats;
	
	public Entities(StatsEntites stats) {
		this.stats=stats;
	}
	
	/**
	 * @return true si le mob est vivant, false sinon
	 */
	public boolean isDead() {
		return !stats.isAlive();
	}
	
	/**
	 * @return les degats que fait le mob
	 */
	public int damage() {
		return stats.damageMob();
	}
	
	/**
	 * @param damage
	 * 
	 * fait baisser les HP du mob
	 */
	public void lossHp(int damage) {
		stats.lossHP(damage);
	}
	
	/**
     * regenere les pv mob en fonction de sa stat regen
     */
    public void regenTurn() {
    	stats.regenTurn();
    }
    
    /**
     * @return true si le mob esquive, false sinon
     */
    public boolean doEvade() {
    	return stats.doEvade();
    }
    
    /**
     * @return true si le mob contre, false sinon
     */
    public boolean doCounter() {
    	return stats.doCounter();
    }
    
    /**
     * @param damage
     * 
     * applique les effet de vampirisme
     */
    public void vampirismRegen(int damage) {
    	stats.vampirismRegen(damage);
    }
}
