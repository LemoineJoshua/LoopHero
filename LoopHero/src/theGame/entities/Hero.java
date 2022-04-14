package theGame.entities;

public class Hero extends Entities{
	
    public Hero(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism){
    	super(hp,strength,defense,counterAttack,regen,evade,vampirism);
    }

    /**
     * Regenere les HP du hero à la fin d'une boucle
     */
    
    public void regenHPloop(){
    	stats.put("hp", stats.get("hp")*0.2);
    	if(stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    }
    
    /**
     * Regenere les HP du héro à chaque jour
     * 
     * @param heal nombre de PV que doit se heal le hero
     */
    public void regenHPdaily(int heal) {
    	stats.put("hp", stats.get("hp")+heal);
    	if(stats.get("hp")>stats.get("maxHp")) {
    		stats.put("hp", stats.get("maxHp"));
    	}
    }
    
	
	/**
	 * @return les hpMax du hero
	 */
	public long maxHp() {
		return stats.get("maxHp").longValue();
	}
	
	/**
	 * @param modifie les maxHp du hero
	 */
	public void modifMaxHP(float modif) {
		stats.put("maxHp", stats.get("maxHp")+modif);
    }
    
	/**
     * @return les degats du hero
     */
	@Override
    public int damage() {
    	int strength = stats.get("strength").intValue();
		return (int) ((strength*4)+(Math.random()*(strength*6 - strength*4)));
	}
    
    
}
