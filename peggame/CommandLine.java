package peggame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
/**
 * This is the main class that implements the PegGame interface. 
 * It contains the board representation and methods to manipulate and query its state, 
 * as well as methods to handle user input and play the game.*/
public class CommandLine implements PegGame {
    
    /**
     * This is a private instance variable that stores the board as a 2D array of characters.
     * Each character represents a cell on the board, with 'o' representing a peg and '.'
     * representing an empty hole.
     */
    private char[][] board;

    /**
     * This is the constructor for the CommandLine class.
     * It takes a 2D array of characters as input and initializes the board instance
     * variable with this value.
     * @param board
     */
    public CommandLine(char[][] board) {
        this.board= board;
    }

    /**
     * This method returns a collection of possible moves that can be made on the current state of the board. 
     * It iterates through each cell on the board and checks if it meets the conditions for a possible move. 
     * If it does, it adds a new Move object to the possibleMoves collection.
     */
    @Override
    public Collection<Move> getPossibleMoves() {
        Collection<Move> possibleMoves = new ArrayList<>();
        for (int row = 0; row < board.length; row++){for (int col = 0; col<board[row].length; col++){
            if (col + 2 < board[row].length && board[row][col]=='o' && board[row][col+1]=='o' && board[row][col+2]=='.')
            {possibleMoves.add(new Move(new Location(row, col), new Location(row, col + 2)));}
            
            if (row + 2 < board.length && board[row][col]=='o' && board[row+1][col]=='o' && board[row+2][col]=='.')
            {possibleMoves.add(new Move(new Location(row, col), new Location(row + 2, col)));}
        }}
        return possibleMoves;
    }

    /**
     * This method returns the current state of the game based on the number of pegs
     * on the board and the number of possible moves. 
     * It initializes a PegCount variable to keep track of the number of pegs on the 
     * board, and a HasMoves variable to check if there are any possible moves.
     * It then iterates through each cell on the board to calculate these values.
     * Based on these values, it returns the appropriate GameState enum value.
     */
    @Override
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
     * This method makes a move on the board by removing a peg from the from location and adding a peg to the to location. 
     * It also checks if the move is valid by checking if there is a peg at the from location, 
     * if the to location is an empty hole, and if the move is a valid jump. 
     * If any of these conditions are not met, it throws a PegGameException.
     */
    @Override
    public void makeMove(Move move) throws PegGameException {
    Location from = move.getFrom();
    Location to = move.getTo();

    try {
        if(board[from.getRow()][from.getColumn()] != 'o') {throw new PegGameException("Invalid move! \nNo peg at the start location");}
        if (board[to.getRow()][to.getColumn()] != '.') {throw new PegGameException("Invalid move! \nDestination is not an empty hole");}

        int jumpCol;
        if (from.getRow() == to.getRow() && from.getColumn() == to.getColumn() + 2 && board[from.getRow()][from.getColumn() - 1] == 'o')
        {jumpCol = from.getColumn() - 1;}

        else if (from.getRow() == to.getRow() && from.getColumn() == to.getColumn() - 2 && board[from.getRow()][from.getColumn() + 1] == 'o')
        {jumpCol = from.getColumn() + 1;}

        else if (from.getRow() == to.getRow() + 2 && board[from.getRow() - 1][from.getColumn()] == 'o') {jumpCol = from.getRow() - 1;}

        else if (from.getRow() == to.getRow() - 2 && board[from.getRow() + 1][from.getColumn()] == 'o') {jumpCol = from.getRow() + 1;}

        else {throw new PegGameException("Invalid move! \nNot a valid jump.");}

        if (board[from.getRow()][jumpCol] != 'o') {
            throw new PegGameException("Invalid Move! \nNot jumping over a peg.");
        }

        board[from.getRow()][from.getColumn()] = '.';
        board[to.getRow()][to.getColumn()] = 'o';
        board[from.getRow()][jumpCol] = '.';}
    catch (PegGameException e){
        System.out.println("Invalid move! " +" \n"+e.getMessage());}
    }


    /**
     * This method prints the current state of the board. 
     * It first retrieves the board from the PegGame object, 
     * and then iterates through each cell on the board to 
     * print its character representation.
     */
    private static void PrintBoard(PegGame pegGame){
        System.out.println("The Current Board: ");
        char[][] board = ReadtheFile.getBoard();
        if(board != null)
        {
            for(char[] row: board){for (char cell: row){System.out.println(cell+" ");}
            System.out.println();}
        }
        else {System.out.println("Error: The board is not availiable...");}
    }

    /*
     * This method prints the current state of the game based on the GameState enum value. 
     * If the game is won, it prints "YOU WON!! ^_^". 
     * If the game is a stalemate, it prints "Stalemate.. No more moves left.". 
     * Otherwise, it prints "Game Over.".
     */
    private static void PrintGameState(GameState gamestate) {
        if(gamestate== GameState.WON) {System.out.println("YOU WON!! ^_^");}
        
        else if(gamestate== GameState.STALEMATE) {System.out.println("Stalemate.. No more moves left.");}
        
        else{System.out.println("Game Over.");}
    }

    /*
     * This method prints the possible moves that can be made on the current state of the board.
     * It first retrieves the possible moves from the PegGame object, and then checks if there are any possible moves. 
     * If there are, it prints "The Possible Moves:" followed by each move. 
     * Otherwise, it prints "Oops! No possible moves availiable. \nGame Over."
     */
    private static void DisplayPossibleMoves(PegGame pegGame){
      Collection<Move> possibleMoves = pegGame.getPossibleMoves();

      if(!possibleMoves.isEmpty()){System.out.println("The Possible Moves:"); 
         for(Move move: possibleMoves){System.out.println("Move: "+move);}}
    
    else {System.out.println("Oops! No possible moves availiable. \nGame Over.");} 
    }

    /*
     * This method returns a string representation of the board.*/
    @Override
    public String toString(){
        StringBuilder SB = new StringBuilder();
        for (int row = 0; row < board.length; row++){for (int col = 0; col<board[row].length; col++)
            {SB.append(board[row][col]).append(" ");}
             SB.append("\n");
            }
            
    return SB.toString();
    }

    /*
     * This method takes a 2D array of characters representing a board and converts it into a 2D array of Location objects. 
     * Each location on the board is represented by a row and column index, 
     * which is used to create a new Location object.
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
     * This method plays a game of PegGame using the provided PegGame object and Scanner object. 
     * It repeatedly prompts the user for input and performs the corresponding move or command. 
     * The game continues until the user types 'Q' to quit or the game is over.
     * @param peggame
     * @param scanner
     */
    public static void PlayPegGame(PegGame peggame, Scanner scanner) {
        System.out.println("Welcome to PegGame :) \nKindly enter your commands to play! ");
        System.out.println("Type 'Q' to exit the game :D");

        while(true) {
            PrintBoard(peggame);

            DisplayPossibleMoves(peggame);

            if(peggame.getGameState() != GameState.IN_PROGRESS) {PrintGameState(peggame.getGameState()); break;}

            String input= scanner.nextLine().trim();

            if(input.equalsIgnoreCase("Q")) {System.out.println("Game Finished :)"); break;}

            String[] tokens = input.split(" ");

            if (tokens.length == 5 && tokens[0].equalsIgnoreCase("move")) {
                try {
                    int r1 = Integer.parseInt(tokens[1].substring(1));
                    int c1 = Integer.parseInt(tokens[2].substring(1));
                    int r2 = Integer.parseInt(tokens[3].substring(1));
                    int c2 = Integer.parseInt(tokens[4].substring(1));

                    try {peggame.makeMove(new Move(new Location(r1, c1), new Location(r2, c2)));}
                    catch (PegGameException e) {System.out.println("Error Dectected" + e.getMessage());}
                }
                    
                catch (NumberFormatException | StringIndexOutOfBoundsException e) {System.out.println("Invalid Input :( ");
                }}
            else {System.out.println("Invalid command. Please use 'move r1 c1 r2 c2' or 'quit'.");}
        }
        scanner.close();
    }
}