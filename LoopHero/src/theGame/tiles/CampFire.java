package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;

public class CampFire extends AbstractRoad implements Serializable{

	private static final long serialVersionUID = -9156873253702853412L;

	/**
	 * The CampFire constructor
	 * The Campfire heal the player at each loop beginning
	 * 
	 * @param position : the coord of the tile
	 */
	public CampFire(Coord position) {
		super("pictures/Tiles/campFire.png",new ArrayList<AbstractMonster>(), position);
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
		Objects.requireNonNull(board);
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
