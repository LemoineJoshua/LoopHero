package theGame.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Hero extends Entites {

    public Hero(long maxHp, double strength, double defense, double counterAttack, double regen,double esquive,double vampirism,String picture){
        super(maxHp,strength,defense,counterAttack,regen,esquive,vampirism,picture);
    }

    public boolean lossHP(int lostHP){
        //fonctions qui actualise les HP et renvoie vrai si le joueur est mort
        hp-= lostHP;
        if(hp<0){
            return true;
        }
        return false;
    }

    public void regenHPloop(){
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

    public int damage(){
        //fonction qui inflige entre 
        return (int) (4+(Math.random()*2));
    }
    
    public void modifMaxHP(float modif) {
    	maxHp*=1.01;
    }

    public long hp() {
    	return hp;
    }
    
    public long maxHp() {
    	return maxHp;
    }


}
