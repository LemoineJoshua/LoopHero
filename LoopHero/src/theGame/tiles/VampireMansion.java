package theGame.tiles;

import java.io.Serializable;

public class VampireMansion extends AbstractTile implements Serializable{
	
	private static final long serialVersionUID = 5543530654059961607L;

	/**
	 * Mansion constructor
	 * Initialize the tile, it just have to be there
	 */
	public VampireMansion() {
		super("RoadSide","pictures/Tiles/vampireMansion.png");
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

}
