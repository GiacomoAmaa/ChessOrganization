package board;

import util.Color;

public class Turn {
	private final String whiteMove;
	private final String blackMove;

	public Turn(final String whiteMove, final String blackMove) {
		this.whiteMove = whiteMove;
		this.blackMove = blackMove;
	}

	public String getMove(final Color playerColor) {
		return playerColor.isWhite() ? this.whiteMove : this.blackMove ;
	}

}
