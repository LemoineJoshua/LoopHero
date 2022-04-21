package theGame;

import java.awt.Color;

import fr.umlv.zen5.Application;

public class Main {
	
	/**
	 * Main function of the Game
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		
		Game game = new Game();
		
		Application.run(Color.black, game::Run);
	}

}
