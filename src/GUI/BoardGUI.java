package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.LinkedList;
import java.util.List;

import board.Board;
import board.Game;
import board.Tile;
import util.Pair;
import util.loaders.PieceLoader;

public class BoardGUI {
	//Panels
    private final JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));
    private final JScrollPane rightSidebar = new JScrollPane();
    private final JScrollPane leftSidebar = new JScrollPane();
    private final JPanel footer = new JPanel(new GridLayout(1, 3));
	//chessBoard
    private final PieceLoader images = new PieceLoader();
    private final Color lightGray = new Color(215, 215, 215);
    private final Color darkGray = new Color(140, 140, 140);
    private List<JButton> squares = new LinkedList<>();
	//sidebar
    private final DefaultListModel<String> whiteMoves = new DefaultListModel<>();
    private final JList<String> whiteList = new JList<>(whiteMoves);
    private final DefaultListModel<String> blackMoves = new DefaultListModel<>();
    private final JList<String> blackList = new JList<>(blackMoves);
	//footer
    JButton prevMoveButton = new JButton("Previous Move");
    JButton nextMoveButton = new JButton("Next Move");
    JButton backButton = new JButton("Back");
    //util
	private final Game game;

    public BoardGUI(Game game) {
    	this.game = game;
        initializeChessBoard();
        initializeSidebars();
        initializeFooter();
    }

	public final void initializeChessBoard() {
    	List<Pair<String, Tile>> board = this.game.getPosition().getBoard();
    	
        for (int i = 0; i < Board.NUM_TILES; i++) {
            JButton button = new JButton();

            if(!board.get(i).getY().isFree()) {
                ImageIcon icon = new ImageIcon(images.getImage(board.get(i).getY().getPiece().get()));
                button.setIcon(icon);
            }

            if(board.get(i).getY().getColor().isWhite()) {
            	button.setBackground(lightGray);
            } else {
            	button.setBackground(darkGray);
            }
            button.setPreferredSize(new Dimension(64, 64));
            this.chessBoard.add(button);
            this.squares.add(button);
        }
    }

	private void initializeSidebars() {
		for(Pair<String,String> turn : this.game.getMoves()){
			whiteMoves.addElement(turn.getX());
			blackMoves.addElement(turn.getY());
		}
		this.leftSidebar.add(whiteList);
		this.rightSidebar.add(blackList);
	}

    private void initializeFooter() {
   	 prevMoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.makePrevMove();
				displayMove();
			}
        });

        nextMoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.makeNextMove();
				displayMove();
			}
        });

        backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
        });
        this.footer.add(this.prevMoveButton);
        this.footer.add(this.nextMoveButton);
        this.footer.add(this.backButton);
	}

    public final void displayMove() {
    	List<Pair<String, Tile>> board = this.game.getPosition().getBoard();
    	
        for (int i = 0; i < Board.NUM_TILES; i++) {
            if(!board.get(i).getY().isFree()) {
                ImageIcon icon = new ImageIcon(this.images.getImage(board.get(i).getY().getPiece().get()));
                this.squares.get(i).setIcon(icon);
            } else {
            	this.squares.get(i).setIcon(null);
            }
        }
    }

    public final JPanel getBoard() {
        return chessBoard;
    }

	public JScrollPane getRightSidebar() {
		return rightSidebar;
	}

	public JScrollPane getLeftSidebar() {
		return leftSidebar;
	}

	public JPanel getFooter() {
		return footer;
	}
    
}
