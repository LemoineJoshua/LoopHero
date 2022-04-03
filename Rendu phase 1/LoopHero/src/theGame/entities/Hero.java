package theGame.entities;

public class Hero extends Entites {

    public Hero(long maxHp, double strength, double defense, double counterAttack, double regen,double esquive,double vampirism,String picture){
        super(maxHp,strength,defense,counterAttack,regen,esquive,vampirism,picture);
    }

    /**
     * @param lostHP les hp que le joueur doit perdre
     */
    public void lossHP(int lostHP){
        hp-= lostHP;
    }

    /**
     * Regenere les HP du hero à la fin d'une boucle
     */
    public void regenHPloop(){
        hp+=0.2*maxHp;
        if (hp>maxHp){
            hp = maxHp;
        }
    }
    
    /**
     * Regenere les HP du héro à chaque jour
     * 
     * @param heal nombre de PV que doit se heal le hero
     */
    public void regenHPdaily(int heal) {
    	hp+=heal;
        if (hp>maxHp){
            hp = maxHp;
        }
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
    	maxHp*=1.01;
    }

    /**
     * @return les hp du Hero
     */
    public long hp() {
    	return hp;
    }
    
    /**
     * @return les HPmax du héro
     */
    public long maxHp() {
    	return maxHp;
    }
    
    /**
     * @return renvoie vrai si le héro est mort
     */
    public boolean isDead() {
    	return hp<=0;
    }


}
