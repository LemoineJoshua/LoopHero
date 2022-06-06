package theGame.tiles;

import java.io.Serializable;

public class EmptyRoadSide extends AbstractTile implements Serializable{
	
	private static final long serialVersionUID = -7458113470512903928L;


	/**
	 * EmptyRoadSide constructor 
	 * This is a Tile next to a road without any card on it
	 */
	public EmptyRoadSide() {
		super("RoadSide","");
	}

	
	/**
	 * Check if we can the oblivion card on this tile
	 * 
	 * @return true if we can use it, false else.
	 */
	@Override
	public boolean isOblivionable() {
		return false;
	}
}
