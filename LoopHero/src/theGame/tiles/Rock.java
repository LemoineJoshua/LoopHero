package theGame.tiles;

import theGame.boardGame.Board;

public class Rock extends AbstractTile {

	/**
	 * Constructeur du Rock,
	 * Vérifie toutes les cartes autours pour appliquer sa synergie avec d'autre Rock
	 * et faire fleurir les Meadow autour
	 * 
	 * @param board le plateau de jeu
	 * @param y sa ligne
	 * @param x sa colonne
	 */
	public Rock(Board board, int y,int x) {
		super("Field","pictures/Tiles/rock.png"); 
		
		board.hero().modifMaxHP((float) 1.01); //la tuile n'étant pas construite elle n'est pas détectée par la boucle ci dessous
		
		for(int i=(y-1); i<=(y+1) ;i++) {
			for(int j=(x-1) ;j<=(x+1) ;j++) {
				if(j>20 || j<0 || i<0|| i>11) {continue;}
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
