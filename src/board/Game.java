package board;

import java.util.Map;

import util.Color;

/* TODO ????? Load the entire game o the next o previous moves only ????? */
public class Game {
	private final Map<Integer, Turn> game;

	public Game(Map<Integer, Turn> game) {
		this.game = game;
	}

	public String getPrevMove() {
		return "";
	}

	public String getNextMove() {
		return game.get(0).getMove(Color.WHITE); 
	}
	
}
