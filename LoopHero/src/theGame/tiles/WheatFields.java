package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.ScareCrow;

public class WheatFields extends AbstractRoad implements Serializable{
	private static final long serialVersionUID = -5592914800072028413L;
	private int day = 0;

	/**
	 * The Wheat Fields constructor
	 * 
	 * @param position : the coords of the tile
	 * @param matrix : the matrix of tile of the board
	 * @param monsters : the monsters living on the tile.
	 */
	public WheatFields(Coord position,AbstractTile[][] matrix, ArrayList<AbstractMonster> monsters) {
		super("pictures/Tiles/wheatFields.png",monsters,position);
		searchVillage(matrix);
	}
	
	/**
	 * Check if the cell is Empty, which mean we could place a card on it
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}
		
	/**
	 * Check if we can the oblivion card on this tile
	 * 
	 * @return true if we can use it, false else.
	 */
	@Override
	public boolean isOblivionable() {
		return true;
	}
	
	
	/**
	 * Spawn a scarecrow in the wheatField, each four days
	 * 
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 */
	@Override
	public void dailyEffect(Board board) {	
		Objects.requireNonNull(board);
		day+=1;
		if(day%4==0) {
			ScareCrow scareCrow= new ScareCrow();
			this.addMob(scareCrow);
		}
	}
	
	/**
	 * Search Village around to increment the number of wheat field of this village
	 * 
	 * @param matrix : the matrix of tile of the board
	 */
	private void searchVillage(AbstractTile[][] matrix) {
		Objects.requireNonNull(matrix);
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof Village) {
					Village villageAround = (Village) matrix[position.y()+coord.y()][position.x()+coord.x()];
					villageAround.wheatFieldNearby();
				}
			}
		}	
	}
	
	/**
	 *Search village to apply them the effect, of oblivionning a wheat field
	 */
	@Override
	public void removingEffect(Board board) {
		Objects.requireNonNull(board);
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		AbstractTile[][] matrix = board.boardMatrix();
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof Village) {
					Village villageAround = (Village) matrix[position.y()+coord.y()][position.x()+coord.x()];
					villageAround.wheatFieldOblivionned();
				}
			}
		}	
		
	}
}
