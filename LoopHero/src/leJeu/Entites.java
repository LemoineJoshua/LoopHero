package leJeu;

public abstract class Entites implements InterfaceEntites {
	protected long maxHp;
	protected long hp;
	protected double force;
	protected double defence;
	protected double counterAttack;
	protected double regen;
	protected double esquive;
	protected double vampirisme;
	protected final String image;
	
	public Entites(long maxHp, double force, double defence, double counterAttack, double regen,double esquive,double vampirisme,String image) {
		this.maxHp=maxHp;
		this.hp=maxHp;
		this.force=force;
		this.defence=defence;
		this.counterAttack=counterAttack;
		this.regen=regen;
		this.esquive=esquive;
		this.vampirisme=vampirisme;
		this.image=image;
	}

	@Override
	public int degat(){
		return (int) force;
	}

	
	
}
