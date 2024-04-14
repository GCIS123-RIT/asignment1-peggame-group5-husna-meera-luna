package peggame;
/**
 * Creating an enum class; a group of constants.
 * In this class, we included NOT_STARTED which is applied before the game starts.
 * We included IN_PROGRESS, STALEMATE which means the player lost with more than one peg.
 * Finally, WON which means player wins with only one peg and ends the game. 
 */

public enum GameState {
    NOT_STARTED, 
    IN_PROGRESS, 
    STALEMATE, 
    WON; 
}