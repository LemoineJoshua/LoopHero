package fight;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import fr.umlv.zen5.ApplicationContext;
import theGame.GameView;
import theGame.boardGame.Board;
import theGame.entities.Hero;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;
import theGame.tiles.AbstractRoad;

public class Fight {
	private final ApplicationContext ctx;
	private final GameView gameView;
	private final Board board;
	private final CardInventory cardInventory;
	private final RessourcesInventory ressources;
	private final ItemInventory items;
	private final ArrayList<Monster> mobs;
	private final Hero hero;
	
	
	/**
	 * Constrtuctor of the class Fight
	 * which use data such as Hero, gameView to draw, or the board
	 * 
	 * @param gameView : Set of functions related to graphics
	 * @param board : The board of the game, with data such as the hero, the board matrix, or the hero position
	 * @param cardInventory : All cards of the game, split in the deck, and the cards in the player's hand
	 * @param ressources : All the resources collected by the hero
	 * @param itemInventory : Inventory that contains all the equipment the hero has collected, which can now be equipped
	 * @param ctx : Global context of the game
	 */
	public Fight(GameView gameView,Board board,CardInventory cardInventory,RessourcesInventory ressources,ItemInventory itemInventory,ApplicationContext ctx) {
		this.gameView=gameView;
		this.board=board;
		this.cardInventory=cardInventory;
		this.ressources=ressources;
		this.items = itemInventory;
		this.hero=board.hero();
		this.ctx = ctx;
		
		AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
		this.mobs=tile.aliveMonster();
		//System.out.println("---------------------------------");
		
	}
	
	
	/**
	 * Main function of the class Fight, which is a loop that ends 
	 * when either the hero is dead or all ennemies are dead.
	 * In this loop all monsters hit the hero first and the it's time for the hero to play.
	 * 
	 * @return true if the hero win, and false if he loosed.
	 */
	public boolean doFight() {
		for(Monster mob:mobs) {
			mob.fightStats(board.loop());
			
		}
		ArrayList<String> fightProgress = new ArrayList<>();
		int lossHp=0;
		int indexAttack = 0;
		int vampirismRegen = 0;
		while(true){
			//Mobs turn
			int monsterNumber = 1;
			for(Monster mob:mobs) {
				if(mob.isDead()) {
					monsterNumber +=1;
					continue;		
				}	
				fightProgress.add("=> Monstre "+monsterNumber+" attaque.");		// Every line with "fightProgress.add()" are used to stock what will be written in the little terminal on the screen
				
				if(!hero.doEvade()) {		// Condition to know if the hero has evade the mob attack
					int damage=mob.damage();
					//System.out.println("les pv du hero avant : "+hero.hp());
					//System.out.println("les damages du mob : "+damage);
					
					if(hero.doCounter()) {					// If the hero counter, then the monster take their own damage
						lossHp = mob.lossHp(damage);
						fightProgress.add("-Le héros a contré, Monstre "+monsterNumber+" a subi "+lossHp+" dégâts.");
					}else {
						lossHp = hero.lossHp(damage); 		// Else hero loss Hp, calculated with his defense and mob damage.
						fightProgress.add("-Le héros a subi "+lossHp+" dégâts.");
						
						vampirismRegen = mob.vampirismRegen(damage);	// Regen the monster if he got vampirism
						//System.out.println("les pv du hero après : "+hero.hp());

						if (vampirismRegen>0) {
							fightProgress.add("-Le Monstre "+monsterNumber+" a récupéré "+vampirismRegen+" points");
							fightProgress.add("de vie grâce à son vampirisme.");
						}
					}					
					monsterNumber +=1;
					
				}else {				// If the hero has evade
					fightProgress.add("=> Monstre "+monsterNumber+" attaque.");
					fightProgress.add("-Le héros a esquivé.");
				}
				
				//Draw everything that happened during the monster turn
				drawFight(fightProgress);
				fightProgress.clear();
				
			}
			
			// Possible end of fight if the hero is dead
			if(hero.isDead()) {
				return false;
			}
			
			// Hero's turn
			Monster mob=mobs.get(indexAttack);
			if(!mob.doEvade()) {			// Condition to know if the monster has evade the mob attack
				int damage=hero.damage();
				fightProgress.add("=> Le héros attaque le Monstre "+(indexAttack+1));
				//System.out.println("les du mob pv avant : " + mob.hp());
				//System.out.println("damage du hero : "+damage);
				
				
				if(mob.doCounter()) {			// If the monster counter, then the hero take his own attack
					lossHp = hero.lossHp(damage);
					fightProgress.add("-Le Monstre "+(indexAttack+1)+" a contré, le héros a subi "+lossHp+" dégâts.");
				}else {							// Else the monster loss Hp, calculated with his defense and hero damage.
					lossHp = mob.lossHp(damage);
					fightProgress.add("-Le Monstre "+(indexAttack+1)+" a subi "+lossHp+" dégâts.");
					vampirismRegen = hero.vampirismRegen(damage);
					if (vampirismRegen>0) {			// Regen the hero if he got vampirism
						fightProgress.add("-Le Héros a récupéré "+vampirismRegen+" points");
						fightProgress.add("de vie grâce à son vampirisme.");
					}
				}
				
				//System.out.println("les pv du mob après: " + mob.hp());				
				
			}else {				// If the hero has evade
				fightProgress.add("=> Le héros attaque le Monstre "+(indexAttack+1));
				fightProgress.add("-Le Monstre "+(indexAttack+1)+" a esquivé.");
				//System.out.println("il a esquive");
			}
			if(mobs.get(indexAttack).isDead()) {
				indexAttack++;	
			}
			
			//Draw everything that happened during the hero turn
			drawFight(fightProgress);
			fightProgress.clear();
			
			// Everyone regen time
			hero.regenTurn();
			mob.regenTurn();
						
			// Fight end condition
			if (allMobDead()) {
				//System.out.println("les pv du hero à la fin du combat : "+hero.hp());
				AbstractRoad tile = (AbstractRoad) board.boardMatrix()[board.heroY()][board.heroX()];
				tile.clearMob(ressources, cardInventory, items, board.loop());
				return true;
			}
		}	
	}
	
	/**
	 * Function that calls the drawFight of the gameView class to draw
	 * Then wait a little bit, to let the time to the player to read
	 * 
	 * @param fightProgress : A list of sentences which explain the fight progression
	 */
	private void drawFight(ArrayList<String> fightProgress) {
		
		gameView.drawFight(ctx, hero, mobs, fightProgress);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return true if all monsters are dead, else return false
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
