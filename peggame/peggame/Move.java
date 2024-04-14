package peggame;

/**
 * From our location we are moving from and to different coordinates using getters.
 */
public class Move {
    private Location from;
    private Location to;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "This peg moves from " + from + " to " + to + ".";
    }
}