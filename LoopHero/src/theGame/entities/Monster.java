package theGame.entities;

import java.util.ArrayList;

public class Monster extends AbstractEntities{
	
    private final float chanceSpawn;
    private final float chanceCard;
    private final ArrayList<String> drop;
    private final String picture;
    private final String pictureFight;

    public Monster(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,float chanceSpawn,float chanceCard,ArrayList<String> drop,String picture, String pictureFight){
        super(hp,strength,defense,counterAttack,regen,evade,vampirism);
        this.chanceSpawn=chanceSpawn;
        this.chanceCard=chanceCard;
        this.drop=drop;
        this.picture=picture;
        this.pictureFight=pictureFight;
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
	
	public String pictureFight() {
		return pictureFight;
	}

	/**
	 * @param loopNumber
	 * 
	 * actualise les stats du montre en fonction du tour de boucle
	 */
	public void fightStats(int loopNumber){
		stats.put("strength",(double) Math.round(stats.get("strength")* loopNumber * 0.95 * (1+(loopNumber-1)*0.02)));
		stats.put("maxHp", (double) Math.round(stats.get("maxHp")* loopNumber * 0.95 * (1+(loopNumber-1)*0.02)));
		stats.put("hp", stats.get("maxHp"));
	}
    
	
	
}
