package theGame.tiles;

import java.io.Serializable;
import java.util.ArrayList;

import theGame.GameData;
import theGame.boardGame.Board;
import theGame.boardGame.Coord;
import theGame.entities.AbstractMonster;
import theGame.inventories.Item;

public class Village extends AbstractRoad implements Serializable{
	private int wheatFieldAround=0;
	private boolean questDelivered = false;
	private boolean questAchieved = false;
	

	/**
	 * The Village constructor
	 */
	public Village(Coord position, ArrayList<AbstractMonster> aliveMonster) {
		super("pictures/Tiles/village.png",aliveMonster,position);
	}
	
	/**
	 * Check if the cell is Empty, which mean we could place a card on it
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}
		
	/**
	 * Check if we can the oblivion card on this tile
	 * 
	 * @return true if we can use it, false else.
	 */
	@Override
	public boolean isOblivionable() {
		return true;
	}
	

	@Override
	public void enteringEffect(GameData gameData) {
		int totalHealing = (15*gameData.board().loop()) + (5*gameData.board().loop()*wheatFieldAround);
		gameData.board().hero().regenHPdaily(totalHealing);
		
		if (!questDelivered) {
			giveAQuest(gameData);
		}else {
			if (questAchieved) {
				Item item = Item.getAQuestItem(gameData.board().loop());
				gameData.itemInventory().add(item);
			}
			questAchieved = false;
			questDelivered= false;
			
		}
		
	}
	
	
	private void giveAQuest(GameData gameData) {
		Integer indexInLoop = gameData.board().getIndexInLoop(position.y(), position.x());
		ArrayList<AbstractTile> tileWithMonster = new ArrayList<>();
		for (Coord coord:gameData.board().coordList()) {
			AbstractRoad road = (AbstractRoad) gameData.board().boardMatrix()[coord.y()][coord.x()];
			if(!road.aliveMonster().isEmpty()) {
				tileWithMonster.add(road);
			}
		}
		if (tileWithMonster.size()>0) {
			AbstractRoad selectedTile = (AbstractRoad) tileWithMonster.get((int)( Math.random()*(tileWithMonster.size()-1)));
			AbstractMonster selectedMonster = (AbstractMonster) selectedTile.aliveMonster().get((int) (Math.random()*(selectedTile.aliveMonster().size())-1));
			
			selectedMonster.giveAQuest(indexInLoop);
			questDelivered = true;
		}
	}
	
	public void questMobDefeated() {
		questAchieved=true;
	}
	
	public void searchOvergrown(Board board) {
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		AbstractTile[][] matrix = board.boardMatrix();
		
		
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof OvergrownWheatField) {
					matrix[position.y()+coord.y()][position.x()+coord.x()]=new WheatFields(position,matrix,aliveMonster);
					wheatFieldAround++;
				}
			}
		}
	}
	
	public void removingEffect(Board board) {
		ArrayList<Coord> posibilities = new ArrayList<>();
		posibilities.add(new Coord(0,1));
		posibilities.add(new Coord(0,-1));
		posibilities.add(new Coord(1,0));
		posibilities.add(new Coord(-1,0));
		
		AbstractTile[][] matrix = board.boardMatrix();
		
		
		for(Coord coord : posibilities) {
			if((position.y()+coord.y()<12 && position.y()+coord.y()>=0) && (position.x()+coord.x()<21 && position.x()+coord.x()>=0)) {
				if(matrix[position.y()+coord.y()][position.x()+coord.x()] instanceof WheatFields) {
					AbstractRoad tile = (AbstractRoad) matrix[position.y()+coord.y()][position.x()+coord.x()];
					ArrayList<AbstractMonster> wheatFieldAliveMonster= tile.aliveMonster();
					matrix[position.y()+coord.y()][position.x()+coord.x()]=new OvergrownWheatField(wheatFieldAliveMonster,position);
				}
			}
		}	
	}
	
	
	public void wheatFieldNearby() {
		wheatFieldAround++;
	}
	
	public void wheatFieldOblivionned() {
		wheatFieldAround--;
	}
	

	
}
