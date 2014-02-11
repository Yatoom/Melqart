/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.tue.s2id90.contest.util;

import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import nl.tue.s2id90.game.Player;
import nl.tue.s2id90.game.GameState;

/**
 * class
 * @author huub
 * @param <M>  Move
 * @param <U>  Undo
 * @param <S> GameState
 */
public abstract class SearchTask<M,U,S extends GameState<M>>
{  
    private SwingWorker<M,U> worker;
    private Player<M,S> player;
    private final S state;

    /**
     * @param player for whom to perform a search for the best move in state s
     * @param s game state in which to search for a best move
     */
    public SearchTask(Player<M,S> player, S s) {
        this.state = s;
        this.player = player;
    }
    
    /**
     * starts a background job to determine the best move of this SearchTask's
     * player and calls done() when the job finishes.
     * @see SearchTask#done(Object) 
     * @see SwingWorker
     */
    public void execute() {
        worker = createNewSwingWorker();
        worker.execute();
    }
    
    /** @return the moves of player in this state. **/
    private M search() {
        if (player!=null) {
            return player.getMove(state);
        } else {
            return null;
        }
    }
    
    /**
     * @return swing worker that starts the search() method and calls done() when that 
     *         task finishes.
     * @see SwingWorker
     */
    private SwingWorker<M,U> createNewSwingWorker() {
        return new SwingWorker<M,U>() {
            @Override
            protected M doInBackground() throws Exception {
                System.err.println("searchtask.doInBackground");
                return SearchTask.this.search();
            }

            @Override
            protected void done() {
                try {
                    System.err.println("searchTask.done()");
                    M m = get(); // gets computed move
                    SearchTask.this.done(m);
                    
                } catch (InterruptedException | ExecutionException ex) { }
            }            
        };
    }
    
    /**
     * method called just before execute finishes.
     * @param m move found in search
     * @see execute()
     */
    abstract public void done(M m);
    
    /**
     *
     */
    public void stop() {
        player.stop();            
        while (!worker.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }
}