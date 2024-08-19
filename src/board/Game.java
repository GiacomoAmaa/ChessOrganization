package board;

import java.util.ArrayList;
import java.util.List;

import GUI.App;
import util.Color;
import util.MoveSymbols;
import util.Pair;
import util.PieceType;
import util.Sound;

public class Game {
	private final Board board;
	private List<Pair<String,String>> game;
	private final MoveParser move;

	private boolean isFinished;
	private int currTurn;
	private Color player;

	public Game(List<Pair<String,String>> game) {
		this.board = new Board();
		this.move = new MoveParser();
		this.game = game;
		this.isFinished = false;
		this.currTurn = 0;
		this.player = Color.WHITE;
	}

	public Game() {
		this.board = new Board();
		this.move = new MoveParser();
		this.game = new ArrayList<Pair<String, String>>();
		this.isFinished = false;
		this.currTurn = 0;
		this.player = Color.WHITE;
	}

	public void makePrevMove() {
		if (currTurn == 0 && this.player.isWhite()) {
			// it is the first move do nothing
			return;
		} else {
			//  return to previous move state
			Color opponent = this.player;
			this.currTurn = this.player.isWhite() ? currTurn - 1 : currTurn ;
			this.player = this.player.isWhite() ? Color.BLACK: Color.WHITE ;

			this.move.parse(this.player.isWhite() ? 
					this.game.get(currTurn).getX() : this.game.get(currTurn).getY());
			
			if (this.move.isMoveType(MoveSymbols.CASTLING)) {
				App.SOUND.play(Sound.CASTLE);
				/* castling message case start:stop:O-O:start:stop */
				this.board.getTile(this.move.getStartingCoord()).moveOutPiece(); // removes the king from arrival position
				this.board.getTile(this.move.getArrivalCoord()).moveOutPiece(); // removes the rook from arrival position
				// moves the king in initial position
				this.board.getTile(this.move.getAttacker())
					.moveInPiece(new Piece(player, PieceType.KING));
				// moves the rook in initial position
				this.board.getTile(this.move.getDefender())
					.moveInPiece(new Piece(player, PieceType.ROOK));
			} else {
				App.SOUND.play(Sound.MOVE);
				// if any piece was captured needs to be restored 
				if(this.move.isMoveType(MoveSymbols.CAPTURE)) {
					this.board.getTile(this.move.getArrivalCoord()).moveInPiece(new Piece(opponent,
							PieceType.getPieceTypeFromSymbol(this.move.getDefender().charAt(0))));
				} else {
					// just remove attacker from arrival position
					this.board.getTile(this.move.getArrivalCoord()).moveOutPiece();
				}
				// get the attacker back to starting position, if the move was a promotion
				// the attacker should be demoted to pawn
				this.board.getTile(this.move.getStartingCoord()).moveInPiece(new Piece(player,
						this.move.isMoveType(MoveSymbols.PROMOTION) ? PieceType.PAWN :
							PieceType.getPieceTypeFromSymbol(this.move.getAttacker().charAt(0))));
			}
			this.isFinished = false;
		}
	}

	public void makeNextMove() {
		if (!isFinished) {
			this.move.parse(this.player.isWhite() ? 
					this.game.get(currTurn).getX() : this.game.get(currTurn).getY());

			if(this.move.isMoveType(MoveSymbols.CONCEDE) ||
					this.move.isMoveType(MoveSymbols.DRAW)) {
				this.isFinished = true;
				return;
			}

			if (this.move.isMoveType(MoveSymbols.CASTLING)) {
				/* castling message case start:stop:O-O:start:stop */
				// moves the king from starting position
				this.board.getTile(this.move.getAttacker()).moveOutPiece();
				// moves the rook from starting position
				this.board.getTile(this.move.getDefender()).moveOutPiece(); 
				//moves the king in final position
				this.board.getTile(this.move.getStartingCoord()).moveInPiece(new Piece(player, PieceType.KING));
				//moves the rook in final position
				this.board.getTile(this.move.getArrivalCoord()).moveInPiece(new Piece(player, PieceType.ROOK));
			} else {
				this.board.getTile(this.move.getStartingCoord()).moveOutPiece();
				this.board.getTile(this.move.getArrivalCoord()).moveInPiece(new Piece(player,
						PieceType.getPieceTypeFromSymbol(this.move.getAttacker().charAt(0))));
			}

			if (this.move.isMoveType(MoveSymbols.CHECKMATE) ||
					this.move.isMoveType(MoveSymbols.STALEMATE)) {
				this.isFinished = true;
			}

			this.currTurn = this.player.isBlack() ? currTurn + 1 : currTurn;
			this.player = this.player.isBlack() ? Color.WHITE : Color.BLACK;

			//Sound effects
			if(this.move.isMoveType(MoveSymbols.CHECK) ||
					this.move.isMoveType(MoveSymbols.CHECKMATE)) {
				App.SOUND.play(Sound.CHECK);
			} else if(this.move.isMoveType(MoveSymbols.CASTLING)) {
				App.SOUND.play(Sound.CASTLE);
			} else if(this.move.isMoveType(MoveSymbols.CAPTURE)) {
				App.SOUND.play(Sound.CAPTURE);
			} else {
				App.SOUND.play(Sound.MOVE);
			}
		}

	}

	public void uploadMove(String move) {
		if(this.player.isWhite()) {
			this.game.add(new Pair<>(move,""));
		} else {
			this.game.get(currTurn).setY(move);
		}
		this.makeNextMove();
	}

	public void undoMove() {
		this.makePrevMove();
		if(this.player.isWhite()) {
			this.game.remove(currTurn);
		} else {
			this.game.get(currTurn).setY("");
		}
	}

	public Board getPosition() {
		return board;
	}

	public List<Pair<String, String>> getMoves() {
		return game;
	}
}
