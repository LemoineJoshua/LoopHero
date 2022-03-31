package theGame.inventories;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class RessourcesInventory {
    private final ArrayList<String> inventaireRessourceNom;
	private final ArrayList<Integer> inventaireRessourceQuantite;
	
	
	/**
	 * Constructeur de l'inventaire des ressources
	 * Initialise les diff�rentes ressources et met leur quantit� � 0
	 */
	public RessourcesInventory() {
		this.inventaireRessourceNom = new ArrayList<String>();
        initName();
		this.inventaireRessourceQuantite = new ArrayList<Integer>();
        initQuantitie();
        }
	

    /**
     * nitialise les diff�rentes ressources
     */
    public void initName(){        
		inventaireRessourceNom.add("Branches");
        inventaireRessourceNom.add("Wood");
        inventaireRessourceNom.add("Pebbles");
        inventaireRessourceNom.add("Rock");
        inventaireRessourceNom.add("ScrapMetal");
        inventaireRessourceNom.add("Metal");
        inventaireRessourceNom.add("Ration");
        inventaireRessourceNom.add("FoodSupply");
        inventaireRessourceNom.add("MemoryFragment");
        inventaireRessourceNom.add("MemoryBook");
        inventaireRessourceNom.add("NoticableChange");
        inventaireRessourceNom.add("Metamorphosis");
        inventaireRessourceNom.add("Craft Fragment");
        inventaireRessourceNom.add("Shapeless Mass");
        inventaireRessourceNom.add("Living Fabric");
    }

    /**
     * Met les quantit�es des ressources � 0
     */
    public void initQuantitie(){
        for (int i=0; i<15; i++){
            inventaireRessourceQuantite.add(0);
        }
    }

    /**
     * Ajoute des ressources � l'inventaire
     * 
     * @param name nom de la ressource � ajouter
     * @param quantitie quantite � ajouter
     */
    public void addRessources(String name, int quantitie){
        int indexRessource=inventaireRessourceNom.indexOf(name);
        inventaireRessourceQuantite.set(indexRessource,inventaireRessourceQuantite.get(indexRessource)+quantitie);   
    }
    
    /**
     * Affiche la liste des ressources du joueur
     * 
     * @param x abscisse 
     * @param y ordonn�e
     * @param graphics Objet de dessin
     * @param squareSize Taille d'un carr� de du plateau (taille de r�f�rence)
     */
    public void afficheRessource(int x, int y, Graphics graphics, int squareSize) {
    	graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));		
    	for(int i = 0;i<15;i++) {
    		graphics.drawString(inventaireRessourceNom.get(i)+" : "+ inventaireRessourceQuantite.get(i), x , y+i*squareSize/2 );
    	}
    }
}
