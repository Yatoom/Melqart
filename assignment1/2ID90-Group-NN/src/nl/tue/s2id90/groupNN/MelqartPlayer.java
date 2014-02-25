<<<<<<< HEAD
package nl.tue.s2id90.groupNN;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Collections;
import java.util.List;
import nl.tue.s2id90.draughts.Draughts;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 * @author Jeroen van Hoof
 * @author Theodoros Margomenos
 */
public class MelqartPlayer extends DraughtsPlayer {

    private Move bestMove = null;
    private int value = 0;
    
    /**
     * Returns the amount of pieces that are on the board given a game state
     * {@code gs}
     *
     * @param gs game state of the board
     * @return the amount of pieces that are on the board. 0 if empty.
     */
    public int getPieceCount(DraughtsState gs) {
        int[] pieces = gs.getPieces();
        int count = 0;
        for (int f = 1; f < pieces.length; f = f + 1) {
            int piece = pieces[f];
            if (Draughts.isWhite(piece) || Draughts.isBlack(piece)) {
                count++;
            }
        }
        return count; //0 if no pieces on the board.
    }

    private boolean stopped = false;

    @Override
    public void stop() {
        stopped = true;
    }

int alphaBeta (GameNode node, int depth, int alpha, int beta, boolean maximizingPlayer){
        DraughtsState state = node.getState();
        List<Move> moves = state.getMoves();
        if (depth == 0 || state.isEndState()) {
            return node.getValue(state);
        } 
        if (maximizingPlayer) {
            for (Move move : moves){
                state.doMove(move);
                // recursive call
                System.out.println("Clone");
                GameNode newNode = new GameNode(state.clone());
                System.out.println("End clone");
                alpha = max(alpha, alphaBeta(newNode, depth-1, alpha, beta, false));
                if (beta <= alpha) {
                    this.bestMove = move;
                    break; //b cut off
                }
                state.undoMove(move);                                
            }
            //node.setBestMove(alpha);
            
            return alpha;
        }
        else { //minimizingPlayer
            for (Move move : moves){
                state.doMove(move);
                // recursive call
                System.out.println("Clone");
                GameNode newNode = new GameNode(state.clone());
                System.out.println("End clone");
                beta = min(beta, alphaBeta(newNode, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    this.bestMove = move;
                    break; //a cut off
                }
                state.undoMove(move);                     
            }
            //node.setBestMove(beta);
            
            return beta; 
        }
}

    @Override
    public Move getMove(DraughtsState ds) {
        if (ds == null){
            throw new IllegalArgumentException("blub");
        } // Not ze problem. le baguette
        System.out.println("getmoves");
        List<Move> moves = ds.getMoves();
        System.out.println("gamenode");
        GameNode node = new GameNode(ds.clone());
        System.out.println("alphaBeta");
        this.value = alphaBeta(node, 1, 0, 0, ds.isWhiteToMove());
        if (this.bestMove == null){
            throw new IllegalArgumentException("blub2");
        } // Not ze problem. le baguette
        System.out.println("The return: extension, remastered");
        return this.bestMove;
    }
    
    @Override
    public Integer getValue() {
        return value;
    }
}
=======
package nl.tue.s2id90.groupNN;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Collections;
import java.util.List;
import nl.tue.s2id90.draughts.Draughts;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 * @author Jeroen van Hoof
 * @author Theodoros Margomenos
 */
public class MelqartPlayer extends DraughtsPlayer {

    public MelqartPlayer() {
        super(MelqartPlayer.class.getResource("resources/squirtle.jpg"));
    }
    
    /**
     * Returns the amount of pieces that are on the board given a game state
     * {@code gs}
     *
     * @param gs game state of the board
     * @return the amount of pieces that are on the board. 0 if empty.
     */
    public int getPieceCount(DraughtsState gs) {
        int[] pieces = gs.getPieces();
        int count = 0;
        for (int f = 1; f < pieces.length; f = f + 1) {
            int piece = pieces[f];
            if (Draughts.isWhite(piece) || Draughts.isBlack(piece)) {
                count++;
            }
        }
        return count; //0 if no pieces on the board.
    }

    private boolean stopped = false;

    @Override
    public void stop() {
        stopped = true;
    }

int alphaBeta (GameNode node, int depth, int alpha, int beta, boolean maximizingPlayer){
        GameState state = node.getState();
        List<Move> moves = state.getMoves();
        if (depth == 0 || state.isEndState()) {
            return node.getValue();
        } 
        if (maximizingPlayer) {
            for (Move move : moves){
                state.doMove(move);
                // recursive call
                alpha = max(alpha, alphaBeta((GameNode) state, depth-1, alpha, beta, false));
                if (beta <= alpha) {
                    break; //b cut off
                }
                state.undoMove(move);                                
            }
            node.setBestMove(alpha);
            return alpha;           
        }
        else { //minimizingPlayer
            for (Move move : moves){
                state.doMove(move);
                // recursive call
                beta = min(beta, alphaBeta((GameNode) state, depth-1, alpha, beta, true));
                if (beta <= alpha) {
                    break; //a cut off
                }
                state.undoMove(move);                     
            }
            node.setBestMove(beta);
            return beta; 
        }
}

    @Override
    public Move getMove(DraughtsState s) {
        System.out.println("getmoves");
        List<Move> moves = s.getMoves();
        System.out.println("gamenode");
        GameNode node = new GameNode(s.clone());
        System.out.println("alphaBeta");
        alphaBeta(node, 1, 0, 0, true);
        System.out.println("return");
        return moves.get(0);
    }
    
    @Override
    public Integer getValue() {
        return 1337;
    }
}
>>>>>>> 970d8073ce6df65a8e99280c6e64df9278cabf0d
