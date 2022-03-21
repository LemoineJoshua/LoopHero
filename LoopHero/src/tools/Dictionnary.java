package tools;

import java.util.ArrayList;
import theGame.entities.Monster;

public class Dictionnary {
	private final ArrayList<String> name;
	private final ArrayList<Monster> monster;
	
	public Dictionnary() {
		this.name = new ArrayList<String>();
		this.monster = new ArrayList<Monster>();
	}
	
	public Monster get(String searchedName) {
		return monster.get(name.indexOf(searchedName));
	}
	
	public void add(String newName,Monster newMonster) {
		name.add(newName);
		monster.add(newMonster);
	}
	
}
