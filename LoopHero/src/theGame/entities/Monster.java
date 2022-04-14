package theGame.entities;

import java.util.ArrayList;

public class Monster extends Entities{
	
    private final float chanceSpawn;
    private final float chanceCard;
    private final ArrayList<String> drop;
    private final String picture;

    public Monster(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,float chanceSpawn,float chanceCard,ArrayList<String> drop,String picture){
        super(hp,strength,defense,counterAttack,regen,evade,vampirism);
        this.chanceSpawn=chanceSpawn;
        this.chanceCard=chanceCard;
        this.drop=drop;
        this.picture=picture;
    }

    /**
     * @return true si le mob spawn, false sinon
     */
    public boolean doSpawn(){
    	return chanceSpawn > Math.random();
    }
    
    /**
     * @return true si le mob drop une carte, false sinon
     */
    public boolean dropCard() {
    	return chanceCard > Math.random();
    }
    
    /**
     * @return les drops du monstre
     */
    public ArrayList<String> drop(){
    	return drop;
    }
    
    /**
	 * @return l'image du mob
	 */
	public String picture() {
		return picture;
	}

	/**
	 * @param loopNumber
	 * 
	 * actualise les stats du montre en fonction du tour de boucle
	 */
	public void fightStats(int loopNumber){
		stats.put("strength", stats.get("strength")* loopNumber * 0.95 * (1+(loopNumber-1)*0.02));
		stats.put("maxHp", stats.get("maxHp")* loopNumber * 0.95 * (1+(loopNumber-1)*0.02));
		stats.put("hp", stats.get("maxHp"));
	}
    
	
	
}
