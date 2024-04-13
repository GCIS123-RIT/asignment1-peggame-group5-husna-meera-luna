/**
 *This class is the MAIN CLASS of the entire peggame 
 the public class is only calling out the required statements 
 for the game to succesfully execute
 */

import java.util.Scanner;


public class Project1Main {
    /**
     * the main method of the program
     */
    public static void main(String[] args) {
        /**
         * This scanner now is creating a new scanner object so that the user can input

         */
        Scanner scanner = new Scanner(System.in);
        
        /**Prompting the user to enter the name of the file*/
        System.out.println("Enter your File: "); 

        /**Reading the user's input as a string*/
        String input = scanner.nextLine();

        /**
         * Reads the board array from the file and stores it in the board variable*/
        char[][] board = ReadtheFile.ReadArray(input);

        /**
         * Creating a new CommandLine object with the board array 
         * and then calling the PlayPegGame method to start the game*/
        CommandLine pegGame=new CommandLine(board);
        CommandLine.PlayPegGame(pegGame, scanner);
    }
}
