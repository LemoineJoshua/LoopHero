package theGame.tiles;

import theGame.boardGame.Board;

public class CampFire extends AbstractRoad{

	public CampFire() {
		super("pictures/campFire.png");
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
