package theGame.tiles;

import theGame.boardGame.Board;

public class Meadow extends AbstractTile {
	private int heal = 2;
	
	
	/**
	 * Meadow constructor,
	 * Verify if the Meadow got to bloom
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 * @param y : the cell line
	 * @param x : the cell column
	 */
	public Meadow(Board board, int y,int x) {
		super("Field","pictures/Tiles/meadow.png");
		searchToBloom(x ,y ,board);
	}
	
	public void searchToBloom(int x, int y, Board board) {
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
	
	/**
	 * Turn the Meadow into a Blooming Meadow, change his appearance and his heal amount
	 */
	public void becomingBloom() {
		pathToPicture="pictures/Tiles/bloomingmeadow.png";
		picture = stringToImage(pathToPicture);
		heal=3;
	}
	
	public void becomingUnbloom() {
		pathToPicture="pictures/Tiles/meadow.png";
		picture = stringToImage(pathToPicture);
		heal=2;
	}
	
	/**
	 * Apply the daily effect of the cell
	 * Regen the Hp of the hero every day
	 */
	@Override
	public void dailyEffect(Board board) {
		board.hero().regenHPdaily(heal);
	}
	
	/**
	 * Check if the cell is Empty, which mean we could place a card on it
	 * 
	 * @return true if the cell is empty, false else
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}
}
