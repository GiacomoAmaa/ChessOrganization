package board;

import java.util.List;

import pieces.Piece;
import util.Color;
import util.MoveSymbols;
import util.Pair;
import util.PieceType;

public class Game {
	private final Board board;
	private final List<Pair<String,String>> game;
	private final MoveParser moveParser;
	
	private boolean isFinished;
	private int currTurn;
	private Color player;

	public Game(List<Pair<String,String>> game) {
		this.board = new Board();
		this.moveParser = new MoveParser();
		this.game = game;
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
			this.currTurn = this.player.isWhite() ? currTurn - 1 : currTurn ;
			Color opponent = this.player;
			this.player = this.player.isWhite() ? Color.BLACK: Color.WHITE ;
			
			if ( this.moveParser.isMoveType(MoveSymbols.CASTLING)) {
				/* castling message case start:stop:O-O:start:stop */
				this.board.getTile(this.moveParser.getStartingCoord()).moveOutPiece(); // removes the king from arrival position
				this.board.getTile(this.moveParser.getArrivalCoord()).moveOutPiece(); // removes the rook from arrival position
				// moves the king in initial position
				this.board.getTile(this.moveParser.getAttacker()).moveInPiece(new Piece(player, PieceType.KING));
				// moves the rook in initial position
				this.board.getTile(this.moveParser.getDefender()).moveInPiece(new Piece(player, PieceType.ROOK));
			} else {
				// if any piece was captured needs to be restored 
				if(this.moveParser.isMoveType(MoveSymbols.CAPTURE)) {
					this.board.getTile(this.moveParser.getArrivalCoord()).moveInPiece(new Piece(opponent,
							PieceType.getPieceTypeFromSymbol(this.moveParser.getDefender())));
				} else {
					// just remove attacker from arrival position
					this.board.getTile(this.moveParser.getArrivalCoord()).moveOutPiece();
				}
				// if there was a promotion should be restored the pawn
				PieceType attacker = this.moveParser.isMoveType(MoveSymbols.PROMOTION) ?
						PieceType.PAWN : PieceType.getPieceTypeFromSymbol(this.moveParser.getAttacker());
				// get the attacker back to starting position
				this.board.getTile(this.moveParser.getArrivalCoord()).moveInPiece(new Piece(player,attacker));
			}
			this.isFinished = false;
		}
	}

	public void makeNextMove() {
		if (!isFinished) {
			String move = this.player.isWhite() ? 
					this.game.get(currTurn).getX() : this.game.get(currTurn).getY();
			this.moveParser.parse(move);
			
			if(this.moveParser.isMoveType(MoveSymbols.CONCEED) ||
					this.moveParser.isMoveType(MoveSymbols.DRAW)) {
				this.isFinished = true;
				return;
			}
			 					 
			if ( this.moveParser.isMoveType(MoveSymbols.CASTLING)) {
				/* castling message case start:stop:O-O:start:stop */
				this.board.getTile(this.moveParser.getAttacker()).moveOutPiece(); // moves the king from starting position
				this.board.getTile(this.moveParser.getDefender()).moveOutPiece(); // moves the rook from starting position
				//moves the king in final position
				this.board.getTile(this.moveParser.getStartingCoord()).moveInPiece(new Piece(player, PieceType.KING));
				//moves the rook in final position
				this.board.getTile(this.moveParser.getArrivalCoord()).moveInPiece(new Piece(player, PieceType.ROOK));
			} else {
				this.board.getTile(this.moveParser.getStartingCoord()).moveOutPiece();
				this.board.getTile(this.moveParser.getArrivalCoord()).moveInPiece(new Piece(player,
						PieceType.getPieceTypeFromSymbol(this.moveParser.getAttacker())));
			}

			if (this.moveParser.isMoveType(MoveSymbols.C_MATE) ||
					this.moveParser.isMoveType(MoveSymbols.STALEMATE)) {
				this.isFinished = true;
			}

			this.currTurn = this.player.isBlack() ? currTurn + 1 : currTurn;
			this.player = this.player.isBlack() ? Color.WHITE : Color.BLACK;
		}

	}
	
}
