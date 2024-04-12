package util;

public enum MoveSymbols {
	SEPARATOR(":"),
	CAPTURE("x"),
	PROMOTION("*"),
	CHECK("+"),
	C_MATE("#"),
	CASTLING("O-O"),
	DRAW("?"),
	STALEMATE("%"),
	CONCEED("!");

	private String symbol;

	MoveSymbols(String s) {
		this.symbol = s;
	}

	@Override
	public String toString() {
		return this.symbol;
	}

}
