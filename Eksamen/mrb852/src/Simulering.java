import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Helper class, that acts like a c#-struct with benefits. Is responsible for
 * storing data for a round.
 * @author ChristianEnevoldsen
 */
class RoundData
{
    public final int round, mice, rocks, owls;

    public void println() {
        System.out.printf( "Round: %d, Mice: %d, Rocks %d, Owls %d\n", round,
                mice, rocks, owls );
    }

    public RoundData( int i, int m, int r, int o ) {
        this.round = i;
        this.mice = m;
        this.rocks = r;
        this.owls = o;
    }
}

/**
 * Is responsible for user input and output, and managing the simulation
 * @author ChristianEnevoldsen
 */
public class Simulering
{

    private Miljo miljo;

    // Used for the RoundData model
    private int currentStep = 0;

    // I/o
    private Scanner scanner = new Scanner( System.in );

    // A List containing the data for the steps table.
    private List<RoundData> rounds = new ArrayList<RoundData>();

    public static void main( String[] args ) {
        new Simulering();
    }

    public Simulering() {

        // Miljo - Width, height, Mice, rocks, owls.
        this.miljo = new Miljo( 20, 20, 150, 10, 2 );
        startSimulering();
    }

    /**
     * ends the simulation, and prints the data table
     */
    private void end() {

        for ( RoundData data : rounds ) {
            data.println();
        }
    }

    /**
     * Starts the simulation and prompts user what to do.
     */
    private void startSimulering() {
        char c = ' ';
        do {
            printMiljo( miljo );
            this.step( 1 );
            System.out.print( "\nQ for quit\n" );
            String s = scanner.nextLine();
            if ( s.length() > 0 ) {
                c = s.charAt( 0 );
            }
        } while ( c != 'q' && c != 'Q' );
        end();
    }

    /**
     * Prints out the environment for the current step
     * @param miljo the environment to print
     */
    private void printMiljo( Miljo miljo ) {
        for ( int y = 0; y < miljo.height; ++y ) {
            for ( int x = 0; x < miljo.width; ++x ) {
                int field = ( miljo.getSpot( x, y ).getFlag() );
                System.out.printf( "%d ", convert( field ) );
            }
            System.out.println();
        }
    }

    /**
     * Performs a number of steps in the simulation
     * @param c count - how many times the step will be triggered
     */
    private void step( int c ) {
        for ( int i = 0; i < c; ++i ) {
            this.miljo.step();

            this.currentStep++;

            // Store the info for this step.
            this.rounds.add( new RoundData( this.currentStep, this.miljo
                    .getMice().size(), this.miljo.getRocks().size(), this.miljo
                    .getOwls().size() ) );
        }

    }

    /**
     * Helper method convert flags to assignment suggestion
     * @param flag the flag
     * @return converted flag
     */
    private int convert( int flag ) {
        switch ( flag ) {
            case 2:
                return 1;
            case 4:
                return 2;
            case 8:
                return 3;
            case 6:
                return 4;
            case 10:
                return 5;
            case 12:
                return 6;
            case 128:
                return 7;
            case 14:
                return 8;
            case 130:
                return 9;
            default:
                return flag;
        }

    }
}
