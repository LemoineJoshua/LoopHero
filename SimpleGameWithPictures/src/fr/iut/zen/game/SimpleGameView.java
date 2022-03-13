package fr.iut.zen.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public record SimpleGameView(int xOrigin, int yOrigin, int length, int width, int squareSize) implements GameView {

	public static SimpleGameView initGameGraphics(int xOrigin, int yOrigin, int length, SimpleGameData data) {
		int squareSize = (int) (length * 1.0 / data.nbLines());
		return new SimpleGameView(xOrigin, yOrigin, length, data.nbColumns() * squareSize, squareSize);
	}

	public boolean checkXY(Point2D.Float location) {
		return location.x >= xOrigin && location.x < width + xOrigin && location.y >= yOrigin
				&& location.y < length + yOrigin;
	}

	private int indexFromReaCoord(float coord, int origin) {
		return (int) ((coord - origin) / squareSize);
	}

	/**
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 * 
	 * @param y a float y-coordinate
	 * @return the index of the corresponding line.
	 */
	public int lineFromY(float y) {
		return indexFromReaCoord(y, yOrigin);
	}

	/**
	 * Transforms a real x-coordinate into the index of the corresponding column.
	 * 
	 * @param x a float x-coordinate
	 * @return the index of the corresponding column.
	 */
	public int columnFromX(float x) {
		return indexFromReaCoord(x, xOrigin);
	}

	private float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}

	private float xFromColumn(int column) {
		return realCoordFromIndex(column, xOrigin);
	}

	private float yFromLine(int line) {
		return realCoordFromIndex(line, yOrigin);
	}

	////////////////////////////// drawing methods //////////////////////////////
	private void drawSelectedCell(Graphics2D graphics, int line, int column) {
		float x = xFromColumn(column);
		float y = yFromLine(line);
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(x, y, squareSize, squareSize));
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(x + 2, y + 2, squareSize - 4, squareSize - 4));
	}

	private void drawBob(Graphics2D graphics, GridPosition position) {
		float x = xFromColumn(position.column());
		float y = yFromLine(position.line());
		graphics.setColor(Color.BLACK);
		graphics.fill(new Ellipse2D.Float(x + squareSize / 4, y + squareSize / 4, squareSize / 2, squareSize / 2));
	}

	private void drawImage(Graphics2D graphics, int line, int column, Path path) {
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(squareSize / (double) img.getWidth(), squareSize / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, xOrigin + column * squareSize, yOrigin + line * squareSize);
		} catch (IOException e) {
			throw new RuntimeException("problème d'affichage : " + path.getFileName());
		}
	}

	private void drawGrid(Graphics2D graphics, int nbLines, int nbColumns) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, width, length));

		graphics.setColor(Color.WHITE);
		for (int i = 0; i <= nbLines; i++) {
			int line = yOrigin + i * squareSize;
			graphics.draw(new Line2D.Float(xOrigin, line, xOrigin + width, line));
		}

		for (int i = 0; i <= nbColumns; i++) {
			int column = xOrigin + i * squareSize;
			graphics.draw(new Line2D.Float(column, yOrigin, column, yOrigin + length));
		}
	}

	private void drawBar(Graphics2D graphics, int width, double timeFraction) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fill(new Rectangle2D.Double(xOrigin, yOrigin - 20, width, 10));
		graphics.setColor(Color.GREEN);
		graphics.fill(new Rectangle2D.Double(xOrigin, yOrigin - 20, width * timeFraction, 10));
		graphics.setColor(Color.BLACK);
		graphics.draw(new Rectangle2D.Double(xOrigin, yOrigin - 20, width, 10));
	}
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 */
	@Override
	public void draw(Graphics2D graphics, SimpleGameData data, TimeData timeData) {
		drawBar(graphics, data.nbColumns() * squareSize, timeData.timeFraction());

		// dessin d'une grille
		drawGrid(graphics, data.nbLines(), data.nbColumns());

		// dessin de la cellule selectionnée
		GridPosition position = data.getSelected();
		if (position != null) {
			drawSelectedCell(graphics, position.line(), position.column());
		}

		drawBob(graphics, data.bob());

		// ajout d'une image à une position donnée
		String pictureName = "pictures/panda.png";
		Path pandaPath = Path.of(pictureName);
		drawImage(graphics, 2, 4, pandaPath);
	}

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param x        the float x-coordinate of the cell.
	 * @param y        the float y-coordinate of the cell.
	 */
	@Override
	public void drawOnlyOneCell(Graphics2D graphics, SimpleGameData data, int x, int y) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(x, y, 10, 10));
	}
}
