package theGame.entities;

import java.util.ArrayList;

public class Spider extends AbstractMonster{

	public Spider() {
		super(8,3.1,0.0,0.0,0.0,0.10,0.0,1,(float)0.55,new ArrayList<String>(),"pictures/Entities/spider.png", "pictures/Entities/spiderFight.png");
		generateLootList();
		
	}
	
	private void generateLootList(){
		drop.add("Living Fabric");
	}
	
	@Override
	public void vampireNearby() {
		stats.put("vampirism",stats.get("vampirism")+0.1);
	}

}
