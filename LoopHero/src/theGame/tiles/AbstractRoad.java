package theGame.tiles;

import java.util.ArrayList;
import theGame.GameData;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
import theGame.inventories.Item;
import theGame.inventories.ItemInventory;
import theGame.inventories.RessourcesInventory;

public class AbstractRoad extends AbstractTile {

	private final ArrayList<Monster> aliveMonster;
	
	/**
	 * Constructeur d'une abstractRoad
	 * initialise sont image, son type, et les monstres dessus
	 * 
	 * @param img l'image de la carte 
	 * @param aliveMonster les monstres sur la tuile avant le placage de carte
	 */
	public AbstractRoad(String img, ArrayList<Monster> aliveMonster) {
		super("Road",img);
		if (aliveMonster.isEmpty()) {
			this.aliveMonster = new ArrayList<Monster>();
		}else {
			this.aliveMonster = aliveMonster;
		}
		
	}
	
	
	/**
	 * @return true si il y à un combat, false sinon
	 */
	public boolean isFight() {
		return !aliveMonster.isEmpty();
	}
	
	/**
	 * Vide les mobs de la case à la fin d'un combat.
	 * Gère les loot et les branches du grove (seule carte ayant ce type de comportement)
	 * 
	 * 
	 * @param lootList l'inventaire des ressources
	 * @param cardInventory l'inventaire des cartes
	 */
	public void clearMob(RessourcesInventory lootList,CardInventory cardInventory,ItemInventory itemInventory, int loop){
		int countBranches = 0;
		for(Monster mob:aliveMonster) {//comme ça on pourra gérer le drop facilement à la fin du combat quand on vide la case
			if(!mob.dropCard()) {
				
				double type = Math.random();
				Item item;
				if(type>0.75) {
					item = new Item(0.0,((loop*4)+(Math.random()*(loop*6 - loop*4))),0.0,0.0,0.0,0.0,0.0,0,"","weapon");
				}else if(type>0.5){
					item = new Item(0.0,0.0,4*loop,0.0,0.0,0.0,0.0,0,"","shield");
				}else if(type>0.25) {
					item = new Item(((loop*80)+(Math.random()*(loop*80 - loop*100))),0.0,0.0,0.0,0.0,0.0,0.0,0,"","armor");
				}else {
					item = new Item(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,"","ring");
				}
				itemInventory.add(item);
				
				
			}else {
				int nb = (int) Math.round(Math.random() * (cardInventory.deck().size()-1));
				cardInventory.cardList().add(cardInventory.deck().get(nb));
				cardInventory.overflowForbidden();
				cardInventory.deck().remove(nb);
			}
			if (Math.random()>0.5) {countBranches ++;}
			
			for(String loot:mob.drop()) { //en fait ils loots tout le temps des ressources
				lootList.addRessources(loot, 5);
			}
		}
		
		
		
		if (this instanceof Grove) {
			lootList.addRessources("Branches", 1+countBranches);
		}
		
		aliveMonster.clear();
	}
	
	/**
	 * @return la liste des monstres sur la carte
	 */
	public ArrayList<Monster> aliveMonster(){
		 return aliveMonster;
	}
	
	/**
	 * @param newMonster ajoute un monstre sur la carte
	 */
	public void addMob(Monster newMonster){
        if(aliveMonster.size()<4) {
        	aliveMonster.add(newMonster);
        }
    }
	
	/**
	 * Gère les effets lorsque le hero arrive sur une case
	 * 
	 * @param gameData données du jeux
	 */
	public void enteringEffect(GameData gameData) {}
	
}
