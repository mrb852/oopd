import java.util.ArrayList;
import java.util.List;

/**
 * The radars job is to scan the environment on behalf of an animal.
 * @author ChristianEnevoldsen
 */
public class Radar
{
    public int radius;
    public Miljo env;

    public Radar( int radius, Miljo env ) {
        this.radius = radius;
        this.env = env;
    }

    /**
     * Searches the neighbor spots within its radius
     * @param spot the placement for the radar
     * @param include determines if the radar should search its spot too
     * @return a list containing all neighbor fields
     */
    public List<Spot> findSpots( Spot spot, boolean include ) {
        List<Spot> spots = new ArrayList<Spot>();
        if ( spot != null ) {
            int r = radius;

            // Set boundaries
            int lx = Math.max( spot.x - r, 0 );
            int rx = Math.min( spot.x + r + 1, env.width );
            int ty = Math.max( spot.y - r, 0 );
            int by = Math.min( spot.y + r + 1, env.height );

            // Search the bounding box
            for ( int x = lx; x < rx; ++x ) {
                for ( int y = ty; y < by; ++y ) {
                    spots.add( env.getSpot( x, y ) );
                }
            }
        }

        if ( !include ) {
            spots.remove( spot );
        }

        return spots;
    }
}
