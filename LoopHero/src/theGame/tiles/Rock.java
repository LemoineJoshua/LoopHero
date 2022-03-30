package theGame.tiles;

import theGame.boardGame.Board;

public class Rock extends AbstractTile {

	public Rock(Board board, int y,int x) {
		super("Field","pictures/rock.png"); 
		
		board.hero().modifMaxHP((float) 1.01); //la tuile n'étant pas construite elle n'est pas détectée par la boucle ci dessous
		
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(j>20 || i>11) {continue;}
				if(board.boardMatrix()[i][j] instanceof Rock){
					board.hero().modifMaxHP((float) 1.01);
				}
			}
		}
		super.searchMeadowtoBloom(board, x, y);
		
	}
 
	@Override
	public boolean isEmpty() {
		return false;
	}
	
}
