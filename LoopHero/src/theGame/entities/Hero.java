package theGame.entities;

public class Hero extends Entities{
	
    public Hero(StatsEntites stats){
        super(stats);
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
     * @return renvoie les d�gat fait par le h�ro
     */
    @Override
    public int damage(){
        return stats.damageHero();
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
    
}
