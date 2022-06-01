package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.Slime;

public class Wastelands extends AbstractRoad implements Serializable{
	
	/**
	 * WasteLands constructor
	 * A empty path cell, which can spawn slime
	 */
	public Wastelands(Coord position) {
		super("pictures/Tiles/chemin.png",new ArrayList<AbstractMonster>(),position);
	}
	
	/**
	 * WasteLands constructor
	 * A empty path cell, which can spawn slime
	 */
	public Wastelands(ArrayList<AbstractMonster> monsterList,Coord position) {
		super("pictures/Tiles/chemin.png",monsterList,position);
	}
    
   /**
    * Apply the daily effect of the tile each day
    * Got 5% chance to spawn a slime each day
    */
	@Override
   public void dailyEffect(Board board) {
	   Slime slime = new Slime();
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
