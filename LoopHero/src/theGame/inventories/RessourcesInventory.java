package theGame.inventories;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class RessourcesInventory implements Serializable{
    private final ArrayList<String> inventaireRessourceName;
	private final ArrayList<Integer> inventaireRessourceQuantity;
	private int score;
	
	
	/**
	 * RessourcesInventory constructor
	 * Initialize all ressources and put their quantity to 0
	 */
	public RessourcesInventory() {
		this.inventaireRessourceName = new ArrayList<String>();
        initName();
		this.inventaireRessourceQuantity = new ArrayList<Integer>();
		initQuantity();
		this.score = 0;
        }
	

    /**
     * Initialize all ressources
     */
    public void initName(){        
    	inventaireRessourceName.add("Branches");
    	inventaireRessourceName.add("Wood");
    	inventaireRessourceName.add("Pebbles");
    	inventaireRessourceName.add("Rock");
    	inventaireRessourceName.add("ScrapMetal");
    	inventaireRessourceName.add("Metal");
    	inventaireRessourceName.add("Ration");
    	inventaireRessourceName.add("FoodSupply");
    	inventaireRessourceName.add("MemoryFragment");
    	inventaireRessourceName.add("MemoryBook");
    	inventaireRessourceName.add("NoticeableChange");
    	inventaireRessourceName.add("Metamorphosis");
    	inventaireRessourceName.add("Craft Fragment");
    	inventaireRessourceName.add("Shapeless Mass");
    	inventaireRessourceName.add("Living Fabric");
    	inventaireRessourceName.add("PitifulRemains");
    }

    /**
     * Put the quantity of each ressources to 0
     */
    public void initQuantity(){
        for (int i=0; i<16; i++){
        	inventaireRessourceQuantity.add(0);
        }
    }

    /**
     * Add ressources to the inventory
     * 
     * @param name : the name of the added ressource 
     * @param quantity : the quantity to add
     */
    public void addRessources(String name, int quantity){
    	Objects.requireNonNull(name);
        int indexRessource=inventaireRessourceName.indexOf(name);
        inventaireRessourceQuantity.set(indexRessource,inventaireRessourceQuantity.get(indexRessource)+quantity);   
        int point = 0;
        switch (name) {
	        case "Branches" : 
	    		point = 3;
	    		break;
	    	case "Wood" :
	    		point = 25;
	    		break;
	    	case "Pebbles":
	    		point = 3;
	    		break;
	    	case "Rock":
	    		point = 25;
	    		break;
	    	case "ScrapMetal":
	    		point = 3;
	    		break;
	    	case "Metal":
	    		point = 3;
	    		break;
	    	case "Ration":
	    		point = 3;
	    		break;
	    	case "FoodSupply":
	    		point = 25;
	    		break;
	    	case "MemoryFragment":
	    		point = 3;
	    		break;
	    	case "MemoryBook":
	    		point = 25;
	    		break;
        	case "NoticeableChange":
        		point = 5;
        		break;
        	case "Metamorphosis":
        		point = 50;
        		break;
        	case "Shapeless Mass":
        		point = 15;
        		break;
        	case "Craft Fragment":
        		point = 10;
        		break;
        	case "Living Fabric":
        		point = 15;
        		break;
        	case "PitifulRemains":
        		point = 10;
        		break;
        }
        score += (point*quantity);
       }

	
    /**
     * Draw the ressource list 
     * 
     * @param x : the beginning x coord
     * @param y : the beginning y coord
     * @param graphics Drawing Objects
     * @param squareSize The reference size of a square of the board 
     */
    public void drawRessources(int x, int y, Graphics graphics, int squareSize, int fontSize) {
    	graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, fontSize));		
    	for(int i = 0;i<16;i++) {
    		graphics.drawString(inventaireRessourceName.get(i)+" : "+ inventaireRessourceQuantity.get(i), x , y+i*squareSize/2 );
    	}
    	graphics.drawString("Score Total : "+ score, x , y+18*squareSize/2 );
    }
    
    /**
     * score Accessor
     * 
     * @return the current score
     */
    public int score() {
    	return score;
    }
}
