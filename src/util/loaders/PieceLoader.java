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
    		PieceType.BISHOP, loadPiece("whitebishop.gif"),
    		PieceType.KING, loadPiece("whiteking.gif"),
    		PieceType.KNIGHT, loadPiece("whiteknight.gif"),
    		PieceType.PAWN, loadPiece("whitepawn.gif"),
    		PieceType.QUEEN, loadPiece("whitequeen.gif"),
    		PieceType.ROOK, loadPiece("whiterook.gif")));
    
    private static final Map<PieceType,BufferedImage> blackPieces = new HashMap<>(Map.of(
    		PieceType.BISHOP, loadPiece("blackbishop.gif"),
    		PieceType.KING, loadPiece("blackking.gif"),
    		PieceType.KNIGHT, loadPiece("blackknight.gif"),
    		PieceType.PAWN, loadPiece("blackpawn.gif"),
    		PieceType.QUEEN, loadPiece("blackqueen.gif"),
    		PieceType.ROOK, loadPiece("blackrook.gif")));

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
