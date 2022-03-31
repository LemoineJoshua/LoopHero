package theGame;

import java.awt.Color;

import fr.umlv.zen5.Application;

public class Main {
	
	/**
	 * Fonction main du jeu
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		
		Game game = new Game();
		
		Application.run(Color.black, game::Run);
	}

}
