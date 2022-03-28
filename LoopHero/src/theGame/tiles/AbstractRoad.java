package theGame.tiles;

import java.util.ArrayList;
import theGame.entities.Monster;

public class AbstractRoad extends AbstractTile {

	private final ArrayList<Monster> aliveMonster;
	
	public AbstractRoad(String img) {
		super("Road",img);
		this.aliveMonster = new ArrayList<Monster>();
	}
	
	
	public boolean isCombat() {
		return !aliveMonster.isEmpty();
	}
	
	public void clearMob(){
		aliveMonster.clear();
	}
	
	public ArrayList<Monster> aliveMonster(){
		 return aliveMonster;
	}
	
	public void addMob(Monster newMonster){
        if(aliveMonster.size()<1) {
        	aliveMonster.add(newMonster);
        }
    }
}
