import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mus extends Dyr
{

    private Mus parent;
    public List<Mus> children = new ArrayList<Mus>();

    /**
     * The life / steps of the mouse
     */
    private int life = 20;

    /**
     * Used to filter the mouse each step.
     */
    private boolean dead = false;

    /**
     * Constructor.
     * @param environment the environment to present the mouse in
     */
    public Mus( Miljo environment ) {
        super( environment, 1 );
        this.tag = MOUSE;
    }

    /**
     * Gets a list of spots which contains the given type
     * @param type the actor type
     * @return a list of spots which contains the given type
     */
    private List<Spot> getSpots( int type ) {
        this.setRadarRadius( 1 );
        List<Spot> spots = new ArrayList<Spot>();
        for ( Spot s : useRadar( false ) )
            if ( s.contains( type ) )
                spots.add( s );
        return spots;
    }

    /**
     * Spawns a new mouse if there are any free spots
     */
    public void spawnMouse() {
        Mus m = new Mus( environment );
        for ( Spot s : this.getWalkableSpots() ) {
            if ( m.moveToSpot( s ) ) {
                this.children.add( m );
                m.parent = this;
                break;
            }
        }
    }

    /**
     * Updates the life, spawns mice and moves the mouse around
     */
    public void step() {

        // Try to spawn a mouse
        if ( this.getSpot().contains( MICE ) ) {
            if ( new Random().nextInt( 100 ) < 10 ) {
                this.spawnMouse();
            }
        }

        // Move around
        Spot spot = this.getNextSpot();
        if ( !super.moveToSpot( spot ) ) {
            // Last backup plan
            List<Spot> availabelSpots = this.getWalkableSpots();
            while ( availabelSpots.size() > 0 ) {
                spot = availabelSpots.remove( 0 );
                if ( ( this.canMoveToSpot( spot ) ) ) {
                    super.moveToSpot( spot );
                }
            }
        }

        // Update life and die if life <= 0
        this.life--;
        if ( this.life <= 0 )
            this.die();
    }

    /**
     * Dies if there is no rock to hide behind
     * @param ugle the owl that eats this
     */
    public void getEaten( Ugle ugle ) {
        // If there is a rock on this spot, request assistance from the rock.
        // If permitted, the death is cancelled.
        List<Actor> rocks = this.getSpot().getActors( ROCK );
        if ( !rocks.isEmpty() ) {
            if ( ( (Sten) rocks.get( 0 ) ).requestProtection( this ) ) {
                return;
            }
        }
        this.die();
    }

    /**
     * Performs the death of this mouse. Removes the mouse from its spot and
     * changes from not dead to dead
     */
    public void die() {
        if ( this.getSpot() != null ) {
            this.getSpot().removeActor( this );
            if ( this.parent != null )
                this.parent.getChildren().remove( this );
        }
        this.dead = true;
    }

    /**
     * Requested by superclass. Creates a MouseRadar and returns it
     */
    protected Radar radarForAnimal() {
        return new MouseRadar( 1, environment );
    }

    /**
     * Figures out the best possible move for the current state. If chased hide
     * or flee, otherwise perform random movements
     * @return the next spot to move to
     */
    private Spot getNextSpot() {

        // Get all walkable spots.
        List<Spot> walkableSpots = this.getWalkableSpots();

        // Shuffling is needed because the mouse will always just pick the first
        // spot in the list.
        Collections.shuffle( walkableSpots );

        // If there is no place to go, just stay
        if ( walkableSpots.isEmpty() ) {
            return this.getSpot();
        }
        else {

            // Determines the closest rock, and "flees" over to it if one is
            // present
            List<Spot> rockSpots = this.getSpots( ROCK );
            if ( !rockSpots.isEmpty() ) {

                // Sort the results to get the closest rock
                Collections.sort( rockSpots,
                        new ClosestComparator( this.getSpot() ) );

                // Hide
                return this.flee( rockSpots.get( 0 ), true );
            }
            else {

                // If there is no rocks, then find the closest owls
                List<Spot> owlSpots = this.getSpots( OWL );
                if ( !owlSpots.isEmpty() ) {

                    // Sort the results to get the closest owl

                    Collections.sort( owlSpots,
                            new ClosestComparator( this.getSpot() ) );

                    // Flee
                    return this.flee( owlSpots.get( 0 ), false );

                }
                else {
                    // Perform random move

                    return walkableSpots.get( 0 );
                }
            }

        }
    }

    /**
     * Determine the best walkable spot for fleeing or hiding
     * @param closestSpot the closest spot to either go towards or flee from
     * @param findRock if false the mouse will walk away from the closest spot.
     *            Opposite if true
     * @return the best walkable spot for fleeing or hiding
     */
    Spot flee( Spot closestSpot, boolean findRock ) {

        // Find the best walkable spots
        List<Spot> walkableSpots = this.getWalkableSpots();
        Collections.sort( walkableSpots, new ClosestComparator( closestSpot ) );

        // If the mouse doesn't want to find a rock, reverse the result, so the
        // best spot will be the furthest spot from the closest spot
        if ( !findRock )
            Collections.reverse( walkableSpots );

        // Return the spot or the current if nothing found
        if ( !walkableSpots.isEmpty() ) {
            return walkableSpots.get( 0 );
        }
        else {
            return this.getSpot();
        }
    }

    public List<Mus> getChildren() {
        return this.children;
    }

    /**
     * returns whether or not a mouse is dead.
     */
    public boolean isDead() {
        return this.dead;
    }
}
