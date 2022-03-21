package theGame.boardGame;

import java.awt.Graphics2D;
import java.util.ArrayList;

import theGame.entities.Monster;

public class Cases {
    private final ArrayList<Monster> monstresPresent;   //les monstres sur la case
    private final ArrayList<Monster> monstresSpawnable; //les monstres qui peuvent apparaitre
    private final ArrayList<Coord> casesImpactes;       //les cases impactés par cette case (comme avec le phare apr exemple)
    private String apparence; //je crois que c'est un chemin absolu qui faut

    public Cases(){
        this.monstresPresent = new ArrayList<Monster>();
        this.monstresSpawnable = new ArrayList<Monster>();
        this.casesImpactes = new ArrayList<Coord>();
        this.apparence = null;
    }

    public void addMob(Monster newMonster){
        //ajouter un monstre sur la case
        if(monstresPresent.size()<1){
            monstresPresent.add(newMonster);
        }
    }

    public void clearMob(){
        //supprimer tout les monstres de la case (combat gagné/oblivion)
        monstresPresent.clear();; 
    }

    public void addSpawnable(Monster newSpawnable){
        //ajouter un monstre pouvant apparaitre sur la case
        monstresSpawnable.add(newSpawnable); //pas d'anti doublon, on veut que les chances de spawn évolue en fonction des batiments autours
    }
    
    public void removeSpawnable(Monster spawnable){
        //enlever un monstre pouvant apparaitre sur la case (oblivion)
        monstresSpawnable.remove(spawnable);
    }

    /*
    public void clearCase(){ //Fonction pour le Oblivion
        for (Cases case : casesImpactes){
            
        }
        
        clearMob();
    }
    */
    

    public void SpawnTour(){
        //fait spawner des mobs sur la case pour le tour actuel, en verifiant la proba de spawn
        for (Monster spawningMonstre : monstresSpawnable){
            if (spawningMonstre.doSpawn()){
                addMob(spawningMonstre);
            }
        }
    }

    public boolean isCombat(){
        //renvoie vrai si un combat doit etre lance
        return (!monstresPresent.isEmpty());
    }
    
    public void AfficheCase(Graphics2D graphics) {
    	if(apparence!=null) {
    		
    	}
    }
    
    public ArrayList<Monster> monstresPresent(){
    	return monstresPresent;
    }
    
    
}
