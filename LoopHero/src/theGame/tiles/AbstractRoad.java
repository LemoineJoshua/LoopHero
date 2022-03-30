package theGame.tiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import theGame.GameData;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;

public class AbstractRoad extends AbstractTile {

	private final ArrayList<Monster> aliveMonster;
	
	public AbstractRoad(String img, ArrayList<Monster> aliveMonster) {
		super("Road",img);
		if (aliveMonster.isEmpty()) {
			this.aliveMonster = new ArrayList<Monster>();
		}else {
			this.aliveMonster = aliveMonster;
		}
		
	}
	
	
	public boolean isCombat() {
		return !aliveMonster.isEmpty();
	}
	
	public void clearMob(RessourcesInventory lootList,CardInventory cardInventory){
		int countBranches = 0;
		for(Monster mob:aliveMonster) {//comme ça on pourra gérer le drop facilement à la fin du combat quand on vide la case
			if(!mob.dropCard()) {
				for(String loot:mob.drop()) {
					lootList.addRessources(loot, 5);
				}
			}else {
				int nb = (int) Math.round(Math.random() * (cardInventory.deck().size()-1));
				cardInventory.cardList().add(cardInventory.deck().get(nb));
				cardInventory.overflowForbidden();
				cardInventory.deck().remove(nb);
			}
			if (Math.random()>0.5) {countBranches ++;}
		}
		
		if (this instanceof Grove) {
			lootList.addRessources("Branches", 1+countBranches);
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
	
	public void enteringEffect(GameData gameData) {}
	
}
