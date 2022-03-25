package theGame.tiles;

import theGame.boardGame.Board;

public class Rock extends AbstractCase {

	public Rock(Board board, int y,int x) {
		super("Field","pictures/rock.png"); 
		
		board.hero().modifMaxHP((float) 1.01); //la tuile n'�tant pas construite elle n'est pas d�tect�e par la boucle ci dessous
		
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(x+j>21 || y+i>12) {break;}
				if(board.matricePlateau()[i][j] instanceof Rock){
					board.hero().modifMaxHP((float) 1.01);
				}
			}
		}
		
	}
 
	@Override
	public boolean isEmpty() {
		return false;
	}
	
}