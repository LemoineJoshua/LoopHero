package theGame.entities;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import theGame.GameView;

public abstract class AbstractMonster extends AbstractEntities implements Serializable{
	
    private final float chanceSpawn;
    private final float chanceCard;
    protected final ArrayList<String> drop;
    private final String pathPicture;
    private final String pathPictureFight;
    private transient BufferedImage picture;
    private transient BufferedImage pictureFight;
    

    /**
	 * Abstract Entities Constructor
	 * 
	 * @param hp : The monster max hp and current hp at his creation
	 * @param strength : The monster strength
	 * @param defense : The monster defense
	 * @param counterAttack : The monster chance to counter attack
	 * @param regen : The monster regen
	 * @param evade : The monster chance to evade 
	 * @param vampirism : The monster vampirism percentage
     * @param chanceSpawn : The percentage of chance, the monster got to spawn each day
     * @param chanceCard : The percentage of chance, the monster got to drop a card
     * @param drop : The list of ressources, the monster can drop
     * @param picture : The path to the picture of the monster, when he is on the board
     * @param pictureFight : The path to the picture of the monster, when he is in fight
     */
    public AbstractMonster(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,float chanceSpawn,float chanceCard,ArrayList<String> drop,String picture, String pictureFight){
        super(hp,strength,defense,counterAttack,regen,evade,vampirism);
        this.chanceSpawn=chanceSpawn;
        this.chanceCard=chanceCard;
        this.drop=drop;
        this.pathPicture=picture;
        this.pathPictureFight=pictureFight;
        this.picture=GameView.stringToImage(pathPicture);
        this.pictureFight=GameView.stringToImage(pathPictureFight);
    }

    /**
     * Check if a mob spawn
     * 
     * @return true if the mob spawn0, else false
     */
    public boolean doSpawn(){
    	return chanceSpawn > Math.random();
    }
    
    /**
     * Check if mob drop a card or not
     * 
     * @return true if the mob drop a card, else false
     */
    public boolean dropCard() {
    	return chanceCard > Math.random();
    }
    
    /**
     * drop accessor
     * 
     * @return the drops of the monster
     */
    public ArrayList<String> drop(){
    	return drop;
    }
    
    /**
     * Picture accessor
     * 
	 * @return the picture 
	 */
	public BufferedImage picture() {
		if(picture==null) {
			this.picture=GameView.stringToImage(pathPicture);
		}
		return picture;
	}
	
	/**
	 * Picture Fight accessor
	 * 
	 * @return the picture in fight 
	 */
	public BufferedImage pictureFight() {
		if(pictureFight==null) {
			this.pictureFight=GameView.stringToImage(pathPictureFight);
		}
		return pictureFight;
	}

	/**
	 * Refresh the stats of the monster at the current loop.
	 * 
	 * @param loopNumber : The number of the current loop
	 */
	public void fightStats(int loopNumber){
		stats.put("strength",(double) Math.round(stats.get("strength")* loopNumber * 0.95 * (1+(loopNumber-1)*0.02)));
		stats.put("maxHp", (double) Math.round(stats.get("maxHp")* loopNumber * 0.95 * (1+(loopNumber-1)*0.02)));
		stats.put("hp", stats.get("maxHp"));
	}
	
	public boolean hasASoul() {
		return false;
	}
		
	public void vampireNearby() {}
	
    
	
	
}
