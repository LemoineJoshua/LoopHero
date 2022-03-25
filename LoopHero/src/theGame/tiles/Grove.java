package theGame.tiles;

import java.util.ArrayList;

import theGame.boardGame.Board;
import theGame.entities.Monster;

public class Grove extends AbstractCase {
	private int position;
	private int day = 0;
	private final ArrayList<Monster> aliveMonster;
	
	public Grove(int position) {
		super("Road","pictures/grove.png");
		this.position = position;
		this.aliveMonster = new ArrayList<Monster>();
	} 
	
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	/*
	public void dailyEffect(Board board) {
		if(day%2==0) {
			double test = Math.random();
			if(test>0.6 && aliveMonster.size()<1){
				aliveMonster.add(new Monster());
			}else if(test>0.3 && board.listeCoord()[position+1].havePlace()) {
				
			}
		}
	}
	*/
}
