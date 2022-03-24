package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Wastelands extends AbstractCase{
	private final ArrayList<Monster> aliveMonster;   //les monstres sur la case
	
	public Wastelands() {
		super("Road","pictures/chemin.png");
		this.aliveMonster = new ArrayList<Monster>();
	}
	
	 public void addMob(Monster newMonster){
	        //ajouter un monstre sur la case
	        if(aliveMonster.size()<1){
	        	aliveMonster.add(newMonster);
	        }
	    }
	 
	 public ArrayList<Monster> aliveMonster(){
		 return aliveMonster;
	 }

	@Override 
    public void clearMob(){
        //supprimer tout les monstres de la case (combat gagné/oblivion)
    	aliveMonster.clear();; 
    }
    
    @Override
    public boolean isCombat(){
        //renvoie vrai si un combat doit etre lance
	        return (!aliveMonster.isEmpty());
	    }
    
    
   @Override
   public void dailyEffect(Board board) {
	   Monster slime = new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/slimeS.png",(float)0.05);
	   if (slime.doSpawn()){
           addMob(slime);
	   }
   }
   
}
