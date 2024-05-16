package util.loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import board.Piece;
import util.PieceType;

public class PieceLoader {
    private static final String SEPARATOR = System.getProperty("file.separator");
    public static final String DEFAULT_DIR = System.getProperty("user.dir") + SEPARATOR
            + "resources" + SEPARATOR + "pieces" + SEPARATOR;
    
    private static final Map<PieceType,BufferedImage> whitePieces = new HashMap<>(Map.of(
    		PieceType.BISHOP, loadPiece("whitebishop.png"),
    		PieceType.KING, loadPiece("whiteking.png"),
    		PieceType.KNIGHT, loadPiece("whiteknight.png"),
    		PieceType.PAWN, loadPiece("whitepawn.png"),
    		PieceType.QUEEN, loadPiece("whitequeen.png"),
    		PieceType.ROOK, loadPiece("whiterook.png")));
    
    private static final Map<PieceType,BufferedImage> blackPieces = new HashMap<>(Map.of(
    		PieceType.BISHOP, loadPiece("blackbishop.png"),
    		PieceType.KING, loadPiece("blackking.png"),
    		PieceType.KNIGHT, loadPiece("blackknight.png"),
    		PieceType.PAWN, loadPiece("blackpawn.png"),
    		PieceType.QUEEN, loadPiece("blackqueen.png"),
    		PieceType.ROOK, loadPiece("blackrook.png")));

    public BufferedImage getImage(Piece piece) {
    	return piece.getColor().isWhite() ? 
    			PieceLoader.whitePieces.get(piece.getType()) :
    				PieceLoader.blackPieces.get(piece.getType());
    }
    
    private static BufferedImage loadPiece(final String name) {
    	BufferedImage img = null;
    	try {
    	    img = ImageIO.read(new File(DEFAULT_DIR + name));
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
		return img;
    }
    
}
