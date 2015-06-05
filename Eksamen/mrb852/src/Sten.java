/**
 * Sten: the rock does nothing but protecting mice
 * @author ChristianEnevoldsen
 */
public class Sten extends Actor
{

    // The currently protected mouse
    private Mus protectedMouse = null;

    public Sten( Miljo environment ) {
        super( environment );
        this.tag = Actor.ROCK;
    }

    @Override
    /**
     * Determines if the rock can move to the spot
     */
    public boolean canMoveToSpot( Spot spot ) {
        switch ( spot.getFlag() ) {
            case ROCK:
            case ROCK | OWL:
            case ROCK | MOUSE:
            case ROCK | MOUSE | OWL:
            case ROCK | MICE:
                return false;
            default:
                return true;
        }
    }

    /**
     * gets the currently protected mouse.
     * @return the currently protected mouse
     */
    public Mus getProtectedMouse() {
        return this.protectedMouse;
    }

    /**
     * Asked by mice. If no other mouse is protected by this yet then accept
     * @param mouse the mouse asking for protection
     * @return true if not currently protecting any other
     */
    public boolean requestProtection( Mus mouse ) {
        if ( this.protectedMouse == null ) {
            if ( this.getSpot().getAllActors().contains( mouse ) ) {
                this.protectedMouse = mouse;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes mouse protection
     */
    @Override
    public void step() {
        this.protectedMouse = null;
    }

    /**
     * A rock never dies
     */
    @Override
    boolean isDead() {
        return false;
    }
}
