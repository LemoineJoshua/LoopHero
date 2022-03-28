package theGame.tiles;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Grove extends AbstractRoad {
	private final int position;
	private int day = 0;
	private final Monster ratWolf = new Monster((long)13,3.3,0.0,0.0,0.0,0.0,0.0,"pictures/ratWolf.png",(float)0.05);
	
	public Grove(int position) {
		super("pictures/grove.png");
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
				upperTile = (AbstractRoad) board.matricePlateau()[board.listeCoord()[position+1].y()][board.listeCoord()[position+1].x()];
			}
			if(position>1) {
				lowerTile = (AbstractRoad) board.matricePlateau()[board.listeCoord()[position-1].y()][board.listeCoord()[position-1].x()];
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
