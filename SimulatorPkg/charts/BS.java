package SimulatorPkg.charts;

import static SimulatorPkg.Configs.*;

public class BS {
    private BS() {
        // Prevent instantiation
    }
    
    public static boolean willSplit(
        char player_card,
        char dealer_upcard
    ) {
        switch (player_card) {
            case 'A':
                return true;
            case 'T':
                return false;
            case '9':
                if (   dealer_upcard >= '2' && dealer_upcard <= '6'
                    || dealer_upcard >= '8' && dealer_upcard <= '9'
                ) {
                    return true;
                }
                return false;
            case '8':
                return true;
            case '7':
                if (dealer_upcard >= '2' && dealer_upcard <= '7') {
                    return true;
                }
                return false;
            case '6':
                if (   dealer_upcard == '2' && DAS
                    || dealer_upcard >= '3' && dealer_upcard <= '6'
                ) {
                        return true;
                }
                return false;
            case '5':
                return false;
            case '4':
                if (   dealer_upcard == '5' && DAS
                    || dealer_upcard == '6' && DAS
                ) {
                    return true;
                }
                return false;
            case '3':
            case '2':
                if (   dealer_upcard == '2' && DAS
                    || dealer_upcard == '3' && DAS
                    || dealer_upcard >= '4' && dealer_upcard <= '7'
                ) {
                    return true;
                }
                return false;
            default:
                throw new IllegalArgumentException("Split failed due to undefined player_card: " + player_card);
        }
    }

    public static char willDecide(
        int player_sum,
        char dealer_upcard,
        boolean can_double
    ) {
        switch (player_sum) {
            case -21:
            case -20:
                return 'S';
            case -19:
                if (dealer_upcard == '6' && can_double) {
                    return 'D';
                }
                return 'S';
            case -18:
                if (dealer_upcard >= '2' && dealer_upcard <= '6') {
                    if (can_double) {
                        return 'D';
                    }
                    return 'S';
                }
                if (dealer_upcard >= '7' && dealer_upcard <= '8') {
                    return 'S';
                }
                return 'H';
            case -17:
                if (dealer_upcard >= '3' && dealer_upcard <= '6' && can_double) {
                    return 'D';
                }
                return 'H';
            case -16:
            case -15:
                if (dealer_upcard >= '4' && dealer_upcard <= '6'
                    && can_double) {
                    return 'D';
                }
                return 'H';
            case -14:
            case -13:
                if (dealer_upcard >= '5' && dealer_upcard <= '6' && can_double) {
                    return 'D';
                }
                return 'H';
            case -12:
                throw new IllegalArgumentException("Arrived at a soft 12 somehow");
            
            case 16:
            case 15:
            case 14:
            case 13:
                if (dealer_upcard >= '2' && dealer_upcard <= '6') {
                    return 'S';
                }
                return 'H';
            case 12:
                if (dealer_upcard >= '4' && dealer_upcard <= '6') {
                    return 'S';
                }
                return 'H';
            case 11:
                if (can_double) {
                    return 'D';
                }
                return 'H';
            case 10:
                if (dealer_upcard >= '2' && dealer_upcard <= '9' && can_double) {
                    return 'D';
                }
                return 'H';
            case 9:
                if (dealer_upcard >= '3' && dealer_upcard <= '6' && can_double) {
                    return 'D';
                }
                return 'H';
            default:
                if (player_sum >= 17) {
                    return 'S';
                }
                if (player_sum >= 4 && player_sum <= 8) {
                    return 'H';
                }
                throw new IllegalArgumentException("player_sum out of bounds: " + player_sum);
        }
    }

    public static boolean willSurrender(
        int player_sum,
        char dealer_upcard
    ) {
        switch (player_sum) {
            case 16:
                if (   dealer_upcard == '9'
                    || dealer_upcard == 'T'
                    || dealer_upcard == 'A'
                ) {
                    return true;
                }
                return false;
            case 15:
                if (dealer_upcard == 'T') {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public static boolean willInsure() {
        return false;
    }
}
