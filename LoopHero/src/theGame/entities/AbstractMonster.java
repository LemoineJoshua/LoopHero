package theGame.entities;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import theGame.GameView;

public abstract class AbstractMonster extends AbstractEntities implements Serializable{
	
	private static final long serialVersionUID = 6897548213687074948L;
	private final float chanceSpawn;
    private final float chanceCard;
    protected final ArrayList<String> drop;
    private final String pathPicture;
    private final String pathPictureFight;
    private final String pathPictureFightAtt;
    private final String pathPictureFightDeath;
    private transient BufferedImage picture;
    private transient BufferedImage pictureFight;
    private transient BufferedImage pictureFightAtt;
    private transient BufferedImage pictureFightDeath;
    private Integer questVillagePosition;
    

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
     * @param pictureFightAtt : The path to the picture of the monster, when he attack in fight
     * @param pictureFightDeath : The path to the picture of the monster, when he is dead in fight
     * @param questVillagePosition : The index of the village, of which the monster is the quest
     */
    public AbstractMonster(double hp, double strength, double defense, double counterAttack, double regen,double evade,double vampirism,float chanceSpawn,float chanceCard,ArrayList<String> drop,String picture, String pictureFight, String pictureFightAtt, String pictureFightDeath, Integer questVillagePosition ){
        super(hp,strength,defense,counterAttack,regen,evade,vampirism);
        this.chanceSpawn=chanceSpawn;
        this.chanceCard=chanceCard;
        this.drop=Objects.requireNonNull(drop); 
        this.pathPicture=Objects.requireNonNull(picture);
        this.pathPictureFight=Objects.requireNonNull(pictureFight);
        this.pathPictureFightAtt=Objects.requireNonNull(pictureFightAtt);
        this.pathPictureFightDeath=Objects.requireNonNull(pictureFightDeath);
        this.picture=GameView.stringToImage(pathPicture);
        this.pictureFight=GameView.stringToImage(pathPictureFight);
        this.questVillagePosition = null;
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
	 * Picture Fight Of the Attack accessor
	 * 
	 * @return the picture in fight when entity attacks 
	 */
	
	public BufferedImage pictureFightAtt() {
		if(pictureFightAtt==null) {
			this.pictureFightAtt=GameView.stringToImage(pathPictureFightAtt);
		}
		return pictureFightAtt;
	}
	
	/**
	 * Picture Fight Of the Death accessor
	 * 
	 * @return the picture in fight  when entity is dead
	 */
	public BufferedImage pictureFightDeath() {
		if(pictureFightDeath==null) {
			this.pictureFightDeath=GameView.stringToImage(pathPictureFightDeath);
		}
		return pictureFightDeath;
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
	
	public void fightStatsQuest(int loopNumber) {
		stats.put("strength",(double) Math.round(stats.get("strength")* loopNumber * 0.95 * 2 * (1+(loopNumber-1)*0.02)));
		stats.put("maxHp", (double) Math.round(stats.get("maxHp")* loopNumber * 0.95 * 1.75 *(1+(loopNumber-1)*0.02)));
		stats.put("counterAttack",(double) stats.get("counterAttack") +0.15);
		stats.put("evade",(double) stats.get("evade") +0.15);
		stats.put("hp", stats.get("maxHp"));
	}
	
	/**
	 * Check if the monster got a soul
	 * 
	 * @return true if the monster got a soul, else false
	 */
	public boolean hasASoul() {
		return false;
	}
		
	/**
	 * Deal the effect if the monster is near a vampire mansion
	 */
	public void vampireNearby() {}
	
	/**
	 * Give the monster a quest
	 * 
	 * @param position : The index of the village tile in the loop
	 */
	public void giveAQuest(int position) {
		questVillagePosition = position;
	}
	
	/**
	 * Check if the monster is in a quest
	 * 
	 * @return true if the monster got a quest, else false
	 */
	public boolean gotAQuest() {
		return (questVillagePosition!=null);
	}
	
	/**
	 * questVillagePosition accessor
	 * 
	 * @return the questVillagePosiion
	 */
	public Integer questVillagePosition() {
		return questVillagePosition;
	}
}
