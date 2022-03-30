package theGame.tiles;

import theGame.boardGame.Board;

public class Meadow extends AbstractTile {
	private int heal = 2;
	public Meadow(Board board, int y,int x) {
		super("Field","pictures/meadow.png");
		boolean isBloomingMeadow = false;
		
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(j>20 || i>11) {continue;}
				if (!(board.boardMatrix()[i][j] instanceof Meadow) && !(board.boardMatrix()[i][j].isEmpty())) {
					isBloomingMeadow=true;
					break;
				}
			}
			if (isBloomingMeadow) {break;}
		}
		if (isBloomingMeadow) {
			becomingBloom();
		}
	}
	
	public void becomingBloom() {
		pathToPicture="pictures/bloomingmeadow.png";
		picture = stringToImage(pathToPicture);
		heal=3;
	}
	
	@Override
	public void dailyEffect(Board board) {
		board.hero().regenHPdaily(heal);
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
}
