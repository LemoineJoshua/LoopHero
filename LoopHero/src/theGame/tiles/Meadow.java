package theGame.tiles;

import theGame.boardGame.Board;

public class Meadow extends AbstractCase {

	public Meadow() {
		super("Field","pictures/meadow.png");
	}
	
	@Override
	public void dailyEffect(Board board) {
		board.hero().regenHPdaily(2);
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
}
