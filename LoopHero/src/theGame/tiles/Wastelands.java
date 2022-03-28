package theGame.tiles;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Wastelands extends AbstractRoad{
	
	public Wastelands() {
		super("pictures/chemin.png");
	}
    
   @Override
   public void dailyEffect(Board board) {
	   Monster slime = new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/slimeS.png",(float)0.05);
	   if (slime.doSpawn()){
           addMob(slime);
	   }
   }
   
}
