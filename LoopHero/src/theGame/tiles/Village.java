package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;

public class Village extends AbstractRoad implements Serializable{
	private int wheatFieldAround=0;

	/**
	 * The Village constructor
	 */
	public Village(Coord position) {
		super("pictures/Tiles/village.png",new ArrayList<AbstractMonster>(),position);
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
	

	@Override
	public void enteringEffect(GameData gameData) {
		int totalHealing = (15*gameData.board().loop()) + (5*gameData.board().loop()*wheatFieldAround);
		gameData.board().hero().regenHPdaily(totalHealing);
		
		// GIVE A QUEST
	}
	
	
	public void removingEffect(Board board) {
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		AbstractTile[][] matrix = board.boardMatrix();
		
		
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof WheatFields) {
					matrix[position.y()+coord.y()][position.x()+coord.x()]=new OvergrownWheatField(aliveMonster,position);
				}
			}
		}	
	}
	
	
	public void wheatFieldNearby() {
		wheatFieldAround++;
	}
	
}
