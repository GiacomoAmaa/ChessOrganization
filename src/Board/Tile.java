package Board;

import java.util.Optional;

import Pieces.Piece;
import util.Color;

public class Tile {

	private final Color color;
	private Optional<Piece> piece;

	public Tile(final Color color, final Optional<Piece> piece) {
		this.color = color;
		this.piece = piece;
	}

	public boolean isFree() {
		return this.piece.isEmpty();
	}

	public Optional<Piece> getPiece() {
		return piece;
	}

	public void moveInPiece(final Piece piece) {
		this.piece = Optional.of(piece);
	}

	public void moveOutPiece() {
		this.piece = Optional.empty();
	}

	public Color getColor() {
		return color;
	}

}
