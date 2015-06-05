public abstract class Actor
{
    /**
     * Bit flags to help identify the different actors
     */
    public static final int ROCK = 1 << 1;
    public static final int OWL = 1 << 2;
    public static final int MOUSE = 1 << 3;
    public static final int MICE = 1 << 7;

    /**
     * The current spot ie. a field in the environment.
     */
    private Spot spot = null;

    /**
     * The tag will be one of the bit flags
     */
    public int tag;

    /**
     * A reference to the current environment, which will be used on radars for
     * instance.
     */
    public final Miljo environment;

    /**
     * Constructor.
     * @param environment the environment which this actor will be presented to.
     */
    public Actor( Miljo environment ) {
        this.environment = environment;
    }

    /**
     * Tries to move an actor a spot
     * @param spot the spot to move to
     * @return true if moved
     */
    public boolean moveToSpot( Spot spot ) {
        if ( !this.isDead() ) {
            if ( canMoveToSpot( spot ) ) {

                // Remember to remove self from current spot
                if ( this.spot != null )
                    this.spot.removeActor( this );

                spot.addActor( this );
                this.spot = spot;

                return true;
            }
        }

        return false;
    }

    /**
     * Returns the current spot (Can be null)
     * @return the current spot
     */
    public Spot getSpot() {
        return this.spot;
    }

    /**
     * Returns whether or not the actor is dead
     * @return true if the actor is dead
     */
    abstract boolean isDead();

    /**
     * Determines whether or not the actor can move to a spot
     * @param spot the spot to move to
     * @return true if the actor can move to the spot
     */
    abstract boolean canMoveToSpot( Spot spot );

    /**
     * Updates internal states and, before performing a step, and invokes the
     * moveToSpot function for those who moves
     */
    abstract void step();
}
