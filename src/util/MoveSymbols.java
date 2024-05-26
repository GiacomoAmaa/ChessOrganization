package util;

public enum MoveSymbols {
	SEPARATOR(":", "Separator"),
	CAPTURE("x", "Capture"),
	PROMOTION("*", "Promotion"),
	CHECK("+", "Check"),
	CHECKMATE("#", "Checkmate"),
	CASTLING("O-O", "Castling"),
	DRAW("?", "Draw"),
	STALEMATE("%", "Stalemate"),
	CONCEDE("!", "Concede"),
	UNKNOWN("--", "--");

	private final String symbol;
	private final String description;

	MoveSymbols(String symbol, String description) {
		this.symbol = symbol;
	    this.description = description;
	}

	public String getSymbol() {
	    return symbol;
	}

	@Override
	public String toString() {
	    return description;
	}
}
