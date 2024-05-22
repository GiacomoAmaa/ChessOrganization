package board;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import util.Color;
import util.Pair;
import util.PieceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
	public static final String[] SQUARES = {"a1","a2","a3","a4","a5","a6","a7","a8",
			"b1","b2","b3","b4","b5","b6","b7","b8","c1","c2","c3","c4","c5","c6","c7","c8",
			"d1","d2","d3","d4","d5","d6","d7","d8","e1","e2","e3","e4","e5","e6","e7","e8",
			"f1","f2","f3","f4","f5","f6","f7","f8","g1","g2","g3","g4","g5","g6","g7","g8",
			"h1","h2","h3","h4","h5","h6","h7","h8",};
	public static final String[] COLUMNS = {"a","h","g","f","e","d","c","b"};
	public static final int NUM_TILES = 64;
	public static final int BOARD_DIM = 8;

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

	/* TODO Sta schifezza è per stampare tranquillamente la board, ma
	 *  una map sarebbe meglio a livello logico */
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
			rowIndex = i % BOARD_DIM == 0 ? i / BOARD_DIM : rowIndex;
			if (rowIndex % 2 == 0) {
				tileColor = i % 2 == 0 ? Color.WHITE : Color.BLACK; // alternates colors on squares
			} else {
				tileColor = i % 2 == 0 ? Color.BLACK : Color.WHITE; // alternates colors on squares
			}
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
	
	public Tile getTile(final String coord) {
		return this.board.stream()
				.filter(x -> x.getX().equals(coord))
				.findFirst()
				.get().getY();
	}

	public List<Pair<String, Tile>> getBoard() {
		return Collections.unmodifiableList(this.board);
	}

}
