package theGame.tiles;

import theGame.boardGame.Board;

public class Meadow extends AbstractTile {

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
