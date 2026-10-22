package SimulatorPkg.decisions;

import static SimulatorPkg.Configs.*;
import static SimulatorPkg.utils.HandHelpers.convertToTenValue;

import SimulatorPkg.charts.BS;
import SimulatorPkg.charts.H17;
import SimulatorPkg.charts.H17_EXP;
import SimulatorPkg.charts.S17;
import SimulatorPkg.charts.S17_EXP;

public class Split {
    private Split() {
        // Prevent instantiation
    }
    
    public static boolean decideToSplit(
        char player_card,
        char dealer_upcard,
        double true_count
    ) {
        // true_count = (int)true_count;

        player_card = convertToTenValue(player_card);
        dealer_upcard = convertToTenValue(dealer_upcard);

        switch (DEVIATION_MODE) {
            case BS:
                return BS.willSplit(player_card, dealer_upcard);
            case H17:
                return H17.willSplit(player_card, dealer_upcard, true_count);
            case S17:
                return S17.willSplit(player_card, dealer_upcard, true_count);
            case H17_EXP:
                return H17_EXP.willSplit(player_card, dealer_upcard, true_count);
            case S17_EXP:
                return S17_EXP.willSplit(player_card, dealer_upcard, true_count);
            default:
                throw new IllegalArgumentException("getInsuranceBets failed due to undefined DEVIATION_MODE" + DEVIATION_MODE);
        }
    }
}
