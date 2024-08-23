package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GUI.api.UserInterface;

import java.util.LinkedList;
import java.util.List;

import board.Board;
import board.Game;
import board.Tile;
import util.Pair;
import util.loaders.PieceLoader;

public class BoardGUI implements UserInterface{
	//Panels
    private final JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));
    private final JPanel footer = new JPanel(new GridLayout(0, 2));
	//chessBoard
    private final PieceLoader images = new PieceLoader();
    private final Color lightGray = new Color(215, 215, 215);
    private final Color darkGray = new Color(140, 140, 140);
    private List<JButton> squares = new LinkedList<>();
	//footer
    JButton prevMoveButton = new JButton("Previous Move");
    JButton nextMoveButton = new JButton("Next Move");
    //util
	private final Game game;

    public BoardGUI(Game game) {
    	this.game = game;
        initializeChessBoard();
        initializeFooter();
    }

    public BoardGUI(final List<Pair<String, String>> game) {
    	this.game = new Game(game);
        initializeChessBoard();
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

        this.footer.add(this.prevMoveButton);
        this.footer.add(this.nextMoveButton);
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

	@Override
	public JPanel getUpperPanel() {
		return chessBoard;
	}

	@Override
	public JPanel getLowerPanel() {
		return footer;
	}   
}
