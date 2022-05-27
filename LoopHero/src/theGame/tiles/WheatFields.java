package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.AbstractMonster;
import theGame.entities.ScareCrow;
import theGame.entities.ScorchWorm;

public class WheatFields extends AbstractRoad{
	private int day = 0;

	/**
	 * The Wheat Fields constructor
	 */
	public WheatFields() {
		super("pictures/Tiles/wheatFields.png",new ArrayList<AbstractMonster>());
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
	
	private void searchVillage() {
		
	}
}
