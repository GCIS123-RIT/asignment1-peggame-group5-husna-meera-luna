package peggame;

import java.util.ArrayList;
import java.util.Collection;

public class CommandLine implements PegGame {
    
    private char[][] board;

    public CommandLine(char[][] board) {
        this.board = board;
    }

    @Override
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

    @Override
    public GameState getGameState() {
        int PegCount = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 'o') {
                    PegCount++;
                }
            }
        }

        Collection<Move> PossibleMoves = getPossibleMoves();
        if (!PossibleMoves.isEmpty()) {
            return GameState.IN_PROGRESS;
        } else {
            return (PegCount == 1) ? GameState.WON : GameState.STALEMATE;
        }
    }

    @Override
    public void makeMove(Move move) throws PegGameException {
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
    }

    private void validateStartLocation(Location from) throws PegGameException {
        if (board[from.getRow()][from.getColumn()] != 'o') {
            throw new PegGameException("No peg at the start location!");
        }
    }

    private void validateEndLocation(Location to) throws PegGameException {
        if (board[to.getRow()][to.getColumn()] != '.') {
            throw new PegGameException("Destination is not an empty hole!");
        }
    }

    private Location getJumpLocation(Location from, Location to) {
        int jumpRow = (from.getRow() + to.getRow()) / 2;
        int jumpCol = (from.getColumn() + to.getColumn()) / 2;
        return new Location(jumpRow, jumpCol);
    }

    private void validateJumpLocation(Location jumpLocation) throws PegGameException {
        if (board[jumpLocation.getRow()][jumpLocation.getColumn()] != 'o') {
            throw new PegGameException("Not jumping over a peg!");
        }
    }

    @Override
    public String toString() {
        StringBuilder SB = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                SB.append(board[row][col]).append(" ");
            }
            SB.append("\n");
        }
        return SB.toString();
    }

    public static Location[][] LocationConverting(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        Location[][] LocationBoard = new Location[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                LocationBoard[i][j] = new Location(i, j);
            }
        }
        return LocationBoard;
    }

    public static void PlayPegGame(PegGame peggame, java.util.Scanner scanner) {
        System.out.println("Welcome to PegGame :) \nKindly enter your commands to play! ");
        System.out.println("Type 'Q' to exit the game :D");

        while (true) {
            System.out.println(peggame.toString());
            
            Collection<Move> possibleMoves = peggame.getPossibleMoves();
            if (possibleMoves.isEmpty()) {
                System.out.println("Oops! No possible moves available. \nGame Over.");
                break;
            }

            if (peggame.getGameState() != GameState.IN_PROGRESS) {
                System.out.println(peggame.getGameState() == GameState.WON ? "YOU WON!! ^_^" : "Stalemate.. No more moves left.");
                break;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Game Finished :)");
                break;
            }

            String[] tokens = input.split(" ");

            if (tokens.length == 5 && tokens[0].equalsIgnoreCase("move")) {
                try {
                    int r1 = Integer.parseInt(tokens[1].substring(1));
                    int c1 = Integer.parseInt(tokens[2].substring(1));
                    int r2 = Integer.parseInt(tokens[3].substring(1));
                    int c2 = Integer.parseInt(tokens[4].substring(1));

                    try {
                        peggame.makeMove(new Move(new Location(r1, c1), new Location(r2, c2)));
                    } catch (PegGameException e) {
                        System.out.println("Error Detected: " + e.getMessage());
                    }
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.out.println("Invalid Input :( ");
                }
            } else {
                System.out.println("Invalid command. Please use 'move r1 c1 r2 c2' or 'quit'.");
            }
        }
        scanner.close();
    }
}
