package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.Chest;
import theGame.entities.Spider;

public class BattleField extends AbstractTile implements Serializable{
	private final ArrayList<Coord> spawningTiles;
	
	
	public BattleField(Coord position,AbstractTile[][] matrix) {
		super("RoadSide","pictures/Tiles/battleField.png");
		
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		ArrayList<Coord> spawningTiles = new ArrayList<>();
		
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof AbstractRoad) {
					spawningTiles.add(new Coord(position.x()+coord.x(),position.y()+coord.y()));
				}
			}
		}	
		
		this.spawningTiles=spawningTiles;
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
	public void loopEffect(Board board) {
		ArrayList<AbstractRoad>trueSpawningTiles= new ArrayList<AbstractRoad>();
		for (Coord tileCoord: spawningTiles) {
			AbstractRoad tile = (AbstractRoad) board.boardMatrix()[tileCoord.y()][tileCoord.x()];
			if (tile.aliveMonster().size()<4) {
				trueSpawningTiles.add(tile);
			}
		}
		Chest chest= new Chest();	
		if (trueSpawningTiles.size()>0) {
			AbstractRoad spawningTile = trueSpawningTiles.get(Math.round((float) Math.random()*(trueSpawningTiles.size()-1)));
			spawningTile.addMob(chest);
		}
		
	}
	
	

	
	

}
