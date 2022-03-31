package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Wastelands extends AbstractRoad{
	
	/**
	 * Une case de chemin 'vide', peut faire apparaitre des slime
	 */
	public Wastelands() {
		super("pictures/chemin.png",new ArrayList<Monster>());
	}
    
   /**
    *5% de chance qu'un slime spawn tout les jours
    */
	@Override
   public void dailyEffect(Board board) {
	   ArrayList<String> drop = new ArrayList<>();
	   drop.add("Shapeless Mass");
	   drop.add("Craft Fragment");
	   Monster slime = new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/slimeS.png",(float)0.05, (float) 0.65,drop);
	   if (slime.doSpawn()){
           addMob(slime);
	   }
   }
   
}
