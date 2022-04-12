package theGame.entities;

public class Stats {
	private long maxHp;
	private long hp; 
	private double strength;
	private final double defense;
	private final double counterAttack;
	private final double regen;
	private final double esquive;
	private final double vampirism;
	
	/**
	 * @param maxHp HP max de l'entit�e
	 * @param strength force de l'entit�e
	 * @param defense d�fence de l'entit�e
	 * @param counterAttack la chance de contre-attaque
	 * @param regen la regen de l'entite
	 * @param esquive la chance d'esquive de l'entit�e
	 * @param vampirism le pourcentage de vampirisme
	 * @param picture l'image de l'entit�e
	 */
	public Stats(long maxHp, double strength, double defense, double counterAttack, double regen,double esquive,double vampirism) {
		this.maxHp=maxHp;
		this.hp=maxHp;
		this.strength=strength;
		this.defense=defense;
		this.counterAttack=counterAttack;
		this.regen=regen;
		this.esquive=esquive;
		this.vampirism=vampirism;
	}

	public long getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(long maxHp) {
		this.maxHp = maxHp;
	}

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public double getDefense() {
		return defense;
	}

	public double getCounterAttack() {
		return counterAttack;
	}

	public double getRegen() {
		return regen;
	}

	public double getEsquive() {
		return esquive;
	}

	public double getVampirism() {
		return vampirism;
	}
	
	public void modifMaxHP(float modif) {
    	maxHp*=1.01;
    }
	
	/**
     * @param lostHP les hp que le joueur doit perdre
     */
    public void lossHP(int lostHP){
        hp-= lostHP;
    }

    /**
     * Regenere les HP du hero � la fin d'une boucle
     */
    public void regenHPloop(){
        hp+=0.2*maxHp;
        if (hp>maxHp){
            hp = maxHp;
        }
    }
    
    /**
     * Regenere les HP du h�ro � chaque jour
     * 
     * @param heal nombre de PV que doit se heal le hero
     */
    public void regenHPdaily(int heal) {
    	hp+=heal;
        if (hp>maxHp){
            hp = maxHp;
        }
    }

	
	
	

	
	
}
