import java.util.Comparator;

/**
 * ClosestComparator is created to sort a list of spots, by comparing them to
 * one spot
 * @author ChristianEnevoldsen
 */
public class ClosestComparator implements Comparator<Spot>
{
    /**
     * The spot to compare to.
     */
    private final Spot _spot;

    /**
     * Constructor. Sets the internal spot
     * @param spot the spot to compare to
     */
    public ClosestComparator( Spot spot ) {
        this._spot = spot;
    }

    /**
     * Compares two spots with the internal spot. The closer one will get the
     * lower index
     */
    @Override
    public int compare( Spot o1, Spot o2 ) {

        // Calculates the distance in spots, by ignoring diagonal skips
        int x1 = Math.abs( _spot.x - o1.x );
        int y1 = Math.abs( _spot.y - o1.y );
        int x2 = Math.abs( _spot.x - o2.x );
        int y2 = Math.abs( _spot.y - o2.y );
        int z1 = x1 + y1;
        int z2 = x2 + y2;

        // Compare the distances
        if ( z1 > z2 )
            return 1;
        else if ( z2 > z1 )
            return -1;
        else
            return 0;

    }

}