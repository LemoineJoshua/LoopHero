package theGame.entities;

public abstract class Entites implements InterfaceEntites {
	protected long maxHp;
	protected long hp; 
	protected double strength;
	protected final double defense;
	protected final double counterAttack;
	protected final double regen;
	protected final double esquive;
	protected final double vampirism;
	protected final String picture;
	
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

	public String picture() {
		return picture;
	}
	
	@Override
	public int damage(){
		return (int) strength;
	}

	
	
}
