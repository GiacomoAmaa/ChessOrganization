package util;

public enum PieceType {
    KING("King", 'K'),
    QUEEN("Queen", 'Q'),
    ROOK("Rook", 'R'),
    BISHOP("Bishop", 'B'),
    KNIGHT("Knight", 'N'),
    PAWN("Pawn", 'P'),
	UNKNOWN("--", '-');

    private final String name;
    private final char symbol;

    PieceType(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name;
    }

	public static PieceType getPieceTypeFromSymbol(final char sym) {
		switch(sym) {
		case 'N': return PieceType.KNIGHT;
		case 'K': return PieceType.KING;
		case 'Q': return PieceType.QUEEN;
		case 'R': return PieceType.ROOK;
		case 'B': return PieceType.BISHOP;
		default : return PieceType.PAWN;
		}
	}

}
