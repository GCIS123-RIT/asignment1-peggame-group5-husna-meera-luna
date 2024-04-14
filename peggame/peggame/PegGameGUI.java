//DONE BY HUSNA, LUNA AND MEERA
//THE GUI PART OF THE PEGGAME
package peggame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * So this is the GUI class which runs the 
 * execution of PegGame graphically and interects with the user.
 */
public class PegGameGUI extends Application {

/**
 * The radius of the pegs used in the PegGame which we have set to 20 
 */
private static final double radii_peg = 20;

/**
 * The color of the pegs in the PegGame, set to white
 */
private static final Color ColorPeg = Color.WHITE;

/**
 * The color of the holes in the PegGame, set to black
 */
private static final Color ColorHole = Color.BLACK;

/**
 * The background color of the PegGame board, set to pink.
 */
private static final Color ColorTheBoard = Color.PINK;

/**
 * The color used to indicate a selected peg in the PegGame, 
 * set to a lavender
 */
private static final Color SelectionColor = Color.web("bf92e4");


/**
 * 2D character array representing the game board and each character represents a position on the board like row and coloumn 
 */
private char[][] board;

/**
 * The row index of the currently selected peg or hole on the game board.
 */
private int selectedRow = -1;

/**
 * The column index of the currently selected peg or hole on the game board.
 */
private int selectedCol = -1;

/**
 * A GridPane object used to organize and display the pegs and holes on the game board.
 */
private GridPane grid;

/**
 * A Button object used for saving the current game state or action.
 */
private Button saveButton;

/**
 * The method Initializes and displays the primary stage of the Peg Game application. This method sets up 
 * the game title, start button, save button, quit button,
 * and the layout for the primary stage.
 * 
 * @param primaryStage The primary stage of the JavaFX application.
 */
    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("Welcome to Peg Game!");
        title.setStyle("-fx-font-size: 40px;");

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> loadGameBoard(primaryStage));

        saveButton = new Button("Save Game");
        saveButton.setOnAction(e -> saveGame());
        saveButton.setVisible(false);  // Initially hide the save button

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(title, startButton, quitButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 500, 500, Color.WHITE);
        primaryStage.setTitle("Peg Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

/**
 * This method loads a game board from a selected text file and displays it in a new scene. This method utilizes a FileChooser to allow 
 * the user to select a text file containing the game board configuration. The selected file is then read,
 * and the game board is created and displayed using a BorderPane layout.
 * 
 * @param stage The primary stage of the JavaFX application where the game board will be displayed.
 */
    private void loadGameBoard(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);
    
        if (selectedFile != null) {
            board = ReadtheFile.ReadArray(selectedFile.getAbsolutePath());
            BorderPane mainBranch = new BorderPane();
            mainBranch.setStyle("-fx-background-color: white;");
    
            mainBranch.setCenter(createGameBoard(board));
    
            HBox controlPanel = createControlPanel(stage);
            mainBranch.setTop(controlPanel);  // Set control panel at the top
    
            Scene gameScene = new Scene(mainBranch, 500, 500);
            stage.setScene(gameScene);
        }
    }

/**
 * This method creates and returns a hbox containing control buttons for the game. The control panel includes 
 * a "Save Game" button and an "Exit" button.
 * The "Save Game" button triggers the saveGame() method when clicked and the "Exit" button closes the current stage to exit the game when clicked.
 * 
 * @param stage The primary stage of the JavaFX application where the control panel will be used.
 * @return An HBox containing the control buttons for the game.
 */
    private HBox createControlPanel(Stage stage) {
        HBox controlPanel = new HBox(10);
        controlPanel.setAlignment(Pos.CENTER);
    
        Button saveButton = new Button("Save Game");
        saveButton.setOnAction(e -> saveGame());
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> stage.close());  // Close the current stage to exit game
    
        controlPanel.getChildren().addAll(saveButton, exitButton);
        return controlPanel;
    }

/**
 * This method creates and returns a GridPane representing the game board based on the provided board array.
 * The method then sets up the GridPane with specific styling and alignment properties. It iterates through the boardArray 
 * to create Circle pegs for each position on the board using the createPeg method and adds them to the GridPane.
 * 
 * @param boardArray A 2D character array representing the game board configuration.
 * @return A GridPane containing the pegs representing the game board.
 */
    private GridPane createGameBoard(char[][] boardArray) {
        grid = new GridPane();
        grid.setStyle("-fx-background-color: " + toRgbString(ColorTheBoard) + "; -fx-padding: 10; -fx-hgap: 10; -fx-vgap: 10; -fx-alignment: center;");

        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                Circle peg = createPeg(boardArray[i][j], i, j);
                grid.add(peg, j, i);
            }

        }
        return grid;
    }

/**
 * Creating and returning a Circle object representing a peg for a specific cell on the game board. The method initializes a Circle with 
 * a specified radius and sets its stroke width.
 * It then updates the visual appearance of the peg using the updatePegVisual method.
 * Additionally, it adds a MouseEvent handler to the peg to handle peg selection.
 * 
 * @param cell The character representing the content of the cell on the game board.
 * @param row The row index of the cell where the peg will be placed.
 * @param col The column index of the cell where the peg will be placed.
 * @return A Circle object representing a peg for the specified cell.
 */
    private Circle createPeg(char cell, int row, int col) {
        Circle circle = new Circle(radii_peg);
        circle.setStrokeWidth(2);
        updatePegVisual(circle, cell, row, col);

        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            handlePegSelection(circle, row, col);
        });

        return circle;
    }

    /**
 *Handles the game board's peg selection and deselection according to user input.
 *This method uses a Circle to represent a peg along with its rows and columns.
 *After determining the current status of the chosen cell on the board, it handles: 
 * - Picking or selecting an available peg if nothing is chosen.
 * - Moves peg if none is chosen and ensures it is a correct move.
 * - Deselects if an unlegitamite or incorrect peg is chosen or the selected peg is re-clicked.
 * As well as, upadating the board display and informs the user for illegitamite moves.
 * 
 * @param circle The Circle object represensts the peg that was chosen.
 * @param row The row index of the selected peg or hole.
 * @param col The column index of the selected peg or hole.
 */

    private void handlePegSelection(Circle circle, int row, int col) {
        char currentCell = board[row][col]; // Gets the current state of the board.
        if (currentCell == 'o' && selectedRow == -1 && selectedCol == -1) {
            // Selects the peg if none is selected.
            circle.setStroke(SelectionColor);
            selectedRow = row;
            selectedCol = col;
        } else if (currentCell == '.' && selectedRow != -1 && selectedCol != -1 && isValidMove(selectedRow, selectedCol, row, col)) {
            // Makes the move if nothing was picked or there was ana wrong move.
            makeMove(selectedRow, selectedCol, row, col);
            updateBoardDisplay();  // updates and displays the board.
            selectedRow = -1;
            selectedCol = -1;
        } else {
            // Deselect if user makes a wrong move or re-clicks the peg.
            deselectCurrentSelection();
            showIllegalMoveAlert();
        }
    }
/**
 * This method helps alert the user if any wrong move was made or wrong peg was chosen.
 * Therefore, informing the user of the wrong move by displaying "Invalid Move!".
 */
    private void showIllegalMoveAlert() {
        //Creates an alert with INFORMATION type
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invalid Move!");
        alert.setHeaderText(null);
        //Setting a text with content so user understands that the mode made was invalid.
        alert.setContentText("Hey, you can't make that move!");
        //Alert waits for the user to acknowledge its invalid move.
        alert.showAndWait();
    }
    
    /**
     * This method delselects any selected peg by setting its color to transparents.
     * Then, it delects the peg by resetting the selected row and selected column to -1 indicating that no peg is selected.
    */
    private void deselectCurrentSelection() {
        //Checks if any peg is selected.
        if (selectedRow != -1 && selectedCol != -1) {
            Circle selectedPeg = (Circle) getNodeByRowColumnIndex(selectedRow, selectedCol, grid);
            //Checks if the selected peg exists and setting it to transparent to show that its been deselected.
            if (selectedPeg != null) {
                selectedPeg.setStroke(Color.TRANSPARENT);
            }
            //Resetting it to -1 to deselect the peg.
            selectedRow = -1;
            selectedCol = -1;
        }
    }
/*
 * This method checks if the move made is invalid or not on the board.
 * 
 * @param fromRow is the starting position of the peg from the row.
 * @param fromCol is the starting position of the peg from the column.
 * @param toRow is the destination of the peg after a  move is made to the row.
 * @param toCol is the destination of the peg after a move is made to the column.
 * @return
*/
    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        //Checks if the move is moving vertically.
        if (Math.abs(fromRow - toRow) == 2 && fromCol == toCol) {
            //checks if theres space for the peg to jump to.
            return board[(fromRow + toRow) / 2][fromCol] == 'o';
            //checks if the move is moving horizantally.
        } else if (Math.abs(fromCol - toCol) == 2 && fromRow == toRow) {
            return board[fromRow][(fromCol + toCol) / 2] == 'o';
        }
        //if the move is invaild it returns false.
        return false;
    }
/**
 * Moves a peg from one place on the game board to another to make a move.
 * By moving a peg from the starting location to the specified destination position, 
 * this method updates the board state.
 * The peg that was jumped over is removed off the board if the move needs jumping over another peg. 
 * The board's display changes and is updated after the move.
 * @param fromRow
 * @param fromCol
 * @param toRow
 * @param toCol
 */
    private void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        //removes a peg from its orginal position.
        board[fromRow][fromCol] = '.';
        //places it in a new destination.
        board[toRow][toCol] = 'o';
        //removes a peg if it was jumped over another.
        board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = '.';
        //updates the board after the move.
        updateBoardDisplay();
    }
/**
 * The game board's display is updated to reflect the board's current status.
 This procedure goes over every cell on the board iteratively, 
 updating the board display according to the state of the peg.
 To change each peg's display, it executes the updatePegVisual method.
 */
    private void updateBoardDisplay() {
        //Iterating through each peg of the board.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                //Retrieving the peg at the current cell.
                Circle peg = (Circle) getNodeByRowColumnIndex(i, j, grid);
                //Checking if a peg exists at the current cell.
                if (peg != null) {
                    //updates the appearance of the peg based on its state.
                    updatePegVisual(peg, board[i][j], i, j);
                }
            
            }
        }
    }

    /**
     * Updates the appearance of a peg on the game board.
     *  This method represents a peg on the game board, based on the state of the cell.
     * 
     * @param circle the circle object representing the peg to be updated.
     * @param cell represents the state of the cell
     * @param row is the row index of the cell on the game board grid.
     * @param col is the column index of the cell on the game board grid.
     */

    private void updatePegVisual(Circle circle, char cell, int row, int col) {
        // If the cell contains a peg, set the fill color to a specified peg color.
        if (cell == 'o') {
            circle.setFill(ColorPeg);
         // If the cell does not contain a peg, set the fill color to a specified hole color.
        } else if (cell == '.') {
            circle.setFill(ColorHole);
        }
        circle.setStroke(Color.TRANSPARENT);  // Deselect any previously selected peg by making it transparent.
    }
/**
 * In order to locate the Circle node at the given row and column index, this method iterates over the child nodes of the GridPane.
 *  A Circle node is returned if it is located at the specified location. 
 * @param row
 * @param col
 * @param gridPane
 * @return
 */
    private Circle getNodeByRowColumnIndex(final int row, final int col, GridPane gridPane) {
        //Iterating through the child nodes of the GridPane
        for (javafx.scene.Node node : gridPane.getChildren()) {
            //Checks if the current circle node is located at the specified row and column index.
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Circle) {
                //If a Circle node is found at the specified position, return it.
                return (Circle) node;
            }
        }
        // If no Circle node is found at the specified position, return null
        return null;
    }
/**
 * Converts a JavaFX Color object to an RGB string representation.
 * This method takes a Color object representing an RGB color and converts it to a string.
 * @param color is the color object to convert to an RGB string.
 * @return string representation of the color in the rgb(r, g, b).
 */
    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }
/**
 * Saves the current game state to a text file.
 * This method prompts the user to select a location to save the game file.
 */
    private void saveGame() {
        // Creating a file selector box to store the game
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //Prompting the user to select a file location to save the game
        File file = fileChooser.showSaveDialog(null);
        //Write the game state to the file if the user chooses the correct location.
        if (file != null) {
            writeBoardToFile(file.getAbsolutePath());
        }
    }
    /**
     * Writes the current game board state to a text file.
     * This method takes a filename as input and writes the current state of the game board to the specified file.
     * @param filename is the name of the file to which the game board state will be written.
     */
    public void writeBoardToFile(String filename) {

        try (FileWriter writer = new FileWriter(filename, false)) {  // false to overwrite
            writer.write(board.length + "\n");
            // Writing each row of the game board to the file.
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    // Writing each cell of the row as a character to the file
                    writer.write(board[i][j]);
                }
                // Writing a newline character to separate rows
                writer.write("\n");
            }
        // Handling any IOException that may occur during writing the file.
        } catch (IOException e) {
            e.printStackTrace();
            //Displays an error alert to inform the user of the save failure.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Save Error");
            alert.setContentText("Failed to save the game.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
