import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class UnitTests
{
    @Test
    /**
     * tests the mouse ability to flee. 
     * The mouse should flee to the most far spot away from the closest owl.
     */
    public void testFlee() {

        // Sets up a 3 x 3 grid "miljo"
        // With one mouse and one owl
        Miljo miljo = new Miljo( 3, 3, 0, 0, 0 );
        Mus m = new Mus( miljo );
        Ugle u = new Ugle( miljo );

        u.moveToSpot( miljo.getSpot( 0, 0 ) );
        m.moveToSpot( miljo.getSpot( 0, 1 ) );

        miljo.step();
        assertEquals( 1, m.getSpot().x );
        assertEquals( 2, m.getSpot().y );

    }

    @Test
    /**
     * Tests the mouse ability to get protected 
     */
    public void testProtect() {
        // Sets up a 3 x 3 grid "miljo"
        // With 1 mouse, 1 owl and 1 rocks

        boolean madeMice = false;
        do {
            Miljo miljo = new Miljo( 3, 3, 0, 0, 0 );
            Mus m = new Mus( miljo );
            Mus m2 = new Mus( miljo );
            Ugle u = new Ugle( miljo );
            Sten r = new Sten( miljo );
            m.moveToSpot( miljo.getSpot( 0, 1 ) );
            m2.moveToSpot( m.getSpot() );
            u.moveToSpot( miljo.getSpot( 1, 2 ) );
            r.moveToSpot( miljo.getSpot( 0, 2 ) );
            miljo.step();

            if ( m.children.size() == 0 && m2.children.size() == 0 ) {
                madeMice = false;
                assertEquals( true, m2.isDead() || m.isDead() );

                // To show that mice cannot flee when an owl is on the same spot
                // Which means that hiding is a useless strategy. :)
                miljo.step();
                assertEquals( true, m2.isDead() && m.isDead() );

            }

        } while ( madeMice );

    }

    /**
     * Makes sure no more than 2 mice can occur at the same spot
     */
    @Test
    public void maxTwoMice() {
        Miljo m = new Miljo( 1, 1, 2, 0, 0 );
        Mus m1 = new Mus( m );
        m1.moveToSpot( m.getSpot( 0, 0 ) );
        assertEquals( 2, m.getActors().size() );
    }

    @Test
    /**
     * Tests the radar to see if it gets they right neighbor spots
     */
    public void testOwlRadar() {
        Miljo m = new Miljo( 5, 5, 0, 0, 0 );
        Ugle u = new Ugle( m );
        u.moveToSpot( m.getSpot( 2, 2 ) );
        u.setRadarRadius( u.radius );
        assertEquals( 24, u.useRadar( false ).size() );
    }

    @Test
    /**
     * Tests the owls ability to move towards the closest mouse
     * This is reversed for the mice when they flee
     */
    public void testClosestSpot() {
        Miljo m = new Miljo( 10, 10, 0, 0, 0 );
        Spot spot = m.getSpot( 5, 5 );

        // Create some spots
        List<Spot> spots = new ArrayList<Spot>();
        for ( int x = 3; x < 10; x += 3 ) {
            for ( int y = 2; y < 7; y += 2 ) {
                spots.add( m.getSpot( x, y ) );
            }
        }

        // Closest should be (6, 4)
        Spot testSpot = spots.get( 4 );
        assertEquals( new Point( 6, 4 ), new Point( testSpot.x, testSpot.y ) );
        Collections.sort( spots, new ClosestComparator( spot ) );
        assertEquals( testSpot, spots.get( 0 ) );
    }

    @Test
    /**
     * Tests that the actor never sleeps
     */
    public void testNoSleep() {
        Miljo m = new Miljo( 20, 20, 150, 100, 1 );
        Ugle u = m.getOwls().get( 0 );
        Spot lastSpot = u.getSpot();
        while ( !m.getMice().isEmpty() ) {
            m.step();
            assertEquals( false, lastSpot == u.getSpot() );
            lastSpot = u.getSpot();
        }
    }

    @Test
    /**
     * Tests if a spot updates its flag, when calling removeActor and addActor
     */
    public void testSpotFlag() {

        Spot spot = new Spot( 0, 0 );

        // Adding mouse
        spot.addActor( new Mus( null ) );

        // Should be MOUSE
        assertEquals( Actor.MOUSE, spot.getFlag() );

        spot.addActor( new Mus( null ) );

        // Spot updates to MICE
        assertEquals( Actor.MICE, spot.getFlag() );

        // Remove one mouse, and make sure the spot updates
        spot.removeActor( spot.getAllActors().get( 0 ) );
        assertEquals( Actor.MOUSE, spot.getFlag() );
    }

    @Test
    /**
     * Tests the mice ability to repopulate
     */
    public void testMiceChildren() {
        Miljo m = new Miljo( 2, 1, 0, 0, 0 );
        Mus m1 = new Mus( m );
        Mus m2 = new Mus( m );
        m1.moveToSpot( m.getSpot( 0, 0 ) );
        m2.moveToSpot( m.getSpot( 0, 0 ) );
        m1.spawnMouse();
        assertEquals( 3, m.getMice().size() );
    }

    @Test
    /**
     * Shows that only one owl can occur on a field at a time
     */
    public void testMaxOneOwl() {
        Miljo m = new Miljo( 1, 1, 0, 0, 1 );
        new Ugle( m ).moveToSpot( m.getSpot( 0, 0 ) );
        assertEquals( 1, m.getOwls().size() );
    }

}
