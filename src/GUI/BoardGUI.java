package GUI;

import java.awt.*;

import javax.swing.*;

import java.util.LinkedList;
import java.util.List;

import board.Board;
import board.Game;
import board.Tile;
import util.Pair;
import util.loaders.PieceLoader;

public class BoardGUI {

    private JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));
    private PieceLoader images = new PieceLoader();
    private Color lightGray = new Color(215, 215, 215);
    private Color darkGray = new Color(140, 140, 140);
    private List<JButton> buttons = new LinkedList<>();
	private final Game game;

    BoardGUI(Game game) {
    	this.game = game;
        initializeGui(this.game.getPosition());
    }

    public final void initializeGui(Board pos) {
    	List<Pair<String, Tile>> board = pos.getBoard();
    	
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
            this.buttons.add(button);
        }
    }

    public final void displayMove(Board pos) {
    	List<Pair<String, Tile>> board = pos.getBoard();
    	
        for (int i = 0; i < Board.NUM_TILES; i++) {
            if(!board.get(i).getY().isFree()) {
                ImageIcon icon = new ImageIcon(this.images.getImage(board.get(i).getY().getPiece().get()));
                this.buttons.get(i).setIcon(icon);
            } else {
            	this.buttons.get(i).setIcon(null);
            }
        }
    }

    public final JPanel getGui() {
        return chessBoard;
    }

}
