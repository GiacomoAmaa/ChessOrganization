package util;

/**
 * Lists all game's sounds.
 */
public enum Sound {
    /**
     * sound names.
     */
    CASTLE("castle.wav"),
    CAPTURE("capture.wav"),
    CHECK("check.wav"),
    MOVE("move.wav");

	 private final String symbol;

	 Sound(String symbol) {
		 this.symbol = symbol;
	 }

	 @Override
	 public String toString() {
	     return symbol;
	 }

}
