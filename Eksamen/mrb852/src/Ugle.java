import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ugle extends Dyr
{
    /**
     * Constructor, initializes the owl, with a radius of 2.
     * @param enviroment the environment to present the owl in
     */
    public Ugle( Miljo enviroment ) {
        super( enviroment, 2 );
        this.tag = Actor.OWL;
    }

    /**
     * Tries to eat a mouse. Only one
     */
    private void eatMouse() {
        if ( this.getSpot() != null ) {

            // Get mice from spot
            List<Actor> mice = this.getSpot().getActors( Actor.MICE );
            mice.addAll( this.getSpot().getActors( Actor.MOUSE ) );

            if ( !mice.isEmpty() ) {
                for ( Actor actor : mice ) {
                    Mus m = (Mus) actor;
                    if ( !m.isDead() ) {
                        m.getEaten( this );
                        if ( m.isDead() )
                            return;
                    }
                }
            }
        }
    }

    /**
     * Uses the radar to find all spots containing mice within a radius
     * @param radius the radius for the radar
     * @return a list containing all spots with mice
     */
    private List<Spot> getSpotsWithMice( int radius ) {
        setRadarRadius( radius );
        List<Spot> visibleSpots = useRadar( false );
        List<Spot> spotsWithMice = new ArrayList<Spot>();
        for ( Spot spot : visibleSpots )
            if ( spot.contains( MICE ) || spot.contains( MOUSE ) )
                spotsWithMice.add( spot );

        return spotsWithMice;
    }

    /**
     * Moves the owl
     */
    public void step() {

        this.moveToSpot( this.getNextSpot() );

    }

    /**
     * Figures out the best possible move for the current state. If mice found
     * chase them, otherwise perform random movements
     * @return the next spot to move to
     */
    private Spot getNextSpot() {

        // Get all walkableSpots and shuffle because the owl will always just
        // pick the first
        // spot in the list.
        List<Spot> walkableSpots = this.getWalkableSpots();
        Collections.shuffle( walkableSpots );
        if ( walkableSpots.isEmpty() ) {

            // Nothing to do
            return this.getSpot();
        }
        else {
            // Determines the closest mice, and starts "hunting" them
            List<Spot> spotsWithMice = this.getSpotsWithMice( 2 );
            Collections.sort( spotsWithMice,
                    new ClosestComparator( this.getSpot() ) );

            if ( !spotsWithMice.isEmpty() ) {
                return spotsWithMice.get( 0 );
            }
            else {
                // fall back, when no mice are found.
                return walkableSpots.get( 0 );

            }
        }
    }

    /**
     * Tries to move to a spot, and if succeeded try to eat a mouse
     */
    public boolean moveToSpot( Spot spot ) {

        setRadarRadius( 1 );
        this.eatMouse();
        if ( super.moveToSpot( spot ) ) {
            this.eatMouse();
            return true;
        }
        // Last backup plan
        List<Spot> availabelSpots = this.getWalkableSpots();
        while ( availabelSpots.size() > 0 ) {
            spot = availabelSpots.remove( 0 );
            if ( ( this.canMoveToSpot( spot ) ) ) {
                super.moveToSpot( spot );
            }
        }

        return false;
    }

    /**
     * Creates and returns the radar for the owl
     */
    protected Radar radarForAnimal() {
        return new OwlRadar( 2, environment );
    }

    boolean isDead() {
        return false;
    }

}
