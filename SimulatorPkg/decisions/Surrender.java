package SimulatorPkg.decisions;

import static SimulatorPkg.Configs.DEVIATION_MODE;
import static SimulatorPkg.utils.HandHelpers.convertToTenValue;

import java.util.*;

import SimulatorPkg.charts.BS;
import SimulatorPkg.charts.H17;
import SimulatorPkg.charts.H17_EXP;
import SimulatorPkg.charts.S17;
import SimulatorPkg.charts.S17_EXP;

public class Surrender {
    // Returns true if player will surrender this hand.
    // PRECONDITION: Check for LS beforehand and ensure only 2 cards in player_hand
    public static boolean decideToSurrender(
        int player_sum,
        char dealer_upcard,
        boolean is_pair,
        int running_count,
        double true_count
    ) {
        // true_count = (int)true_count;

        dealer_upcard = convertToTenValue(dealer_upcard);

        switch (DEVIATION_MODE) {
            case BS:
                return BS.willSurrender(player_sum, dealer_upcard);
            case H17:
                return H17.willSurrender(player_sum, dealer_upcard, running_count, true_count);
            case S17:
                return S17.willSurrender(player_sum, dealer_upcard, running_count, true_count);
            case H17_EXP:
                return H17_EXP.willSurrender(player_sum, dealer_upcard, is_pair, running_count, true_count);
            case S17_EXP:
                return S17_EXP.willSurrender(player_sum, dealer_upcard, is_pair, running_count, true_count);
            default:
                throw new IllegalArgumentException("getInsuranceBets failed due to undefined DEVIATION_MODE" + DEVIATION_MODE);
        }
    }
}