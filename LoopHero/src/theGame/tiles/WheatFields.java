package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.ScareCrow;
import theGame.entities.ScorchWorm;

public class WheatFields extends AbstractRoad implements Serializable{
	private int day = 0;

	/**
	 * The Wheat Fields constructor
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
	 */
	@Override
	public void dailyEffect(Board board) {	
		day+=1;
		if(day%4==0) {
			ScareCrow scareCrow= new ScareCrow();
			this.addMob(scareCrow);
		}
	}
	
	private void searchVillage(AbstractTile[][] matrix) {
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
	
	@Override
	public void removingEffect(Board board) {
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
