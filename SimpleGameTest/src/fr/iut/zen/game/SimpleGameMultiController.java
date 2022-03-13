package fr.iut.zen.game;

import java.awt.Color;
import java.awt.geom.Point2D;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;

public class SimpleGameMultiController {
	private final SimpleGameData data = new SimpleGameData(5, 8);
	private final SimpleGameView view = SimpleGameView.initGameGraphics(200, 50, 600, data);
	private final TimeData timeData = new TimeData();
	private final static int USER_ACTION_DELAY = 200; // attention, ne doit pas dépasser BOB_DELAY

	private static void printScreenInfo(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();
		System.out.println("size of the screen (%.0f x %.0f)".formatted(width, height));
	}

	private void doKeyAction(ApplicationContext context, Event event) {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		case S -> timeData.stop();
		case D -> timeData.start();
		default -> System.out.println("Touche inactive : " + event.getKey());
		}
	}

	private void doMouseAction(ApplicationContext context, Event event) {
		if (!data.hasASelectedCell()) { // no cell is selected
			Point2D.Float location = event.getLocation();
			if (view.checkXY(location)) {
				data.selectCell(view.lineFromY(location.y), view.columnFromX(location.x));
			}
		} else {
			data.unselect();
		}
	}

	private void doBobActionAndDraw(ApplicationContext context) {
		if (timeData.elapsedBob() >= TimeData.BOB_DELAY) {
			data.moveBob();
			view.draw(context, data, timeData);
			timeData.resetElapsedBob();
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void doEventActionAndDraw(ApplicationContext context) {
		Event event = context.pollOrWaitEvent(USER_ACTION_DELAY);
		if (event == null) { // no event
			return;
		}

		switch (event.getAction()) {
		case KEY_PRESSED:
			doKeyAction(context, event);
			break;
		case POINTER_DOWN:
			if (timeData.stopped()) {
				doMouseAction(context, event);
			}
			break;
		}
		view.draw(context, data, timeData);
	}

	private void simpleGame(ApplicationContext context) { // le type de méthode que prend run() en paramètre
		printScreenInfo(context);

		data.setRandomMatrix();
		view.draw(context, data, timeData);

		while (true) {
			doBobActionAndDraw(context);
			doEventActionAndDraw(context);
		}
	}

	public static void main(String[] args) {
		// pour changer de jeu, remplacer stupidGame par le nom de la méthode de jeu
		// (elle doit avoir extactement la même en-tête).

		SimpleGameMultiController controller = new SimpleGameMultiController();

		Application.run(Color.LIGHT_GRAY, controller::simpleGame); // attention, utilisation d'une lambda.
		System.out.println("ne doit pas s'afficher");
	}
}
