package board;

import util.MoveSymbols;

public class MoveParser {
	private String[] move;

	private enum Position {
		ATTACKER,
		FROM,
		MOVE_TYPE,
		DEFENDER,
		TO;
	}

	public void parse(final String move) {
		this.move = move.split(MoveSymbols.SEPARATOR.getSymbol());
	}

	public boolean isMoveType(MoveSymbols sym) {
		return this.move[Position.MOVE_TYPE.ordinal()].contains(sym.getSymbol());	
	}

	public String getAttacker() {
		return this.move[Position.ATTACKER.ordinal()];
	}

	public String getDefender() {
		return this.move[Position.DEFENDER.ordinal()];
	}

	public String getStartingCoord() {
		return this.move[Position.FROM.ordinal()];
	}

	public String getArrivalCoord() {
		return this.move[Position.TO.ordinal()];
	}

}
