package peggame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadtheFile {
    private static char[][] board;

    /**
     * Reads a text file and converts it to a 2-Dimentional Array.
     * This class contains dimensions of the array to represent the elements.
     * @param filename (name of the file)
     * @return the 2D array from the file or null in case of an error.
     */
    public static char[][] ReadArray(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            // Read the first line to determine the dimensions
            int size = scanner.nextInt();

            // Create the square array with the specified size
            board = new char[size][size];

            // read the rest of the file to fill the array
            for (int row = 0; row < size; row++) {
                String line = scanner.next();
                // check if the line length is equal to the board size
                if (line.length() != size) {
                    System.err.println("Invalid line length in file: " + line);
                    return null;
                }
                // iterate through each character in the line
                for (int col = 0; col < size; col++) {
                    // check if the character represents a peg or an empty hole
                    char c = line.charAt(col);
                    if (c == 'o' || c == '.') {
                        board[row][col] = c;
                    } else {
                        System.err.println("Invalid value in file: " + c);
                        return null;
                    }
                }
            }
            scanner.close();
            return board;

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            e.printStackTrace();
            return null;

        } catch (RuntimeException e) {
            System.err.println("Error parsing file: " + filename);
            e.printStackTrace();
            return null;
        }
    }
    public void writeBoardToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) { // false to overwrite the file
            writer.write(board.length + "\n");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    writer.write(board[i][j]);
                }
                writer.write("\n"); // Newline after each row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is established to retrieve the board array
     * @return the board
     */
    public static char[][] getBoard() {
        return board;
    }
}



  



