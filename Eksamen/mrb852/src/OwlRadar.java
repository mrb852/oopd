import java.util.ArrayList;
import java.util.List;

/**
 * The OwlRadar is similar to the Radar, except it ignores spots containing owls
 * @author ChristianEnevoldsen
 */
public class OwlRadar extends Radar
{
    public OwlRadar( int radius, Miljo env ) {
        super( radius, env );
    }

    public List<Spot> findSpots( Spot spot, boolean include ) {
        List<Spot> spots = super.findSpots( spot, include );

        // Ignore spots containing owls.
        List<Spot> spotss = new ArrayList<Spot>();
        for ( Spot s : spots ) {
            if ( !s.contains( Actor.OWL ) ) {
                spotss.add( s );
            }
        }
        return spotss;
    }
}