package SimulatorPkg.decisions;

import static SimulatorPkg.Configs.DAS;
import static SimulatorPkg.Configs.DEVIATION_MODE;
import static SimulatorPkg.utils.HandHelpers.convertToTenValue;

import SimulatorPkg.charts.BS;
import SimulatorPkg.charts.H17;
import SimulatorPkg.charts.H17_EXP;
import SimulatorPkg.charts.S17;
import SimulatorPkg.charts.S17_EXP;

public class PlayingDecision {
    // Returns either 'D', 'H', or 'S' for Double, Hit, and Stand
    public static char getPlayingDecision(
        int player_sum,
        char dealer_upcard,
        boolean from_split,
        boolean is_two_cards,
        int running_count,
        double true_count
    ) {
        // true_count = (int)true_count;

        boolean can_double = is_two_cards && (!from_split || DAS);
        dealer_upcard = convertToTenValue(dealer_upcard);
        
        switch (DEVIATION_MODE) {
            case BS:
                return BS.willDecide(player_sum, dealer_upcard, can_double);
            case H17:
                return H17.willDecide(player_sum, dealer_upcard, can_double, running_count, true_count);
            case S17:
                return S17.willDecide(player_sum, dealer_upcard, can_double, running_count, true_count);
            case H17_EXP:
                return H17_EXP.willDecide(player_sum, dealer_upcard, can_double, running_count, true_count);
            case S17_EXP:
                return S17_EXP.willDecide(player_sum, dealer_upcard, can_double, running_count, true_count);
            default:
                throw new IllegalArgumentException("getInsuranceBets failed due to undefined DEVIATION_MODE" + DEVIATION_MODE);
        }
    }
}
