package fight;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import theGame.GameView;
import theGame.TimeData;
import theGame.boardGame.Board;
import theGame.entities.Hero;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;

public class Fight {
	private final TimeData timeData;
	private final GameView gameView;
	private final Board board;
	private final CardInventory deck;
	private final RessourcesInventory ressources;
	private final ItemInventory items;
	private final ArrayList<Monster> mobs;
	private final Hero hero;
	
	
	public Fight(TimeData timeData,GameView gameView,Board board,CardInventory deck,RessourcesInventory ressources,ItemInventory itemInventory) {
		this.timeData=timeData;
		this.gameView=gameView;
		this.board=board;
		this.deck=deck;
		this.ressources=ressources;
		this.items = itemInventory;
		this.hero=board.hero();
		
		AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
		this.mobs=tile.aliveMonster();
		//System.out.println("---------------------------------");
		
	}
	
	public boolean doFight() {
		for(Monster mob:mobs) {
			mob.fightStats(board.loop());
			
		}
		
		
		int indexAttack = 0;
		while(true){
			
			//phase des mobs
			for(Monster mob:mobs) {
				/*
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				
				if(!hero.doEvade()) {
					int damage=mob.damage();
					//System.out.println("les pv du hero avant : "+hero.hp());
					//System.out.println("les damages du mob : "+damage);
					
					if(hero.doCounter()) {
						mob.lossHp(damage);
					}else {
						hero.lossHp(damage);
					}
					
					mob.vampirismRegen(damage);
					//System.out.println("les pv du hero après : "+hero.hp());
				}
			}
			
			//phase du hero
			Monster mob=mobs.get(indexAttack);
			if(!mob.doEvade()) {
				int damage=hero.damage();
				//System.out.println("les du mob pv avant : " + mob.hp());
				//System.out.println("damage du hero : "+damage);
				
				
				if(mob.doCounter()) {
					hero.lossHp(damage);
				}else {
					mob.lossHp(damage);
				}
				
				//System.out.println("les pv du mob après: " + mob.hp());
				
				hero.vampirismRegen(damage);
			}else {
				//System.out.println("ho no il a veski le batar");
			}
			if(mobs.get(indexAttack).isDead()) {
				indexAttack++;	
			}
			
			//regen de tout le monde
			hero.regenTurn();
			mob.regenTurn();
			
			
			//fin de combat hypothetique
			if (allMobDead()) {
				//System.out.println("les pv du hero à la fin du combat : "+hero.hp());
				AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
				tile.clearMob(ressources, deck, items, board.loop());
				return true;
			}
			
			if(hero.isDead()) {
				return false;
			}
		}
		
		
	}
	
	/**
	 * @return true si tout les monstres sont mort, false sinon
	 */
	public boolean allMobDead() {
		for(Monster mob:mobs) {
			if(!mob.isDead()) {
				return false;
			}
		}
		return true;
	}
}
