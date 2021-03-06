package theGame.tiles;

import java.io.Serializable;

import theGame.boardGame.Board;

public class Rock extends AbstractTile implements Serializable{

	private static final long serialVersionUID = -399593308381128305L;
	int x;
	int y;
	/**
	 * rock constructor
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 * @param y : the cell line
	 * @param x : the cell column
	 */
	public Rock(Board board, int y,int x) {
		super("Field","pictures/Tiles/rock.png"); 
				
		board.hero().modifMaxHP((float) (board.hero().maxHp()*0.01)); //la tuile n'?tant pas construite elle n'est pas d?tect?e par la boucle ci dessous
		
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(j>20 || j<0 || i<0|| i>11) {continue;}
				if(board.boardMatrix()[i][j] instanceof Rock){
					board.hero().modifMaxHP((float) (board.hero().maxHp()*0.01));
				}
			}
		}
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
	
	/**
	 *Apply the olbivion effect when used on a rock
	 */
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
