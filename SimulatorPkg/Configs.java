package SimulatorPkg;

import java.util.ArrayList;
import java.util.Arrays;

public final class Configs {
    private Configs() {
        // restrict instantiation
    }
    public static enum DEVIATION_MODES { BS, H17, S17, H17_EXP, S17_EXP }
    
    public static final boolean DEBUG_MODE = false;
    public static int NUM_ITERS = 1000;
    public static int NUM_SHOES = 10000;
    public static final boolean ROUND_FOR_DECK_ESTIMATION = true;
    public static int NUM_PROGRESS_UDPDATES = 10;
    
    public static final double STARTING_BANKROLL = 10000.00;
    public static int ROUNDS_PER_HR = 100;
    public static int TOTAL_PLAYERS = 1; // Including self
    
    public static DEVIATION_MODES DEVIATION_MODE = DEVIATION_MODES.H17_EXP;
    public static final int EST_MODE = 2; // 1=whole, 2=half, 4=quarter
    
    public static final int NUM_DECKS = 6;
    public static final double PEN = 1.5; // How many decks NOT dealt
    public static final boolean IS_H17 = true;
    public static final boolean DAS = true;
    
    public static final boolean RSA = true;
    public static final int MAX_HANDS_FROM_SPLIT = 4;
    public static final boolean LS = false;

    
    public static ArrayList<Double> getBet(double true_count_double) {
        int true_count = (int)true_count_double;
        ArrayList<Double> b = new ArrayList<>();
        if (TOTAL_PLAYERS < 1) {
            throw new IllegalArgumentException("Must have at least 1 player at the table!");
        }
        for (int i = 1; i < TOTAL_PLAYERS; i++) {
            b.add(0.0);
        }
        
        /*Custom Spread */
        if (true_count <= -3) {
            b.add(5.0);
            return b;
        }
        if (true_count == -2) {
            b.add(5.0);
            return b;
        }
        if (true_count == -1) {
            b.add(5.0);
            return b;
        }
        if (true_count == 0) {
            b.add(25.0);
            return b;
        }
        if (true_count == 1) {
            b.add(25.0);
            return b;
        }
        if (true_count == 2) {
            b.add(25.0);
            b.add(25.0);
            return b;
        }
        if (true_count == 3) {
            b.add(50.0);
            b.add(50.0);
            return b;
        }
        if (true_count == 4) {
            b.add(75.0);
            b.add(75.0);
            return b;
        }
        if (true_count == 5) {
            b.add(100.0);
            b.add(100.0);
            return b;
        }
        if (true_count == 6) {
            b.add(100.0);
            b.add(100.0);
            return b;
        }
        if (true_count >= 7) {
            b.add(100.0);
            b.add(100.0);
            return b;
        }
        throw new IllegalStateException("True_count " + true_count + " not accounted for!");
    }

    public static boolean isWongOut(int running_count, double true_count) {
        if (running_count <= -6) {
            return true;
        }
        return false;
    }

    public static final Character[] CUSTOM_DECK = {};
    public static ArrayList<Character> deck_2 = new ArrayList<Character>(Arrays.asList(CUSTOM_DECK));
}