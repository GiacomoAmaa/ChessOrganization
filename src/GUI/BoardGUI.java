package GUI;

import java.awt.*;

import javax.swing.*;
import java.util.List;

import board.Board;
import board.Game;
import board.Tile;
import util.Pair;
import util.loaders.PieceLoader;

public class BoardGUI {

    private JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));
    private PieceLoader images = new PieceLoader();

    BoardGUI(Game game) {
        initializeGui(game.getPosition());
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
            	button.setBackground(java.awt.Color.WHITE);
            } else {
            	button.setBackground(java.awt.Color.BLACK);
            }
            chessBoard.add(button);
        }
    }

    public final JComponent getGui() {
        return chessBoard;
    }
}
