package fight;

import java.util.ArrayList;
import theGame.GameView;
import theGame.TimeData;
import theGame.boardGame.Board;
import theGame.entities.Hero;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;

public class Fight {
	private final TimeData timeData;
	private final GameView gameView;
	private final Board board;
	private final CardInventory deck;
	private final RessourcesInventory ressources;
	private final ArrayList<Monster> mobs;
	private final Hero hero;
	
	
	public Fight(TimeData timeData,GameView gameView,Board board,CardInventory deck,RessourcesInventory ressources) {
		this.timeData=timeData;
		this.gameView=gameView;
		this.board=board;
		this.deck=deck;
		this.ressources=ressources;
		
		this.hero=board.hero();
		
		AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
		this.mobs=tile.aliveMonster();
		System.out.println("---------------------------------");
		
	}
	
	public boolean doFight() {
		for(Monster mob:mobs) {
			mob.fightStats(board.loop());
			
		}
		
		
		int indexAttack = 0;
		while(true){
			
			//phase des mobs
			for(Monster mob:mobs) {
				
				if(!hero.doEvade()) {
					int damage=mob.damage();
					
					if(hero.doCounter()) {
						mob.lossHp(damage);
					}else {
						hero.lossHp(damage);
					}
					
					mob.vampirismRegen(damage);
				}
			}
			
			//phase du hero
			Monster mob=mobs.get(indexAttack);
			if(!mob.doEvade()) {
				int damage=hero.damage();
				System.out.println("les pv avant: " + mob.hp());
				System.out.println("damage du hero rigolo : "+damage);
				
				
				if(mob.doCounter()) {
					hero.lossHp(damage);
				}else {
					mob.lossHp(damage);
				}
				
				System.out.println("les pv après: " + mob.hp());
				
				hero.vampirismRegen(damage);
			}else {
				System.out.println("ho no il a veski le batar");
			}
			if(mobs.get(indexAttack).isDead()) {
				indexAttack++;	
			}
			
			//regen de tout le monde
			hero.regenTurn();
			mob.regenTurn();
			
			
			//fin de combat hypothetique
			if (allMobDead()) {
				AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
				tile.clearMob(ressources, deck);
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
