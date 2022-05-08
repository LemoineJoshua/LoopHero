package theGame.tiles;

import theGame.boardGame.Board;

public class Rock extends AbstractTile {

	int x;
	int y;
	/**
	 * rock constructor
	 * Check all tiles around to apply the synergy with the others Rock
	 * and bloom the Meadow around 
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 * @param y : the cell line
	 * @param x : the cell column
	 */
	public Rock(Board board, int y,int x) {
		super("Field","pictures/Tiles/rock.png"); 
				
		board.hero().modifMaxHP((float) (board.hero().maxHp()*0.01)); //la tuile n'étant pas construite elle n'est pas détectée par la boucle ci dessous
		
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(j>20 || j<0 || i<0|| i>11) {continue;}
				if(board.boardMatrix()[i][j] instanceof Rock){
					board.hero().modifMaxHP((float) (board.hero().maxHp()*0.01));
				}
			}
		}
		
		super.searchMeadowtoBloom(board, x, y);
		this.x = x;
		this.y = y;
		
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
	
	@Override
	public void removingEffect(Board board) {
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(j>20 || j<0 || i<0|| i>11) {continue;}
				if(board.boardMatrix()[i][j] instanceof Rock){
					board.hero().modifMaxHP((float) ((-1) * board.hero().maxHp()/101));
				}
			}
		}
		System.out.println(board.hero().maxHp());
	}
	
}
