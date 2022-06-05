package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.entities.ScareCrow;

public class OvergrownWheatField extends AbstractRoad implements Serializable{

	private int day = 0;
	
	
	/**
	 * the OvergrowWheatField constructor
	 * 
	 * @param aliveMonster : the monster living on the tile
	 * @param position : the coord of the tile
	 */
	public OvergrownWheatField(ArrayList<AbstractMonster> aliveMonster, Coord position) {
		super("pictures/Tiles/overgrownWheatField.png", aliveMonster, position);	
	}
	
	/**
	 * Spawn a scarecrow in the wheatField, each four days
	 */
	@Override
	public void dailyEffect(Board board) {	
		Objects.requireNonNull(board);
		day+=1;
		if(day%4==0) {
			ScareCrow scareCrow= new ScareCrow();
			this.addMob(scareCrow);
		}
	}

}
