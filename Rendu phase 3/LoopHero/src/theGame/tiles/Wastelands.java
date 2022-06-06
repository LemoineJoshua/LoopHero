package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.Slime;

public class Wastelands extends AbstractRoad implements Serializable{
	
	private static final long serialVersionUID = -2214596648468838296L;


	/**
	 * WasteLands constructor
	 * A empty path cell, which can spawn slime
	 * 
	 * @param position : The coords of the tile
	 */
	public Wastelands(Coord position) {
		super("pictures/Tiles/chemin.png",new ArrayList<AbstractMonster>(),position);
	}
	
	/**
	 * WasteLands constructor
	 * A empty path cell, which can spawn slime
	 * 
	 * @param monsterList : the monsters living on the tile
	 * @param position : The coords of the tile
	 */
	public Wastelands(ArrayList<AbstractMonster> monsterList,Coord position) {
		super("pictures/Tiles/chemin.png",monsterList,position);
	}
    
   /**
    * Apply the daily effect of the tile each day
    * Got 5% chance to spawn a slime each day
    * 
    * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
    */
	@Override
    public void dailyEffect(Board board) {
		Objects.requireNonNull(board);
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
