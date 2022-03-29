package theGame.entities;

import java.util.ArrayList;

public class Monster extends Entites {
	
    private final float chanceSpawn;
    private final float chanceCard;
    private final ArrayList<String> drop;

    public Monster(long maxHp, double strength, double defense, double counterAttack, double regen,double esquive,double vampirism,String picture,float chanceSpawn,float chanceCard,ArrayList<String> drop){
        super(maxHp,strength,defense,counterAttack,regen,esquive,vampirism,picture);
        this.chanceSpawn=chanceSpawn;
        this.chanceCard=chanceCard;
        this.drop=drop;
    }

    public void statAuCombat(int loopNumber){
        //actualise les stats du monstre pour un combat
        strength = strength * loopNumber * 0.95 * (1+(loopNumber-1)*0.02);
		maxHp = (int) Math.round(maxHp * loopNumber * 0.95 * (1+(loopNumber-1)*0.02));
		hp = maxHp;
	}

    public boolean doSpawn(){
        //renvoie true si le monstre apparait sur la case
    	return chanceSpawn > Math.random();
    }
    
    public boolean dropCard() {
    	return chanceCard > Math.random();
    }
    
    public ArrayList<String> drop(){
    	return drop;
    }

    
}
