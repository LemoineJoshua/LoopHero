package theGame.tiles;

import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.entities.Monster;
import theGame.entities.StatsEntites;

public class Grove extends AbstractRoad {
	private final int position;
	private int day = 0;
	private final Monster ratWolf;
	
	/**
	 * Constructeur du Grove,
	 * Initialise ses monstres, spawnables,
	 * et récupère sa position sur le chemin
	 * 
	 * @param position position sur le chemin
	 * @param aliveMonster Monstres sur la case avant le placage du Grove
	 */
	public Grove(int position,ArrayList<Monster> aliveMonster) {
		super("pictures/Tiles/grove.png", aliveMonster);
		ArrayList<String> drop = new ArrayList<>();
		drop.add("Living Fabric");
		this.ratWolf= new Monster(new StatsEntites((long)16,3.6,0.0,0.0,0.05,0.10,0.0),(float)0.05,(float)0.6,drop,"pictures/Entities/ratWolf.png");
		this.position = position;
	} 
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	
	/**
	 *Fait apparaitre un rat-loup sur le Grove ou sur une case adjacente tout les deux jours
	 */
	@Override
	public void dailyEffect(Board board) {
		if(day%2==0) {
			double test = Math.random();
			
			AbstractRoad upperTile=null;
			AbstractRoad lowerTile=null;
			if(position<33){
				upperTile = (AbstractRoad) board.boardMatrix()[board.coordList()[position+1].y()][board.coordList()[position+1].x()];
			}
			if(position>1) {
				lowerTile = (AbstractRoad) board.boardMatrix()[board.coordList()[position-1].y()][board.coordList()[position-1].x()];
			}
			
			if(test>0.6 && lowerTile!=null && lowerTile.aliveMonster().isEmpty()){ 
				lowerTile.addMob(ratWolf);
			}else if(test>0.3 && upperTile!=null && upperTile.aliveMonster().isEmpty()) {
				upperTile.addMob(ratWolf);
			}else if(this.aliveMonster().isEmpty()) {
				this.addMob(ratWolf);
			}

		}
		day+=1;
	}
	
	/**
	 *Ajoute une branche à l'inventaire du héro à chaque fois qu'il entre dedans
	 */
	@Override
	public void enteringEffect(GameData gameData) {
		gameData.ressourcesInventory().addRessources("Branches",1 );
	}

}
