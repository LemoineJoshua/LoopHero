package theGame.tiles;

import java.io.Serializable;

public class EmptyField extends AbstractTile implements Serializable{
	
	private static final long serialVersionUID = 8620427698321779919L;

	/**
	 * Empty fiel constructor
	 * This is a tile far which is not next to a Road, without any card on it
	 */
	public EmptyField() {
		super("Field","");
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
