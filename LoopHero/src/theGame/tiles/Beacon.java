package theGame.tiles;

public class Beacon extends AbstractTile {

	private static final long serialVersionUID = 8303416994688846745L;

	/**
	 * The beacon constructor
	 */
	public Beacon() {
		super("Field","pictures/Tiles/beacon.png");
	}
	
	@Override
	public boolean imABeacon() {
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override 
	public boolean isOblivionable() {
		return true;
	}
}
