package theGame.entities;

public abstract class Entites {
	protected long maxHp;
	protected long hp; 
	protected double strength;
	protected final double defense;
	protected final double counterAttack;
	protected final double regen;
	protected final double esquive;
	protected final double vampirism;
	protected final String picture;
	
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
	public Entites(long maxHp, double strength, double defense, double counterAttack, double regen,double esquive,double vampirism,String picture) {
		this.maxHp=maxHp;
		this.hp=maxHp;
		this.strength=strength;
		this.defense=defense;
		this.counterAttack=counterAttack;
		this.regen=regen;
		this.esquive=esquive;
		this.vampirism=vampirism;
		this.picture=picture;
	}

	/**
	 * @return l'image du mob
	 */
	public String picture() {
		return picture;
	}
	
	
	/**
	 * @return les d�gat de l'entit�e
	 */
	public int damage(){
		return (int) strength;
	}

	
	
}
