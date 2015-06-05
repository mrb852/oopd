import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Miljo is the place for all objects within the simulation It manages the spots
 * and invokes steps for every actor within
 * @author ChristianEnevoldsen
 */
public class Miljo
{
    private Random random = new Random();
    public final int width;
    public final int height;

    // An array of all the spots within the environment
    private Spot[][] spots;

    /**
     * Miljo constructor. Creates an environment with a specific size, and
     * predefined actors
     * @param w the width of the environment. 20 width means 20 x height spots
     * @param h the height of the environment.
     * @param mice amount of predefined mice
     * @param rocks amount of predefined rocks
     * @param owls amount of predefined owls
     */
    public Miljo( int w, int h, int mice, int rocks, int owls ) {


        this.width = w;
        this.height = h;
        this.spots = new Spot[ w ][ h ];

        // Initialize the spots
        for ( int y = 0; y < h; ++y ) {
            for ( int x = 0; x < w; ++x ) {
                this.spots[x][y] = new Spot( x, y );
            }
        }

        // Create predefined actors. (Could implement factory design, but no.)
        while ( mice > 0 ) {
            Mus m = new Mus( this );
            if ( m.moveToSpot( randomSpot() ) )
                mice--;
        }

        while ( owls > 0 ) {
            Ugle u = new Ugle( this );
            if ( u.moveToSpot( randomSpot() ) )
                owls--;
        }

        while ( rocks > 0 ) {
            Sten r = new Sten( this );
            if ( r.moveToSpot( randomSpot() ) )
                rocks--;
        }
    }

    /**
     * gets a spot
     * @param x x coordinate of spot
     * @param y y coordinate of spot
     * @return the spot at the coordinate x, y
     */
    public Spot getSpot( int x, int y ) {
        return this.spots[x][y];
    }

    /**
     * Invokes step on every actor
     */
    public void step() {
        for ( Actor a : this.getActors() )
            a.step();
    }

    /**
     * Gets a List with all actors
     * @return list containing all actors
     */
    public List<Actor> getActors() {
        List<Actor> actors = new ArrayList<Actor>();
        actors.addAll( this.getMice() );
        actors.addAll( this.getRocks() );
        actors.addAll( this.getOwls() );

        return actors;
    }

    /**
     * Gets a list containing all rocks
     * @return a list containing all rocks
     */
    public List<Sten> getRocks() {
        List<Sten> rocks = new ArrayList<Sten>();
        for ( Spot s : this.getAllSpots() )
            for ( Actor a : s.getActors( Actor.ROCK ) )
                rocks.add( (Sten) a );
        return rocks;
    }

    /**
     * Gets a list containing all mice.
     * @return a list containing all mice
     */
    public List<Mus> getMice() {
        List<Mus> mice = new ArrayList<Mus>();
        for ( Spot s : this.getAllSpots() )
            for ( Actor a : s.getActors( Actor.MOUSE ) )

                // Sort out dead mice
                if ( !a.isDead() )
                    mice.add( (Mus) a );

        return mice;
    }

    /**
     * Gets a list containing all owls.
     * @return a list containing all owls
     */
    public List<Ugle> getOwls() {
        List<Ugle> owls = new ArrayList<Ugle>();
        for ( Spot s : this.getAllSpots() )
            for ( Actor a : s.getActors( Actor.OWL ) )
                owls.add( (Ugle) a );
        return owls;
    }

    /*********************************************
     * Private helper methods
     *********************************************/

    /**
     * Gets all spots as a List, for easier iteration
     * @return a list containing all spots
     */
    private Set<Spot> getAllSpots() {
        Set<Spot> spots = new HashSet<Spot>();
        for ( int x = 0; x < this.width; x++ )
            for ( int y = 0; y < this.height; ++y )
                spots.add( this.getSpot( x, y ) );
        return spots;
    }

    /**
     * Gets a random spot
     * @return a random spot
     */
    private Spot randomSpot() {
        int x = random.nextInt( width );
        int y = random.nextInt( height );
        return this.spots[x][y];
    }
}
