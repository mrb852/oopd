import java.util.ArrayList;
import java.util.List;

/**
 * The MouseRadar is similar to the Radar, except it ignores spots containing
 * more than one mouse
 * @author ChristianEnevoldsen
 */
class MouseRadar extends Radar
{
    public MouseRadar( int radius, Miljo env ) {
        super( radius, env );
    }

    public List<Spot> findSpots( Spot spot, boolean include ) {

        List<Spot> spots = super.findSpots( spot, include );
        List<Spot> availableSpots = new ArrayList<Spot>( spots );

        // Ignore spots containing mice.
        for ( Spot s : spots ) {
            if ( s.contains( Actor.MICE ) )
                availableSpots.remove( s );
        }
        return availableSpots;
    }
}