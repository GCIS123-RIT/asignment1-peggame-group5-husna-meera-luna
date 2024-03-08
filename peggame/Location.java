package peggame;

/**
 * In this class, we use rows and columns to display the board.
 * In able to  move to or from, we get our coordinations moving through the rows and columnns.
 * Using the toString method to print the location of the peg when prompted and comparing our two locations.
 * We implement our locations to ensure that the two pegs are in two different places.
 */
public class Location {
    private int row; 
    private int col;

    // row and column coordinates for the pegs
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // return row coordinate
    public int getRow() {
        return row;
    }

    // return column coordinate
    public int getColumn() {
        return col;
    }

    // toString method to print the location of the peg when prompted
    @Override
    public String toString() {
        return "The current location of this peg is (" + row + ", " + col + ")";
    }

    // comparing two location objects, to be implemented to make sure no two pegs have the same location
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return row == location.row && col == location.col;
    }
}