package fr.iut.zen.game;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public record Cell(Color color, int value) {

	private static final Random random = new Random();
	public static final List<Color> colors = List.of(Color.RED, Color.BLUE);
	public static final int valueMax = 5;

	public Cell {
		if (!colors.contains(color)) {
			throw new IllegalArgumentException("color not in " + colors);
		}
	}

	public Cell(int i, int value) {
		this(colors.get(Objects.checkIndex(i, colors.size())), Objects.checkIndex(value, valueMax + 1));
	}

	public static Cell randomGameCell() {
		return new Cell(random.nextInt(colors.size()), random.nextInt(valueMax + 1));
	}
}
