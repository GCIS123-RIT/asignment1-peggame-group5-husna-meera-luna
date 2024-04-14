package peggame;

/**
 * This is the board implementing, for this is where the layout of the game is built
 *  */
import java.util.ArrayList;
import java.util.Collection;

public class BoardImplementation implements PegGame
{/**This is a private instance variable that stores the board as a 2D array of characters. 
    Each character represents a cell on the board, 
    with 'o' representing a peg and '.' representing an empty hole. */
    private static char[][] board;

    /***This is the constructor for the BoardImplementation class. 
     * It takes a 2D array of characters as input and 
     * initializes the board instance variable with this value.*/
    public BoardImplementation(char[][] board){
        this.board = board;
    }

   /***
    * This method returns a collection of possible moves that can be made on the current state of the board. 
    It iterates through each cell on the board 
    and checks if it meets the conditions for a possible move.
    If it does, it adds a new Move object to the possibleMoves collection.
    */

    public Collection<Move> getPossibleMoves() {
        Collection<Move> possibleMoves = new ArrayList<>();
        int rows = board.length;
        int cols = board[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 2; col++) {
                if (board[row][col] == 'o' && board[row][col + 1] == 'o' && board[row][col + 2] == '.') {
                    possibleMoves.add(new Move(new Location(row, col), new Location(row, col + 2)));
                }
            }
        }

        for (int row = 0; row < rows - 2; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 'o' && board[row + 1][col] == 'o' && board[row + 2][col] == '.') {
                    possibleMoves.add(new Move(new Location(row, col), new Location(row + 2, col)));
                }
            }
        }

        return possibleMoves;
    }

    /**
     * This method returns the current state of the game based on the number of pegs on the board and the number of possible moves. 
     * It initializes a PegCount variable to keep track of the number of pegs on the board, 
     * and a HasMoves variable to check if there are any possible moves. 
     * It then iterates through each cell on the board to calculate these values. 
     * One can say based on these values, it returns the appropriate GameState enum value.
     */


    public GameState getGameState() {
       boolean HasMoves = false;
       int PegCount = 0;
       for (int row = 0; row < board.length; row++){for (int col = 0; col<board[row].length; col++)
        {if (board[row][col] == 'o'){PegCount++;}}}
       Collection<Move> PossibleMoves = getPossibleMoves();
       if (!PossibleMoves.isEmpty()){HasMoves = true;}

       if (PegCount == 0){return GameState.NOT_STARTED;}
       else if (!HasMoves){if (PegCount == 1){return GameState.WON;} else {return GameState.STALEMATE;}}
       else {return GameState.IN_PROGRESS;}
    }
       
    /**
     * This method makes a move on the board by removing 
     * a peg from the from location and adding a peg to the to location. 
     * If the move is invalid, it throws a PegGameException. 
     * It also removes the peg that was jumped over in the move.
     */

     @Override
     public static void makeMove(Move move) throws PegGameException {
         Location from = move.getFrom();
         Location to = move.getTo();
         Location jumpLocation = getJumpLocation(from, to);
 
         validateStartLocation(from);
         validateEndLocation(to);
         validateJumpLocation(jumpLocation);
 
         // Perform the move on the board
         board[from.getRow()][from.getColumn()] = '.';   // Remove the peg from the starting position
         board[to.getRow()][to.getColumn()] = 'o';       // Place the peg at the destination position
         board[jumpLocation.getRow()][jumpLocation.getColumn()] = '.'; // Remove the jumped-over peg
 
         // Update peg count
     }
 
     // Validate the start location of the move
     private void validateStartLocation(Location from) throws PegGameException {
         if (board[from.getRow()][from.getColumn()] != 'o') {
             throw new PegGameException("Peg doesn't exist in this spot.");
         }
     }
 
     // Validate the end location of the move
     private void validateEndLocation(Location to) throws PegGameException {
         if (board[to.getRow()][to.getColumn()] != '.') {
             throw new PegGameException("Watch it! There's already a peg there! Rude.");
         }
     }
 
     // Determine the jump location given the start and end locations
     private Location getJumpLocation(Location from, Location to) {
         int jumpRow = (from.getRow() + to.getRow()) / 2;
         int jumpCol = (from.getColumn() + to.getColumn()) / 2;
         return new Location(jumpRow, jumpCol);
     }
 
     // Validate the jump location (must contain a peg)
     private void validateJumpLocation(Location jumpLocation) throws PegGameException {
         if (board[jumpLocation.getRow()][jumpLocation.getColumn()] != 'o') {
             throw new PegGameException("Not jumping over a peg!");
         }
     }

    /**
     * This method returns a string representation of the board.
     */

    public String toString(){
        StringBuilder SB = new StringBuilder();
        for (int row = 0; row < board.length; row++){for (int col = 0; col<board[row].length; col++)
            {SB.append(board[row][col]).append(" ");}
             SB.append("\n");
            }
            
    return SB.toString();}

    /**
     * This method converts a 2D array of characters to a 2D array of Location objects. 
     * It iterates through each cell on the board and creates a new Location object for each cell.
     * It also stores these objects in a new 2D array of Location objects, which is then returned.
     */
    public static Location[][] LocationConverting(char[][] board)
    {
        int rows = board.length;
        int cols = board[0].length;

        Location[][] LocationBoard = new Location[rows][cols];

        for (int i=0; i<rows; i++){for(int j=0; j<cols; j++)
            {LocationBoard[i][j]= new Location(i,j);}}

        return LocationBoard;
    }



    /**
     * This is the main method that demonstrates how to use the BoardImplementation class.
     * BUT WE ARE ONLY USING THIS FOR TESTING OUR CODE
     * It creates a new BoardImplementation object with a sample board Ive created, 
     * and then performs several moves on the board. 
     * After each move, it prints the current state of the board, the possible moves, and the actual moves made. 
     * Finally, it prints the final state of the game.
     * @param args
     * @throws PegGameException
     */
    public static void main(String[] args) throws PegGameException {
        
        char[][] sample = {{'o','o','o','-'},
                           {'o','o','o','o'},
                           {'o','o','o','o'},
                           {'o','o','o','o'}};

        BoardImplementation game1 = new BoardImplementation(sample);

        GameState gameState = game1.getGameState(); System.out.println("Game Current State: "+gameState);

        System.out.println("Possible Moves: "); Collection<Move> PossibleMovesAvaliable = game1.getPossibleMoves();
        for(Move move: PossibleMovesAvaliable){System.out.println(move);}
        
        System.out.println("\nBoard:");
         for (char[] row : game1.board) {for (char cell : row) 
            {System.out.print(cell + " ");} 
            System.out.println();}
        
        
        Move move = new Move(new Location(2,3), new Location(0,3)); System.out.println("Move made: "+move); 
        game1.makeMove(move);

        System.out.println("\nNow the Updated Board: ");
        for (char[] row : game1.board) {for (char cell : row) 
            {System.out.print(cell + " ");} 
            System.out.println();}
        
        System.out.println("Possible Moves: "); Collection<Move> PossibleMovesAvaliable1 = game1.getPossibleMoves();
        for(Move move1 : PossibleMovesAvaliable1){System.out.println(move1);}

        Move move2 = new Move(new Location(1,1), new Location(1,3)); System.out.println("Move made: "+move2); 
        game1.makeMove(move2);

        System.out.println("\nNow the Updated Board: ");
        for (char[] row : game1.board) {for (char cell : row) 
            {System.out.print(cell + " ");} 
            System.out.println();}
        
        System.out.println("Possible Moves: "); Collection<Move> PossibleMovesAvaliable2 = game1.getPossibleMoves();
        for(Move move3 : PossibleMovesAvaliable2){System.out.println(move3);}

        Move move3 = new Move(new Location(2,1), new Location(2,3)); System.out.println("Move made: "+move3); 
        game1.makeMove(move3);

        System.out.println("\nNow the Updated Board: ");
        for (char[] row : game1.board) {for (char cell : row) 
            {System.out.print(cell + " ");} 
            System.out.println();}
        
        System.out.println("Possible Moves: "); Collection<Move> PossibleMovesAvaliable3 = game1.getPossibleMoves();
        for(Move move4 : PossibleMovesAvaliable3){System.out.println(move4);}

        GameState gameStateF = game1.getGameState(); System.out.println("The Final Game State: " + gameStateF);
    }
    public static char[][] getBoard() {
        return board;
    }
}
