package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Wastelands extends AbstractRoad{
	
	/**
	 * Une case de chemin 'vide', peut faire apparaitre des slime
	 */
	public Wastelands() {
		super("pictures/Tiles/chemin.png",new ArrayList<Monster>());
	}
	
	public Wastelands(ArrayList<Monster> monsterList) {
		super("pictures/Tiles/chemin.png",monsterList);
	}
    
   /**
    *5% de chance qu'un slime spawn tout les jours
    */
	@Override
   public void dailyEffect(Board board) {
	   ArrayList<String> drop = new ArrayList<>();
	   drop.add("Shapeless Mass");
	   drop.add("Craft Fragment");
	   Monster slime = new Monster(13,3.3,0.0,0.0,0.0,0.0,0.0,(float)0.05, (float) 0.65,drop,"pictures/Entities/slimeS.png", "pictures/Entities/slimeFight.png");
	   if (slime.doSpawn()){
           addMob(slime);
	   }
   }
   
}
