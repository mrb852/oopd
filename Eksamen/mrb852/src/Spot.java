import java.util.ArrayList;
import java.util.List;

/**
 * The spot acts like a container for Actors on a specific spot, and holds the
 * coordinate for the spot
 * @author ChristianEnevoldsen
 */
public class Spot
{

    public final int x;
    public final int y;

    // A List containing all actors on the spot
    private List<Actor> actors = new ArrayList<Actor>();

    // Determines what is on the spot
    private int flag = 0;

    public Spot( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds an actor to this spot.
     * @param actor the actor to add
     */
    public void addActor( Actor actor ) {

        // If there are mice on this spot disable the MOUSE flag and enable the
        // MICE flag
        this.actors.add( actor );
        if ( ( ( actor.tag & Actor.MOUSE ) == Actor.MOUSE )
                && ( this.flag & Actor.MOUSE ) == Actor.MOUSE ) {
            this.flag ^= Actor.MOUSE;
            this.flag |= Actor.MICE;
        }
        else {
            this.flag |= actor.tag;
        }

    }

    /**
     * Removes an actor from this spot
     * @param actor the actor to remove
     */
    public void removeActor( Actor actor ) {

        this.actors.remove( actor );

        // Turn back the MOUSE flag if there is only one mouse on board
        if ( ( actor.tag == Actor.MOUSE )
                && ( this.flag & Actor.MICE ) == Actor.MICE ) {
            this.flag ^= Actor.MICE;
            this.flag |= Actor.MOUSE;
            return;
        }
        this.flag ^= actor.tag;

    }

    /**
     * Determines if the type is currently on the spot
     * @param type the type to search for
     * @return true if the type is currently on the spot
     */
    public boolean contains( int type ) {
        return ( this.flag & type ) == type;
    }

    /**
     * Gets a list containing actors of a specific type
     * @param type the type
     * @return a list containing actors of a specific type
     */
    public List<Actor> getActors( int type ) {

        // Handle mice confusion
        if ( ( type == Actor.MICE && this.contains( Actor.MOUSE ) ) )
            type = Actor.MOUSE;

        // Return a copy of this internal list with the other types removed
        List<Actor> l = new ArrayList<Actor>( this.actors );
        for ( Actor a : this.actors ) {
            if ( ( a.tag & type ) != type ) {
                l.remove( a );
            }
        }
        return l;
    }

    /**
     * Gets a list containing all actors currently on the spot
     * @return a list containing all actors currently on the spot
     */
    public List<Actor> getAllActors() {
        return this.actors;
    }

    /**
     * Gets the flag
     * @return the flag
     */
    public int getFlag() {
        return this.flag;
    }

}
