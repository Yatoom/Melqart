package nl.tue.s2id90.group08;

import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 * @author Theodoros Margomenos
 * @author Jeroen van Hoof
 */
public class NodeLVL3 {
    public static DraughtsState ds;
    
    // Constructor
    public NodeLVL3(DraughtsState ds) {
         if (ds == null){
            throw new IllegalArgumentException("gs in gamenode");
        }
        NodeLVL3.ds = ds.clone();
    }
       
    /**
     * Gets the best move that can be made.
     * @return 
     */
    public int getBestMove() {
        return 0; //testing        
    }

  
    /**
     * Gets the game state of this node.
     * @return 
     */
    public DraughtsState getState() {
        return NodeLVL3.ds;
    }
    
    /**
     * Sets the best move to can be made.
     * @param move
     */
    public void setBestMove(int move) {
        throw new UnsupportedOperationException("Not supported yet."); 
        
    }

    /**
     *
     * @return
     */
    public Integer getValue(DraughtsState ds) {
        return evaluate(ds);
    }

    // Material
    final static int KING = 300;      // Number of kings
    final static int DRAUGHT = 100;   // Number of draughts

    // Infastructure
    final double PLAY = 0.4;  // Playground
    final int MOVE = 4;       // Number of possible moves
    final int MIRROR = 5;     // Number of opposing draughts
    final int DISTR = 5;      // Distribution of draughts in three columns

    // Strategy
    final int SIDE = 6;       // Number of draughts on the sides
    final int LAST = 7;       // Number of draughts on last line
    final int STEPS = 4;      // Manhattan distance to end (except Kings)
    final int DEF = 8;        // Number of defended draughts

    /**
     * Evaluates the current GameState.
     *
     * @param ds The GameState.
     * @return The value of the current state.
     */
    public static int evaluate(DraughtsState ds) {
         if (ds == null){
            throw new IllegalArgumentException("ds in evaluate");
        }
        int total = 0;

        // White or black has to move.
        boolean isWhite = ds.isWhiteToMove();
        // Possible moves.
//        int moves = ds.getMoves().size();

        for (int c = 0; c != 10; c++) {
            for (int r = 0; r != 10; r++) {
                total += addValue(r, c, ds);
            }
        }
        if (!isWhite) {
            total = -total;
        }
        return total;
    }

    public static int addValue(int r, int c, DraughtsState ds) {
        int piece = ds.getPiece(r, c);
        if (piece == DraughtsState.WHITEFIELD || piece == DraughtsState.EMPTY) {
            return 0;
        }
        if (piece == DraughtsState.WHITEKING) {
            return 50 
                    + KING 
                    + calcDef(r, c, true, ds);
        }
        if (piece == DraughtsState.WHITEPIECE){
            return calcPlayground(r, c, true) 
                    + DRAUGHT 
                    + calcDef(r, c, true, ds);
        }
        if (piece == DraughtsState.BLACKKING) {
            return  -50 
                    - KING
                    + calcDef(r, c, false, ds);
        }
        if (piece == DraughtsState.BLACKPIECE){
            return -calcPlayground(r, c, false) 
                    - DRAUGHT
                    + calcDef(r, c, false, ds);
        }
        return 0;
    }

    public static int calcPlayground(int y, int x, boolean white) {
        x++; y++;
        int triangleR = 0, triangleL = 0;

        // Flip board for black
        if (!white) {
            x = 10 - x + 1;
            y = 10 - y + 1;
        }

        // Calculate the big triangle
        int bigTriangle = y / 2 * (y + 1);

        // Overflow on right side
        int baseR = y - 1 + x - 10;
        if (baseR > 0) {
            // Calculate triangle right
            triangleR = (baseR - 1) / 2 * baseR;
        }

        // Overflow on left side
        int baseL = y - x;
        if (baseL > 0) {
            // Calculate triangle left
            triangleL = (baseL + 1) / 2 * baseL;
        }

        return bigTriangle - triangleR - triangleL;

    }
    
    public static int calcDef(int row, int col, boolean white, DraughtsState ds){
        int total = 0;
               
        // Default def for corners and sides.
        int topleft = 10, topright = 10, bottomleft = 10, bottomright = 10;
        
        if (row > 0 && col > 0){
            topleft = ds.getPiece(row-1, col-1);
            total += addDef(topleft);
        }
        if (row > 0 && col < 9){
            topright = ds.getPiece(row-1, col+1);
            total += addDef(topright);
        }
        if (row < 9 && col > 0){
            bottomleft = ds.getPiece(row+1, col-1);
            total += addDef(bottomleft);
        }
        if (row < 9 && col < 9){
            bottomright = ds.getPiece(row+1, col+1);
            total += addDef(bottomright);
        }
        
        return total;
  
    }
    
    public static int addDef(int piece){
        if (piece == DraughtsState.WHITEKING ||
                piece == DraughtsState.WHITEPIECE){
            return 5;
        }
        if (piece == DraughtsState.BLACKKING ||
                piece == DraughtsState.BLACKPIECE){
            return -5;
        }
        return 0;
    }
    
}