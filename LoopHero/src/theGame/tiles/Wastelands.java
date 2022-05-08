package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Wastelands extends AbstractRoad{
	
	/**
	 * WasteLands constructor
	 * A empty path cell, which can spawn slime
	 */
	public Wastelands() {
		super("pictures/Tiles/chemin.png",new ArrayList<Monster>());
	}
	
	/**
	 * WasteLands constructor
	 * A empty path cell, which can spawn slime
	 */
	public Wastelands(ArrayList<Monster> monsterList) {
		super("pictures/Tiles/chemin.png",monsterList);
	}
    
   /**
    * Apply the daily effect of the tile each day
    * Got 5% chance to spawn a slime each day
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
	
	
	/**
	 * Check if we can the oblivion card on this tile
	 * 
	 * @return true if we can use it, false else.
	 */
	@Override
	public boolean isOblivionable() {
		return (super.aliveMonster().size()>0);
	}
   
}
