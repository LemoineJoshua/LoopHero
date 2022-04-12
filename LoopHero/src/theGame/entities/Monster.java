package theGame.entities;

import java.util.ArrayList;

public class Monster {
	
    private final float chanceSpawn;
    private final float chanceCard;
    private final ArrayList<String> drop;
    private final Stats statistique;
    private final String picture;

    public Monster(Stats statistique,float chanceSpawn,float chanceCard,ArrayList<String> drop,String picture){
        this.statistique=statistique;
        this.chanceSpawn=chanceSpawn;
        this.chanceCard=chanceCard;
        this.drop=drop;
        this.picture=picture;
    }

    /**
     * actualise les stats du monstre pour un combat
     * 
     * @param loopNumber numéro du tour de boucle
     *
    public void statAuCombat(int loopNumber){
        strength = strength * loopNumber * 0.95 * (1+(loopNumber-1)*0.02);
		maxHp = (int) Math.round(maxHp * loopNumber * 0.95 * (1+(loopNumber-1)*0.02));
		hp = maxHp;
	}
	*/

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

    
}
