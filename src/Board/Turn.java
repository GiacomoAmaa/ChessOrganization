package Board;

import util.Color;

public class Turn {
	private final String whiteMove;
	private final String blackMove;

	public Turn(final String whiteMove, final String blackMove) {
		this.whiteMove = whiteMove;
		this.blackMove = blackMove;
	}

	public String getMove(final Color player) {
		return player.equals(Color.WHITE) ? this.whiteMove : this.blackMove ;
	}

}
