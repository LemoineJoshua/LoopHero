package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class CampFire extends AbstractRoad{

	/**
	 * The CampFire constructor
	 * The Campfire heal the player at each loop beginning
	 */
	public CampFire() {
		super("pictures/Tiles/campFire.png",new ArrayList<Monster>());
	}
	
	/**
	 * Check if the cell is Empty, which mean we could place a card on it
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	//@Override
	/**
	 * The loop effect of the Campfire
	 * Heal the Hero at each beginning of a loop
	 */
	public void loopEffect(Board board){
		board.hero().regenHPloop();		
	}
	
	/**
	 * Check if we can the oblivion card on this tile
	 * 
	 * @return true if we can use it, false else.
	 */
	@Override
	public boolean isOblivionable() {
		return false;
	}
	
}
