package theGame.entities;

public class Hero extends Entites {

    public Hero(long maxHp, double force, double defence, double counterAttack, double regen,double esquive,double vampirisme,String image){
        super(maxHp,force,defence,counterAttack,regen,esquive,vampirisme,image);
    }

    public boolean perteHP(int hpPerdu){
        //fonctions qui actualise les HP et renvoie vrai si le joueur est mort
        hp-= hpPerdu;
        if(hp<0){
            return true;
        }
        return false;
    }

    public void regenHPturn(){
        //fonction qui régénères les HP du joueur � chaque tour de boucle
        hp*=1.2;
        if (hp>maxHp){
            hp = maxHp;
        }
    }
    
    public void regenHPdaily(int heal) {
    	hp+=heal;
        if (hp>maxHp){
            hp = maxHp;
        }
    }

    public int degat(){
        //fonction qui inflige entre 
        return (int) (4+(Math.random()*2));
    }
    
    public void modifMaxHP(float modif) {
    	maxHp*=1.01;
    }



}
