package board;

import util.MoveSymbols;

/**
 * 
 */
public class MoveParser {
    private String[] move;

    private enum Position {
        ATTACKER,
        FROM,
        MOVE_TYPE,
        DEFENDER,
        TO;
    }

    /**
     * 
     * @param move the move string
     */
    public void parse(final String move) {
        this.move = move.split(MoveSymbols.SEPARATOR.getSymbol());
    }

    /**
     * 
     * @param sym the type of the move
     * @return true if the move corresponds to the type
     */
    public boolean isMoveType(final MoveSymbols sym) {
        return this.move[Position.MOVE_TYPE.ordinal()].contains(sym.getSymbol());
    }

    /**
     * 
     * @return the attacking piece
     */
    public String getAttacker() {
        return this.move[Position.ATTACKER.ordinal()];
    }

    /**
     * 
     * @return the defending piece
     */
    public String getDefender() {
        return this.move[Position.DEFENDER.ordinal()];
    }

    /**
     * 
     * @return tile row from of the start position
     */
    public int getStartingRow() {
        return Character.getNumericValue(this.move[Position.FROM.ordinal()].charAt(0));
    }

    /**
     * 
     * @return row of the arriving tile
     */
    public int getArrivalRow() {
        return Character.getNumericValue(this.move[Position.TO.ordinal()].charAt(0));
    }

    /**
     * 
     * @return tile column of the stat position
     */
    public char getStartingCol() {
        return this.move[Position.FROM.ordinal()].charAt(0);
    }

    /**
     * 
     * @return column of the arriving tile
     */
    public char getArrivalCol() {
        return this.move[Position.TO.ordinal()].charAt(0);
    }

    /**
     * 
     * @return coordinates of the starting piece
     */
    public String getStartingCoord() {
        return this.move[Position.FROM.ordinal()];
    }

    /**
     * 
     * @return coordinates of the arriving tile
     */
    public String getArrivalCoord() {
        return this.move[Position.TO.ordinal()];
    }

}
