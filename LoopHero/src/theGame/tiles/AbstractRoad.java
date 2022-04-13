package theGame.tiles;

import java.util.ArrayList;
import theGame.GameData;
import theGame.entities.Monster;
import theGame.inventories.CardInventory;
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
        if(aliveMonster.size()<1) {
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
