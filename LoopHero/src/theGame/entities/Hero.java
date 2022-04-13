package theGame.entities;

public class Hero extends Entities{
	
    public Hero(StatsEntites stats){
        super(stats);
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
     * @return renvoie les dégat fait par le héro
     */
    @Override
    public int damage(){
        return stats.damageHero();
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
    
}
