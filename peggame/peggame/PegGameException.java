package peggame;
/**
 * Represents an exception specific to the peg game.
 */
public class PegGameException extends Exception {
    /**
     * Constructs PegGameException with a specific error message.
     * @param message
     */
    public PegGameException(String message) {
        super(message);
    }
}