package board;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import pieces.Piece;
import util.Color;
import util.Pair;
import util.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private static final String[] COLUMNS = {"a","b","c","d","e","f","g","h"};
	private static final int NUM_TILES = 64;
	private static final int BOARD_DIM = 8;

	/* starting pieces positions */
	private static final Map<Set<String>, Piece> PIECES_POS = Map.of(
			Set.of("a2","b2","c2","d2","e2","f2","g2","h2"), new Piece(Color.WHITE, PieceType.PAWN),
			Set.of("a7","b7","c7","d7","e7","f7","g7","h7"), new Piece(Color.BLACK, PieceType.PAWN),
			Set.of("c1","f1"), new Piece(Color.WHITE, PieceType.BISHOP),
			Set.of("c8","f8"), new Piece(Color.BLACK, PieceType.BISHOP),
			Set.of("b1","g1"), new Piece(Color.WHITE, PieceType.KNIGHT),
			Set.of("b8","g8"), new Piece(Color.BLACK, PieceType.KNIGHT),
			Set.of("a1","h1"), new Piece(Color.WHITE, PieceType.ROOK),
			Set.of("a8","h8"), new Piece(Color.BLACK, PieceType.ROOK)
			);

	private static final Map<String, Piece> ROYALS_POS = Map.of(
			"d1", new Piece(Color.WHITE, PieceType.QUEEN),
			"d8", new Piece(Color.BLACK, PieceType.QUEEN),
			"e1", new Piece(Color.WHITE, PieceType.KING),
			"e8", new Piece(Color.BLACK, PieceType.KING)
			);

	private final List<Pair<String, Tile>> board;

	public Board() {
		this.board = new ArrayList<Pair<String, Tile>>();
		intializeBoard();
		initializePieces();
		initializeRoyals();
	}

	/**
	  * creates empty Board
	  */
	private void intializeBoard() {
		int rowIndex = BOARD_DIM;
		Color tileColor;
		for(int i = Board.NUM_TILES ; i > 0 ; i--) {
			tileColor = i % 2 == 0 ? Color.WHITE : Color.BLACK; // alternates colors on squares
			rowIndex = i % BOARD_DIM == 0 ? i / BOARD_DIM : rowIndex;
			String coordinate = COLUMNS[i % BOARD_DIM] + Integer.toString(rowIndex); 
			this.board.add(new Pair<String, Tile>(coordinate, new Tile(tileColor, Optional.empty())));
		}
	}

	 private void initializePieces() {
		for(Map.Entry<Set<String>, Piece> entry : PIECES_POS.entrySet()) {
			for(String coordinate: entry.getKey()) {
				this.board.stream().filter(x -> x.getX().equals(coordinate))
				.findFirst()
				.get().getY()
				.moveInPiece(entry.getValue());
			}
        }
	}

	private void initializeRoyals() {
		for(Map.Entry<String, Piece> entry : ROYALS_POS.entrySet()) {
			this.board.stream().filter(x -> x.getX().equals(entry.getKey()))
				.findFirst()
				.get().getY()
				.moveInPiece(entry.getValue());
		}
	}
	
}
