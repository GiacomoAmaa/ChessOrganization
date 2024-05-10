package util.loaders;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import board.Piece;
import util.Pair;
import util.PieceType;

public class PieceLoader {
    private static final String SEPARATOR = System.getProperty("file.separator");
    public static final String DEFAULT_DIR = System.getProperty("user.dir") + SEPARATOR
            + "resources" + SEPARATOR + "pieces" + SEPARATOR;
    
    private static final Map<Pair<PieceType,String>,BufferedImage> whitePieces = new HashMap<>(Map.of(
    		new Pair<>(PieceType.BISHOP,"whitebishop.gif"), null,
    		new Pair<>(PieceType.KING,"whiteking.gif"), null,
    		new Pair<>(PieceType.KNIGHT,"whiteknight.gif"), null,
    		new Pair<>(PieceType.PAWN,"whitepawn.gif"), null,
    		new Pair<>(PieceType.QUEEN,"whitequeen.gif"), null,
    		new Pair<>(PieceType.ROOK,"whiterook.gif"), null ));
    
    private static final Map<Pair<PieceType,String>,BufferedImage> blackPieces = new HashMap<>(Map.of(
    		new Pair<>(PieceType.BISHOP,"blackbishop.gif"), null,
    		new Pair<>(PieceType.KING,"blackking.gif"), null,
    		new Pair<>(PieceType.KNIGHT,"blackknight.gif"), null,
    		new Pair<>(PieceType.PAWN,"blackpawn.gif"), null,
    		new Pair<>(PieceType.QUEEN,"blackqueen.gif"), null,
    		new Pair<>(PieceType.ROOK,"blackrook.gif"), null ));
    
    public PieceLoader() {
    	whitePieces.entrySet().forEach(x-> x.setValue(loadPiece(x.getKey().getY())));
    	blackPieces.entrySet().forEach(x-> x.setValue(loadPiece(x.getKey().getY())));
    }

    public BufferedImage getPiece(Piece piece) {
    	if(piece.getColor().isWhite()) {
    		return PieceLoader.whitePieces.entrySet()
    				.stream()
    				.filter(x-> x.getKey().getX().equals(piece.getType()))
    				.findFirst().get().getValue();
    	}
		return PieceLoader.blackPieces.entrySet()
				.stream()
				.filter(x-> x.getKey().getX().equals(piece.getType()))
				.findFirst().get().getValue();
    }
    
    private BufferedImage loadPiece(final String name) {
    	BufferedImage img = null;
    	try {
    	    img = ImageIO.read(new File(DEFAULT_DIR + name));
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
		return img;
    }
    
}
