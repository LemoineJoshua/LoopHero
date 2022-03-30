package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class CampFire extends AbstractRoad{

	public CampFire() {
		super("pictures/campFire.png",new ArrayList<Monster>());
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	//@Override
	public void loopEffect(Board board){
		board.hero().regenHPloop();		
	}
	
}
