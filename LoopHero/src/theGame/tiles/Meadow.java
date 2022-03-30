package theGame.tiles;

import theGame.boardGame.Board;

public class Meadow extends AbstractTile {
	private int heal = 2;
	public Meadow(Board board, int y,int x) {
		super("Field","pictures/meadow.png");
		boolean isBloomingMeadow = false;
		
		for (int i= x-1;i<x+2; i+=2) {
			if(i<0 || i>20) {continue;}
				if(!(board.boardMatrix()[y][i] instanceof Meadow) && !(board.boardMatrix()[y][i].isEmpty())){
					isBloomingMeadow=true;
					break;
				}
		}
		for (int j= y-1;j<y+2; j+=2) {
			if(j>11 || j<0 ) {continue;}
				if(!(board.boardMatrix()[j][x] instanceof Meadow) && !(board.boardMatrix()[j][x].isEmpty())){
					isBloomingMeadow=true;
					break;
				}
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
