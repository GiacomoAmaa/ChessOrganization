package Board;

import java.util.Map;
import java.util.Collections;

public class Board {
	private final Map<String, Tile> board;

	public Board(Map<String, Tile> board) {
		this.board = board;
	}

	public Map<String, Tile> getBoard() {
		return Collections.unmodifiableMap(this.board);
	}
	
}
