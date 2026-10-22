package SimulatorPkg.decisions;

import java.util.*;
import static SimulatorPkg.Configs.*;
import SimulatorPkg.charts.*;

public class Insurance {
    private Insurance() {
        // Prevent instantiation
    }
    
    // PRECONDITION: Check for Dealer Ace
    public static ArrayList<Double> getInsuranceBets(
        ArrayList<Double> player_bets,
        double true_count
    ) {
        switch (DEVIATION_MODE) {
            case BS:
                return buyInsurance(BS.willInsure(), player_bets);
            case H17:
                return buyInsurance(H17.willInsure(true_count), player_bets);
            case S17:
                return buyInsurance(S17.willInsure(true_count), player_bets);
            case H17_EXP:
                return buyInsurance(H17_EXP.willInsure(true_count), player_bets);
            case S17_EXP:
                return buyInsurance(S17_EXP.willInsure(true_count), player_bets);
            default:
                throw new IllegalArgumentException("getInsuranceBets failed due to undefined DEVIATION_MODE" + DEVIATION_MODE);
        }
    }

    private static ArrayList<Double> buyInsurance(boolean willBuyInsurance, ArrayList<Double> player_bets) {
        ArrayList<Double> ins_bets = new ArrayList<Double>();

        if (willBuyInsurance) {
            for (Double bet : player_bets) {
                ins_bets.add(0.5*bet);
            }
        }
        return ins_bets;
    }
    
}