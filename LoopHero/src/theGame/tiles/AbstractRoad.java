package theGame.tiles;

import java.util.ArrayList;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;

public class AbstractRoad extends AbstractTile {

	private final ArrayList<Monster> aliveMonster;
	
	public AbstractRoad(String img) {
		super("Road",img);
		this.aliveMonster = new ArrayList<Monster>();
	}
	
	
	public boolean isCombat() {
		return !aliveMonster.isEmpty();
	}
	
	public void clearMob(RessourcesInventory lootList,CardInventory CardList){
		for(Monster mob:aliveMonster) {//comme ça on pourra gérer le drop facilement à la fin du combat quand on vide la case
			if(!mob.dropCard()) {
				for(String loot:mob.drop()) {
					lootList.addRessources(loot, 5);
					System.out.println(lootList);
				}
			}
		}
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
