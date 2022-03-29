package theGame.entities;

public abstract class Entites implements InterfaceEntites {
	public long maxHp; /////////////
	public long hp; ////////////////
	protected double strength;
	protected double defense;
	protected double counterAttack;
	protected double regen;
	protected double esquive;
	protected double vampirism;
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
