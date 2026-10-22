package SimulatorPkg.charts;

import static SimulatorPkg.Configs.*;

public class S17_EXP {
    private S17_EXP() {
        // Prevent instantiation
    }
    
    public static boolean willSplit(
        char player_card,
        char dealer_upcard,
        double true_count
    ) {
        throw new UnsupportedOperationException("S17_EXP charts not implemented yet");
    }

    public static char willDecide(
        int player_sum,
        char dealer_upcard,
        boolean can_double,
        int running_count,
        double true_count
    ) {
        throw new UnsupportedOperationException("S17_EXP charts not implemented yet");
    }

    public static boolean willSurrender(
        int player_sum,
        char dealer_upcard,
        boolean is_pair,
        int running_count,
        double true_count
    ) {
        throw new UnsupportedOperationException("S17_EXP charts not implemented yet");
    }

    public static boolean willInsure(double true_count) {
        throw new UnsupportedOperationException("S17_EXP charts not implemented yet");
    }
}
