package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.Chest;
import theGame.entities.Mimic;

public class BattleField extends AbstractTile implements Serializable{
	private static final long serialVersionUID = 7728425123449194094L;
	private final ArrayList<Coord> spawningTiles;
	
	
	/**
	 * The BatteField constructor
	 * @param position : the coords of the tile 
	 * @param matrix : the matrix of tile of the game
	 */
	public BattleField(Coord position,AbstractTile[][] matrix) {
		super("RoadSide","pictures/Tiles/battleField.png");
		
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(1,1));
		posibilities.add(new Coord(-1,-1));
		posibilities.add(new Coord(1,-1));
		posibilities.add(new Coord(-1,1));
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
		Objects.requireNonNull(board);
		ArrayList<AbstractRoad>trueSpawningTiles= new ArrayList<AbstractRoad>();
		for (Coord tileCoord: spawningTiles) {
			AbstractRoad tile = (AbstractRoad) board.boardMatrix()[tileCoord.y()][tileCoord.x()];
			if (tile.aliveMonster().size()<4) {
				trueSpawningTiles.add(tile);
			}
		}
		
		
		
		if (trueSpawningTiles.size()>0) {
			AbstractRoad spawningTile = trueSpawningTiles.get(Math.round((float) Math.random()*(trueSpawningTiles.size()-1)));
			if (Math.random()>0.5) {
				spawningTile.addMob(new Chest());
			}else {
				spawningTile.addMob(new Mimic());
			}
		}
		
	}
	
	

	
	

}
