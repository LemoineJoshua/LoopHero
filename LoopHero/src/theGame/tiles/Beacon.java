package theGame.tiles;

public class Beacon extends AbstractTile {

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
}