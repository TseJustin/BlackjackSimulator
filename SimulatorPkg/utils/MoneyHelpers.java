package SimulatorPkg.utils;

import java.util.ArrayList;
import static SimulatorPkg.utils.HandHelpers.*;

public class MoneyHelpers {

    private MoneyHelpers() {
        // Prevents instantiation
    }

    // Returns true if sum of bets is bigger than bankroll
    public static boolean isRuin(
        ArrayList<Double> player_bets,
        ArrayList<Double> ins_bets,
        double bankroll
    ) {
        double sum = 0;
        for (Double d : player_bets) {
            sum += d;
        }
        for (Double d : ins_bets) {
            sum += d;
        }
        if (sum > bankroll) {
            return true;
        }
        return false;
    }
}
