package theGame;

import java.awt.Color;

import fr.umlv.zen5.Application;

public class Main {
	
	public static void main(String[] args) {
		
		Game game = new Game();//On crée un objet Game
		
		Application.run(Color.black, game::Run);//On lance l'appli avec comme couleur de fond du noir et comme fonction de 'main' la fonction Run de Game
	}

}
