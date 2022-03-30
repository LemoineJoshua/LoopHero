package theGame.inventories;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class RessourcesInventory {
    private final ArrayList<String> inventaireRessourceNom;
	private final ArrayList<Integer> inventaireRessourceQuantite;
	
	
	public RessourcesInventory() {
		this.inventaireRessourceNom = new ArrayList<String>();
        initNom();
		this.inventaireRessourceQuantite = new ArrayList<Integer>();
        initQuantite();
        }
	

    public void initNom(){        
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

    public void initQuantite(){
        for (int i=0; i<15; i++){
            inventaireRessourceQuantite.add(0);
        }
    }

    public void addRessources(String nom, int quantite){
        int indexRessource=inventaireRessourceNom.indexOf(nom);
        inventaireRessourceQuantite.set(indexRessource,inventaireRessourceQuantite.get(indexRessource)+quantite);   
    }
    
    @Override // pour le test
    public String toString() {
    	String entree = "";
    	for(int i = 0;i<15;i++) {
    		entree+="ressource: "+ inventaireRessourceNom.get(i)+" quantitée :"+inventaireRessourceQuantite.get(i);
    	}
    	return entree;
    }
    
    public void afficheRessource(int x, int y, Graphics graphics, int squareSize) {
    	graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));		
    	for(int i = 0;i<15;i++) {
    		graphics.drawString(inventaireRessourceNom.get(i)+" : "+ inventaireRessourceQuantite.get(i), x , y+i*squareSize/2 );
    	}
    }
}
