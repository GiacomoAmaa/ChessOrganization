package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import board.Board;
import util.Color;
import util.MoveSymbols;
import util.PieceType;

public class RegisterGameUI {

	private final JPanel panel = new JPanel(new GridLayout(0, 2));
	private static final String SEP = ":";
    private static final JCheckBox captureCheck = new JCheckBox("Capture"),
    		castlingCheck = new JCheckBox("Castling"),
    		promotionCheck = new JCheckBox("Promotion"),
    		checkCheck  = new JCheckBox("Check"),
    		endgameCheck = new JCheckBox("Final Move");

    private static final JComboBox<String> toField  = new JComboBox<>(Board.SQUARES),
    		fromField = new JComboBox<>(Board.SQUARES),
    		castleField = new JComboBox<>(new String[] {"--", "Long", "Short"});
    private static final JComboBox<PieceType>
    		attackingPieceField = new JComboBox<>(),
    		capturedPieceField = new JComboBox<>(),
    		promotedPieceField = new JComboBox<>();
    private static final JComboBox<MoveSymbols>
    		endgameField = new JComboBox<>();
    
    private JButton submitMoveButton;
    //private int turn = 1;  //TODO da caricare dinamicamente dal db in base a fin dove è stata registrata la partita
    private Color player = Color.WHITE;

    public RegisterGameUI() {
    	List.of(PieceType.UNKNOWN, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN,PieceType.KING)
    		.forEach( x -> RegisterGameUI.attackingPieceField.addItem(x));
    	List.of(PieceType.UNKNOWN, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP,	PieceType.ROOK, PieceType.QUEEN)
    		.forEach( x -> RegisterGameUI.capturedPieceField.addItem(x));
    	List.of(PieceType.UNKNOWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN)
    		.forEach( x -> RegisterGameUI.promotedPieceField.addItem(x));
    	List.of(MoveSymbols.UNKNOWN, MoveSymbols.CHECKMATE, MoveSymbols.STALEMATE, MoveSymbols.DRAW, MoveSymbols.CONCEDE)
		.forEach( x -> RegisterGameUI.endgameField.addItem(x));
    	setupForm();
    }

    private void setupForm() {
        this.panel.add(new JLabel("Piece Moved:"));	// Attacking piece
        this.panel.add(attackingPieceField);
        this.panel.add(new JLabel("From:"));		// From field
        this.panel.add(fromField);
        this.panel.add(new JLabel("To:"));			// To field
        this.panel.add(toField);
        this.panel.add(new JLabel("Move type:"));	// Move types
        this.panel.add(checkCheck);					// Check		
        this.panel.add(captureCheck);				// Capture
        this.panel.add(capturedPieceField);			// Castling
        this.panel.add(castlingCheck);
        this.panel.add(castleField);
        this.panel.add(promotionCheck); 			// Promotion
        this.panel.add(promotedPieceField);
        this.panel.add(endgameCheck);        		//Last move
        this.panel.add(endgameField);        		// Submit move button
        submitMoveButton = new JButton("Submit Move");
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

        endgameCheck.addActionListener(e -> endgameField.setEnabled(endgameCheck.isSelected()));

        submitMoveButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		System.out.println(packMoveInfo());
            }
        });
    }

    private String packMoveInfo() {
		String result, attacker, defender, from, to, type ;
		result = attacker = defender = from = to = type = "";

		if(castlingCheck.isSelected()) {
			if(castleField.getSelectedItem().equals("Short")) {
				result = player.isWhite() ? "e1:g1:" + MoveSymbols.CASTLING.getSymbol() + ":h1:f1":
					"e8:g8:"+ MoveSymbols.CASTLING.getSymbol() +":h8:f8";
			} else if(castleField.getSelectedItem().equals("Long")) {
				result = player.isWhite() ? "e1:c1:" + MoveSymbols.CASTLING.getSymbol() + ":a1:d1":
					"e8:c8:"+ MoveSymbols.CASTLING.getSymbol() +":a8:d8";
			} else {
				JOptionPane.showMessageDialog(null,"Required field Missing");
				return "";
			}
		} else if (
				!attackingPieceField.getSelectedItem().equals(PieceType.UNKNOWN) &&
				!fromField.getSelectedItem().equals("") &&
    			!toField.getSelectedItem().equals("")) {

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
    			}
    		}

        	type = checkCheck.isSelected() ? type + MoveSymbols.CHECK.getSymbol() : type;
        	from = fromField.getSelectedItem().toString();
        	to = toField.getSelectedItem().toString();
        	result = attacker + SEP + from + SEP + type + SEP + to + SEP + defender;
    	} else {
    		JOptionPane.showMessageDialog(null,"Required field Missing");
    		return "";
    	}

		resetForm();
    	return result;
    }

    private void resetForm() {
    	attackingPieceField.setSelectedItem(PieceType.UNKNOWN);
    	fromField.setSelectedItem("--");
    	toField.setSelectedItem("--");
    	capturedPieceField.setSelectedItem(PieceType.UNKNOWN);
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
    }

	public JPanel getPanel() {
		return panel;
	}

}
