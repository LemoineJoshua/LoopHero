package leJeu.entities;

public class Monstre extends Entites {
	
    private final float chanceSpawn;

    public Monstre(long maxHp, double force, double defence, double counterAttack, double regen,double esquive,double vampirisme,String image,float chanceSpawn){
        super(maxHp,force,defence,counterAttack,regen,esquive,vampirisme,image);
        this.chanceSpawn=chanceSpawn;
    }

    public void statAuCombat(int tourBoucle){
        //actualise les stats du monstre pour un combat
        force = force * tourBoucle * 0.95 * (1+(tourBoucle-1)*0.02);
		maxHp = (int) Math.round(maxHp * tourBoucle * 0.95 * (1+(tourBoucle-1)*0.02));
		hp = maxHp;
	}

    public boolean doSpawn(){
        //renvoie true si le monstre apparait sur la case
    	return chanceSpawn > Math.random();
    }

    
}
