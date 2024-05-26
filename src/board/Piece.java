package board;

import util.Color;
import util.PieceType;

public class Piece {
	private final Color color;
	private final PieceType type;
	
	public Piece(Color color, PieceType type) {
		this.color = color;
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public PieceType getType() {
		return type;
	}
}
