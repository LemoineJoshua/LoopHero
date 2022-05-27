package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.entities.AbstractMonster;

public class Village extends AbstractRoad{
	private int wheatFieldAround=0;

	/**
	 * The Village constructor
	 */
	public Village() {
		super("pictures/Tiles/village.png",new ArrayList<AbstractMonster>());
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
		// MAKE OVERGROWN WHEAT FIELD
	}
	
	
}
