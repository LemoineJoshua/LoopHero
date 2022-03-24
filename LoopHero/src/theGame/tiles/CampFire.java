package theGame.tiles;

import theGame.boardGame.Board;

public class CampFire extends AbstractCase{

	public CampFire() {
		super("Road","pictures/campFire.png");
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	//@Override
	public void loopEffect(Board board){
		board.hero().regenHP();		
	}
	
}
