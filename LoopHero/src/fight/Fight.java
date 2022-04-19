package fight;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import fr.umlv.zen5.ApplicationContext;
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
	private final ApplicationContext ctx;
	private final TimeData timeData;
	private final GameView gameView;
	private final Board board;
	private final CardInventory deck;
	private final RessourcesInventory ressources;
	private final ItemInventory items;
	private final ArrayList<Monster> mobs;
	private final Hero hero;
	
	
	public Fight(TimeData timeData,GameView gameView,Board board,CardInventory deck,RessourcesInventory ressources,ItemInventory itemInventory,ApplicationContext ctx) {
		this.timeData=timeData;
		this.gameView=gameView;
		this.board=board;
		this.deck=deck;
		this.ressources=ressources;
		this.items = itemInventory;
		this.hero=board.hero();
		this.ctx = ctx;
		
		AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
		this.mobs=tile.aliveMonster();
		//System.out.println("---------------------------------");
		
	}
	
	public boolean doFight() {
		for(Monster mob:mobs) {
			mob.fightStats(board.loop());
			
		}
		ArrayList<String> fightProgress = new ArrayList<>();
		int lossHp=0;
		int indexAttack = 0;
		int vampirismRegen = 0;
		while(true){
			//phase des mobs
			int monsterNumber = 1;
			for(Monster mob:mobs) {
				fightProgress.add("=> Monstre "+monsterNumber+" attaque.");
				
				if(!hero.doEvade()) {
					int damage=mob.damage();
					//System.out.println("les pv du hero avant : "+hero.hp());
					//System.out.println("les damages du mob : "+damage);
					
					if(hero.doCounter()) {
						lossHp = mob.lossHp(damage);
						fightProgress.add("-Le héros a contré, Monstre "+monsterNumber+" a subi "+lossHp+" dégâts.");
					}else {
						lossHp = hero.lossHp(damage);
						fightProgress.add("-Le héros a subi "+lossHp+" dégâts.");
					}
					
					vampirismRegen = mob.vampirismRegen(damage);
					//System.out.println("les pv du hero après : "+hero.hp());
					
					// A FAIRE 
					fightProgress.add("-Le Monstre "+monsterNumber+" a récupéré "+vampirismRegen+" points");
					fightProgress.add("de vie grâce à son vampirisme.");
					monsterNumber +=1;
				}else {
					fightProgress.add("-Le héros a esquivé.");
				}
				
				drawFight(fightProgress);
				fightProgress.clear();
				
			}
			
			//phase du hero
			Monster mob=mobs.get(indexAttack);
			if(!mob.doEvade()) {
				int damage=hero.damage();
				fightProgress.add("=> Le héros attaque le Monstre "+(indexAttack+1));
				//System.out.println("les du mob pv avant : " + mob.hp());
				//System.out.println("damage du hero : "+damage);
				
				
				if(mob.doCounter()) {
					lossHp = hero.lossHp(damage);
					fightProgress.add("-Le Monstre "+(indexAttack+1)+" a contré, le héros a subi "+lossHp+" dégâts.");
				}else {
					lossHp = mob.lossHp(damage);
					fightProgress.add("-Le Monstre "+(indexAttack+1)+" a subi "+lossHp+" dégâts.");
				}
				
				//System.out.println("les pv du mob après: " + mob.hp());
				
				vampirismRegen = hero.vampirismRegen(damage);
				fightProgress.add("-Le Héros a récupéré "+vampirismRegen+" points");
				fightProgress.add("de vie grâce à son vampirisme.");
			}else {
				fightProgress.add("-Le Monstre "+(indexAttack+1)+" a esquivé.");
				//System.out.println("ho no il a veski le batar");
			}
			if(mobs.get(indexAttack).isDead()) {
				indexAttack++;	
			}
			
			drawFight(fightProgress);
			fightProgress.clear();
			
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
	
	private void drawFight(ArrayList<String> fightProgress) {
		gameView.drawFight(ctx, hero, mobs, fightProgress);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
