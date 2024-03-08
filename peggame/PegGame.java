package peggame;
import java.util.Collection;
/**
 * Creating an interface for peggame that implements the moves, current state, and helping the peghs move from location to location.
 */
public interface PegGame {
    /**
     * 
     * @return
     */
    public Collection<Move> getPossibleMoves(); 
    /**
     * 
     * @return
     */

    public GameState getGameState();
    /**
     * 
     * @param move
     * @throws PegGameException
     */
    public void makeMove(Move move) throws PegGameException;
}