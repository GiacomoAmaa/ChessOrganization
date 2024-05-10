package GUI;

import java.awt.*;

import javax.swing.*;

import board.Board;
import board.Game;
import util.loaders.PieceLoader;

public class BoardGUI {

    private JButton[][] chessBoardSquares = new JButton[Board.BOARD_DIM][Board.BOARD_DIM];
    private JPanel chessBoard = new JPanel(new GridLayout(0, Board.BOARD_DIM));
    //private PieceLoader images = new PieceLoader(); TODO fix

    BoardGUI(Game game) {
        initializeGui(game);
    }

    public final void initializeGui(Game game) {
    	int global = 0;
    	for(int i = 0; i<Board.BOARD_DIM; i++){
    		for(int j = 0; j<Board.BOARD_DIM; j++, global++){
    			// TODO init gui
    		}
    	}
    }

    public final JComponent getGui() {
        return chessBoard;
    }
}
