package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Wastelands extends AbstractRoad{
	
	public Wastelands() {
		super("pictures/chemin.png");
	}
    
   @Override
   public void dailyEffect(Board board) {
	   ArrayList<String> drop = new ArrayList<>();
	   drop.add("Shapeless mass");
	   drop.add("Craft fragment");
	   Monster slime = new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/slimeS.png",(float)0.05, (float) 0.65,drop);
	   if (slime.doSpawn()){
           addMob(slime);
	   }
   }
   
}
