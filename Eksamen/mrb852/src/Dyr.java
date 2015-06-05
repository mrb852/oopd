import java.util.List;

/**
 * Dyr / Animal is a subclass of Actor which main feature is that it can scan
 * the environment with its radar
 * @author ChristianEnevoldsen
 */
public abstract class Dyr extends Actor
{
    /**
     * Radar is used to scan the environment for walkable/flyable spots
     */
    private Radar radar;

    /**
     * Keep track of the original radius.
     */
    public final int radius;

    /**
     * Constructor
     * @param environment the environment to present the animal to
     * @param radius the max radius for the animal
     */
    public Dyr( Miljo environment, int radius ) {
        super( environment );
        this.radius = radius;
        this.radar = this.radarForAnimal();
    }

    /**
     * gets the radar
     * @return the radar
     */
    public Radar getRadar() {
        return this.radar;
    }

    /**
     * Scans the environment within the animals visible radius to determine if
     * the actor can move to the spot.
     */
    public boolean canMoveToSpot( Spot spot ) {

        // if the spot is null, you can allow the animal to think it can stay
        // on the same spot.
        if ( this.getSpot() == null ) {
            return this.radar.findSpots( spot, true ).contains( spot );
        }

        return this.radar.findSpots( this.getSpot(), false ).contains( spot );
    }

    /**
     * Sets the radars radius. Useful for spotting fears and or food, but also
     * used to find walkable spots
     * @param radius the new radius. Must not be >
     */
    public void setRadarRadius( int radius ) {
        if ( radius <= this.radius )
            this.radar.radius = radius;
    }

    /**
     * Helper method that simplifies a radar invoke
     * @param includeSpot if true, the radar will include the current spot
     * @return
     */
    public List<Spot> useRadar( boolean includeSpot ) {
        return radar.findSpots( getSpot(), includeSpot );
    }

    /**
     * Uses the radar to find all the walkable spots
     * @return a list containing walkable spots
     */
    public List<Spot> getWalkableSpots() {

        // By default animals can only walk one spot at a time.
        this.setRadarRadius( 1 );
        List<Spot> walkableSpots = this.useRadar( false );
        this.setRadarRadius( radius );
        return walkableSpots;
    }

    // Make sure subclasses creates a radar object, as it is intensely used by
    // this class
    protected abstract Radar radarForAnimal();
}
