package util;

public enum PieceType {
	PAWN("P"),
	KNIGHT("N"),
	BISHOP("B"),
	ROOK("R"),
	QUEEN("Q"),
	KING("K");

	private String symbol;

	PieceType(String s) {
		this.symbol = s;
	}

	@Override
	public String toString() {
		return this.symbol;
	}

	public static PieceType getPieceTypeFromSymbol(final String sym) {
		switch(sym) {
		case "N": return PieceType.KNIGHT;
		case "K": return PieceType.KING;
		case "Q": return PieceType.QUEEN;
		case "R": return PieceType.ROOK;
		case "B": return PieceType.BISHOP;
		default : return PieceType.PAWN;
		}
	}

}
