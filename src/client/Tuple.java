package client;

/**
 * Tuple for holding pairs of whatever Object
 * @param <X> First value of the pair.
 * @param <Y> Second value of the pair.
 * @author Tereza Miklóšová
 */
public class Tuple<X,Y> {
    public X x;
    public Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}
