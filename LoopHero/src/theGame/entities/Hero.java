package theGame.entities;

public class Hero{

	private final Stats stats;
	
    public Hero(Stats stats){
        this.stats=stats;
    }

    /**
     * @param lostHP les hp que le joueur doit perdre
     */
    public void lossHP(int lostHP){
        stats.lossHP(lostHP);
    }

    /**
     * Regenere les HP du hero à la fin d'une boucle
     */
    public void regenHPloop(){
        stats.regenHPloop();
    }
    
    /**
     * Regenere les HP du héro à chaque jour
     * 
     * @param heal nombre de PV que doit se heal le hero
     */
    public void regenHPdaily(int heal) {
    	stats.regenHPdaily(heal);
    }

    /**
     * @return renvoie les dégat fait par le héro (WYP)
     */
    public int damage(){
        return (int) (4+(Math.random()*2));
    }
    
    /**
     * Modifie la valeurs des HP max du héro
     * 
     * @param modif pourcentage d'augmentation à appliquer aux HPmax
     */
    public void modifMaxHP(float modif) {
    	stats.modifMaxHP(modif);
    }

    /**
     * @return les hp du Hero
     */
    public long hp() {
    	return stats.getHp();
    }
    
    /**
     * @return les HPmax du héro
     */
    public long maxHp() {
    	return stats.getMaxHp();
    }
    
    /**
     * @return renvoie vrai si le héro est mort
     */
    public boolean isDead() {
    	return stats.getHp()<=0;
    }


}
