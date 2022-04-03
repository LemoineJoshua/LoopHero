package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class CampFire extends AbstractRoad{

	/**
	 * Le feu de camps, soigne le joueur à chaque boucle
	 */
	public CampFire() {
		super("pictures/Tiles/campFire.png",new ArrayList<Monster>());
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	//@Override
	/**
	 *Soigne les pv du héro à chaque tour 
	 */
	public void loopEffect(Board board){
		board.hero().regenHPloop();		
	}
	
}
