package GUI.referee;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.BoardGUI;
import GUI.api.UserInterface;
import board.Board;
import board.Game;
import board.MoveParser;
import data.Referee;
import model.DBModel;
import util.Color;
import util.MoveSymbols;
import util.PieceType;

public class RegisterGameUI implements UserInterface {

	private JPanel panel = new JPanel(new GridLayout(0, 6));
	private final Game game = new Game();
	private final BoardGUI board = new BoardGUI(game);

	private static final String SEP = ":";
    private final JCheckBox captureCheck = new JCheckBox("Capture"),
    		castlingCheck = new JCheckBox("Castling"),
    		promotionCheck = new JCheckBox("Promotion"),
    		checkCheck  = new JCheckBox("Check"),
    		endgameCheck = new JCheckBox("Final Move");

    private final JLabel turnLabel = new JLabel(),
    		playerLabel = new JLabel();
    private final JComboBox<String> toField  = new JComboBox<>(Board.SQUARES),
    		fromField = new JComboBox<>(Board.SQUARES),
    		castleField = new JComboBox<>(new String[] {"--", "Long", "Short"});
    private final JComboBox<PieceType>
    		attackingPieceField = new JComboBox<>(),
    		capturedPieceField = new JComboBox<>(),
    		promotedPieceField = new JComboBox<>();
    private final JComboBox<MoveSymbols>
    		endgameField = new JComboBox<>();
    
    private final JButton submitMoveButton = new JButton("Submit Move"),
    		backButton = new JButton("Undo Move ");
    private int turn, gameId;
    private final MoveParser parser = new MoveParser();
    private String winner = "Pari";
    private Color player;

    public RegisterGameUI(final int gameId) {
    	List.of(PieceType.UNKNOWN, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN,PieceType.KING)
    		.forEach( x -> this.attackingPieceField.addItem(x));
    	List.of(PieceType.UNKNOWN, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP,	PieceType.ROOK, PieceType.QUEEN)
    		.forEach( x -> this.capturedPieceField.addItem(x));
    	List.of(PieceType.UNKNOWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN)
    		.forEach( x -> this.promotedPieceField.addItem(x));
    	List.of(MoveSymbols.UNKNOWN, MoveSymbols.CHECKMATE, MoveSymbols.STALEMATE, MoveSymbols.DRAW, MoveSymbols.CONCEDE)
		.forEach( x -> this.endgameField.addItem(x));
    	this.turn = 1;
    	this.gameId = gameId;
    	this.player = Color.WHITE;
    	setupForm();
    }

    private void setupForm() {
        this.panel.add(new JLabel("Piece Moved:"));	// Attacking piece
        this.panel.add(attackingPieceField);
        this.panel.add(new JLabel("From:", SwingConstants.CENTER));		// From field
        this.panel.add(fromField);
        this.panel.add(new JLabel("To:", SwingConstants.CENTER));			// To field
        this.panel.add(toField);
        this.panel.add(new JLabel("Move type:"));	// Move types	
        this.panel.add(checkCheck);					// Check
        this.panel.add(captureCheck);				// Capture
        this.panel.add(castlingCheck);				// Castling
        this.panel.add(promotionCheck); 			// Promotion
        this.panel.add(endgameCheck);        		//Last move
        this.panel.add(turnLabel);
        this.panel.add(playerLabel);
        this.panel.add(capturedPieceField);	
        this.panel.add(castleField);
        this.panel.add(promotedPieceField);
        this.panel.add(endgameField);
        this.panel.add(backButton);
        this.panel.add(submitMoveButton);

    	resetForm();

        // Add action listeners
        String error = "Invalid move: Castling cannot be combined with capture or promotion.";
        attackingPieceField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(attackingPieceField.getSelectedItem().equals(PieceType.PAWN)) {
					promotionCheck.setEnabled(true);
				} else {
					promotedPieceField.setEnabled(false);
					promotionCheck.setSelected(false);
					promotionCheck.setEnabled(false);
				}
			}
        });

        captureCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!castlingCheck.isSelected()) {
					capturedPieceField.setEnabled(captureCheck.isSelected());
				} else {
			        captureCheck.setSelected(false);
	                JOptionPane.showMessageDialog(null,error);
				}
			}
        });

        promotionCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!castlingCheck.isSelected()) {
			        promotedPieceField.setEnabled(promotionCheck.isSelected());
				} else {
			        promotionCheck.setSelected(false);
	                JOptionPane.showMessageDialog(null,error);
				}
			}
        });
        
        castlingCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!promotionCheck.isSelected() && !captureCheck.isSelected()) {
					castleField.setEnabled(castlingCheck.isSelected());
				} else {
					castlingCheck.setSelected(false);
	                JOptionPane.showMessageDialog(null,error);
				}
			}
        });

        endgameCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endgameField.setEnabled(endgameCheck.isSelected());
				submitMoveButton.setText(endgameCheck.isSelected() ?
						"Submit Game" : "Submit Move");
			}
        }); 
        		
        submitMoveButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		String move = packMoveInfo();
        		if (!move.isEmpty()) {
        			System.out.println(move);
        			turn = player.isWhite() ? turn : turn + 1;
        			player = player.isWhite() ? Color.BLACK : Color.WHITE;
        	        turnLabel.setText("Turn: " + turn);
        	        playerLabel.setText(player.isWhite()? "White to play" : "Black to play");
            		game.uploadMove(move);
            		board.displayMove();
            		if (endgameCheck.isSelected()) {
                		registerGame();
                		for (Component comp : panel.getComponents()) {
                            comp.setEnabled(false);
                        }
                        JOptionPane.showMessageDialog(null,
                                "You have now uploaded the game, reload the area or switch area.");
            		}
        		}
            }
        });

        backButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		if(player.isWhite() && turn == 1) {
    				JOptionPane.showMessageDialog(null,"No moves to undo");
    				return ;
        		} else {
            		turn = player.isWhite() ? turn - 1 : turn;
            		player = player.isWhite() ? Color.BLACK : Color.WHITE;
            	    turnLabel.setText("Turn: " + turn);
            	    playerLabel.setText(player.isWhite()? "White to play" : "Black to play");
            	    game.undoMove();
                	board.displayMove();
        		}
        	}
        });

    }

    private void registerGame() {
		int white = Referee.DAO.getWhite(DBModel.getConnection(), gameId);
		int black = Referee.DAO.getBlack(DBModel.getConnection(), gameId);
		var moves = game.getMoves();
		for(int i = 0 ; i < moves.size() ; i++) {
			var wMoveStr = moves.get(i).getX();
			var bMoveStr = moves.get(i).getY();
			parser.parse(wMoveStr);
			int whiteMove = parser.isMoveType(MoveSymbols.CASTLING) ? 
					registerCastle(white, wMoveStr) : registerRegularMove(white, wMoveStr);
			parser.parse(bMoveStr);
			Optional<Integer> blackMove;
			if (!bMoveStr.isEmpty()) {
				blackMove = Optional.of(parser.isMoveType(MoveSymbols.CASTLING) ? 
						registerCastle(black, bMoveStr) : registerRegularMove(black, bMoveStr));
			} else {
				blackMove = Optional.ofNullable(null);
			}
			Referee.DAO.registerTurn(DBModel.getConnection(), gameId, whiteMove, blackMove, i);
		}
		Referee.DAO.addWinner(DBModel.getConnection(), gameId, winner, white, black);
    }

    private int registerRegularMove(int playerId, String move) {
    	return Referee.DAO.registerMove(
				DBModel.getConnection(),
				playerId,
				gameId,
				parser.isMoveType(MoveSymbols.PROMOTION) ? PieceType.PAWN.getSymbol(): parser.getAttacker(),
				Optional.ofNullable(
						parser.isMoveType(MoveSymbols.PROMOTION) ? parser.getAttacker() : null),
				Optional.ofNullable(
						parser.isMoveType(MoveSymbols.CAPTURE) ? parser.getDefender() : null),
				parser.isMoveType(MoveSymbols.CHECKMATE),
				parser.getArrivalCol(),
				parser.getArrivalRow(),
				parser.getStartingCol(),
				parser.getStartingRow(),
				move);
    }

    private int registerCastle(int playerId, String move) {
    	return Referee.DAO.registerMove(
				DBModel.getConnection(),
				playerId,
				gameId,
				PieceType.KING.getSymbol(),
				Optional.ofNullable(null),
				Optional.ofNullable(null),
				parser.isMoveType(MoveSymbols.CHECKMATE),
				parser.getStartingCol(),
				parser.getStartingRow(),
				String.valueOf(parser.getAttacker().charAt(0)),
				Character.getNumericValue(parser.getAttacker().charAt(1)),
				move);
    }

    private String packMoveInfo() {
		String result, attacker, defender, from, to, type ;
		result = attacker = defender = from = to = type = "";

		if(castlingCheck.isSelected()) {
			if(!castleField.getSelectedItem().equals("--")) {
	    		if(endgameCheck.isSelected()) {
	    			if(endgameField.getSelectedItem().equals(MoveSymbols.UNKNOWN)) {
	    				JOptionPane.showMessageDialog(null,"Required field Missing");
	    				return "";
	    			} else {
	    				type += ((MoveSymbols)endgameField.getSelectedItem()).getSymbol();
	    				if (endgameField.getSelectedItem().equals(MoveSymbols.CHECKMATE)) {
	    					this.winner = this.player.isWhite() ? "Bianco" : "Nero";
	    				} else if (endgameField.getSelectedItem().equals(MoveSymbols.CONCEDE)) {
	    					this.winner = this.player.isWhite() ? "Nero" : "Bianco";
	    				}
	    			}
	    		}
				if(castleField.getSelectedItem().equals("Short")) {
					result = player.isWhite() ? "e1:g1:" + MoveSymbols.CASTLING.getSymbol() + ":h1:f1":
						"e8:g8:"+ type + MoveSymbols.CASTLING.getSymbol() +":h8:f8";
				} else if(castleField.getSelectedItem().equals("Long")) {
					result = player.isWhite() ? "e1:c1:" + type + MoveSymbols.CASTLING.getSymbol() + ":a1:d1":
						"e8:c8:"+ MoveSymbols.CASTLING.getSymbol() +":a8:d8";
				}
			} else {
					JOptionPane.showMessageDialog(null,"Required field Missing");
					return "";
			}

		} else if (
				!attackingPieceField.getSelectedItem().equals(PieceType.UNKNOWN) &&
				!fromField.getSelectedItem().equals("--") &&
    			!toField.getSelectedItem().equals("--")) {

    		if(promotionCheck.isSelected()) {
    			if(promotedPieceField.getSelectedItem().equals(PieceType.UNKNOWN)) {
    				JOptionPane.showMessageDialog(null,"Required field Missing");
    				return "";
    			} else {
    				type = MoveSymbols.PROMOTION.getSymbol();
    				attacker = ((PieceType) promotedPieceField.getSelectedItem()).getSymbol();
    			}
    		} else {
    			attacker = ((PieceType) attackingPieceField.getSelectedItem()).getSymbol();
    		}

    		if(captureCheck.isSelected()) {
    			if(capturedPieceField.getSelectedItem().equals(PieceType.UNKNOWN)) {
    				JOptionPane.showMessageDialog(null,"Required field Missing");
    				return "";
    			} else {
    				type += MoveSymbols.CAPTURE.getSymbol();
    				defender = ((PieceType) capturedPieceField.getSelectedItem()).getSymbol();
    			}
    		}

    		if(endgameCheck.isSelected()) {
    			if(endgameField.getSelectedItem().equals(MoveSymbols.UNKNOWN)) {
    				JOptionPane.showMessageDialog(null,"Required field Missing");
    				return "";
    			} else {
    				type += ((MoveSymbols)endgameField.getSelectedItem()).getSymbol();
    				if (endgameField.getSelectedItem().equals(MoveSymbols.CHECKMATE)) {
    					this.winner = this.player.isWhite() ? "Bianco" : "Nero";
    				} else if (endgameField.getSelectedItem().equals(MoveSymbols.CONCEDE)) {
    					this.winner = this.player.isWhite() ? "Nero" : "Bianco";
    				}
    			}
    		}

        	type = checkCheck.isSelected() ? type + MoveSymbols.CHECK.getSymbol() : type;
        	from = fromField.getSelectedItem().toString();
        	to = toField.getSelectedItem().toString();
        	result = attacker + SEP + from + SEP + type + SEP + defender + SEP + to;
    	} else {
			JOptionPane.showMessageDialog(null,"Required field Missing");
    		return "";
    	}

		if (!endgameCheck.isSelected()) {
			resetForm();
		}
    	return result;
    }

    private void resetForm() {
    	attackingPieceField.setSelectedItem(PieceType.UNKNOWN);
    	fromField.setSelectedItem("--");
    	toField.setSelectedItem("--");
    	capturedPieceField.setSelectedItem(PieceType.UNKNOWN);
        checkCheck.setSelected(false);
        captureCheck.setSelected(false);
        capturedPieceField.setEnabled(false);
        castleField.setSelectedItem("--");
        castlingCheck.setSelected(false);
        castleField.setEnabled(false);
        promotedPieceField.setSelectedItem(PieceType.UNKNOWN);
        promotionCheck.setSelected(false);
        promotionCheck.setEnabled(false);
        promotedPieceField.setEnabled(false);
        endgameField.setSelectedItem(MoveSymbols.UNKNOWN);
        endgameCheck.setSelected(false);
        endgameField.setEnabled(false);
        turnLabel.setText("Turn: " + turn);
        playerLabel.setText(player.isWhite()? "White to play" : "Black to play");
    }

	@Override
	public JPanel getUpperPanel() {
		return panel;
	}

	@Override
	public JPanel getLowerPanel() {
		return board.getUpperPanel();
	}
}
