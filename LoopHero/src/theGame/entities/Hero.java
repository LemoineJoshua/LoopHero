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
     * Regenere les HP du hero � la fin d'une boucle
     */
    public void regenHPloop(){
        stats.regenHPloop();
    }
    
    /**
     * Regenere les HP du h�ro � chaque jour
     * 
     * @param heal nombre de PV que doit se heal le hero
     */
    public void regenHPdaily(int heal) {
    	stats.regenHPdaily(heal);
    }

    /**
     * @return renvoie les d�gat fait par le h�ro (WYP)
     */
    public int damage(){
        return (int) (4+(Math.random()*2));
    }
    
    /**
     * Modifie la valeurs des HP max du h�ro
     * 
     * @param modif pourcentage d'augmentation � appliquer aux HPmax
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
     * @return les HPmax du h�ro
     */
    public long maxHp() {
    	return stats.getMaxHp();
    }
    
    /**
     * @return renvoie vrai si le h�ro est mort
     */
    public boolean isDead() {
    	return stats.getHp()<=0;
    }


}
