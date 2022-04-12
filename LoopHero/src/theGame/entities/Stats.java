package theGame.entities;

/**
 * @author Jlwis
 *
 */
/**
 * @author Jlwis
 *
 */
/**
 * @author Jlwis
 *
 */
public class Stats {
	private long maxHp;
	private long hp; 
	private double strength;
	private final double defense;
	private final double counterAttack;
	private final double regen;
	private final double evade;
	private final double vampirism;
	
	/**
	 * @param maxHp HP max de l'entitée
	 * @param strength force de l'entitée
	 * @param defense défence de l'entitée
	 * @param counterAttack la chance de contre-attaque
	 * @param regen la regen de l'entite
	 * @param esquive la chance d'esquive de l'entitée
	 * @param vampirism le pourcentage de vampirisme
	 * @param picture l'image de l'entitée
	 */
	public Stats(long maxHp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism) {
		this.maxHp=maxHp;
		this.hp=maxHp;
		this.strength=strength;
		this.defense=defense;
		this.counterAttack=counterAttack;
		this.regen=regen;
		this.evade=evade;
		this.vampirism=vampirism;
	}
	
	//fonctions pour le hero
	
    /**
     * Regenere les HP du hero à la fin d'une boucle
     */
    public void regenHPloop(){
        hp+=0.2*maxHp;
        if (hp>maxHp){
            hp = maxHp;
        }
    }
    
    /**
     * Regenere les HP du héro à chaque jour
     * 
     * @param heal nombre de PV que doit se heal le hero
     */
    public void regenHPdaily(int heal) {
    	hp+=heal;
        if (hp>maxHp){
            hp = maxHp;
        }
    }
    
    /**
     * @return les hps du héro
     */
    public long getHp() {
		return hp;
	}
	
	/**
	 * @return les hpMax du hero
	 */
	public long getMaxHp() {
		return maxHp;
	}
	
	/**
	 * @param modifie les maxHp du hero
	 */
	public void modifMaxHP(float modif) {
    	maxHp+=modif;
    }
    
    
    //fonctions générales
    
    public int damage() {
		return (int) ((strength*4)+(Math.random()*(strength*6 - strength*4)));
	}
    
    /**
     * @param lostHP les hp que l'entite doit perdre
     */
    public void lossHP(int lostHP){
        hp-= (lostHP)-defense;
    }
    
    /**
     * @param damage les dégats que l'entite inflige
     * 
     * Regenere l'entite en fonction de son vampirisme
     */
    public void vampirismRegen(int damage) {
    	hp+= damage*vampirism;
    }
    
    /**
     * @return true si l'entite esquive, false sinon
     */
    public boolean doEvade() {
    	return evade>Math.random();
    }
    
    /**
     * @return true si l'entite contre attaque, false sinon
     */
    public boolean doCounter() {
    	return counterAttack>Math.random();
    }
    
    
    //fonctions pour les mobs
    
    
    /**
     * actualise les stats du monstre pour un combat
     * 
     * @param loopNumber numéro du tour de boucle
     */
    public void statAuCombat(int loopNumber){
        strength = strength * loopNumber * 0.95 * (1+(loopNumber-1)*0.02);
		maxHp = (int) Math.round(maxHp * loopNumber * 0.95 * (1+(loopNumber-1)*0.02));
		hp = maxHp;
	}
	
    
    
}
