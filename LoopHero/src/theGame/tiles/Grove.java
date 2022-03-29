package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Grove extends AbstractRoad {
	private final int position;
	private int day = 0;
	private final Monster ratWolf;
	
	public Grove(int position) {
		super("pictures/grove.png");
		ArrayList<String> drop = new ArrayList<>();
		drop.add("Living fabric");
		this.ratWolf= new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/ratWolf.png",(float)0.05,(float)0.6,drop);
		this.position = position;
	} 
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	
	@Override
	public void dailyEffect(Board board) {
		if(day%2==0) {
			double test = Math.random();
			
			AbstractRoad upperTile=null;
			AbstractRoad lowerTile=null;
			if(position<33){
				upperTile = (AbstractRoad) board.boardMatrix()[board.coordList()[position+1].y()][board.coordList()[position+1].x()];
			}
			if(position>1) {
				lowerTile = (AbstractRoad) board.boardMatrix()[board.coordList()[position-1].y()][board.coordList()[position-1].x()];
			}
			
			if(test>0.6 && lowerTile!=null && lowerTile.aliveMonster().isEmpty()){ 
				lowerTile.addMob(ratWolf);
			}else if(test>0.3 && upperTile!=null && upperTile.aliveMonster().isEmpty()) {
				upperTile.addMob(ratWolf);
			}else if(this.aliveMonster().isEmpty()) {
				this.addMob(ratWolf);
			}

		}
		day+=1;
	}

}
