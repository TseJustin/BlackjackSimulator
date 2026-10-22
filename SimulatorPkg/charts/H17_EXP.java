package SimulatorPkg.charts;

import static SimulatorPkg.Configs.*;

public class H17_EXP {
    private H17_EXP() {
        // Prevent instantiation
    }
    
    public static boolean willSplit(
        char player_card,
        char dealer_upcard,
        double true_count
    ) {
        switch (player_card) {
            case 'A':
                return true;
            case 'T':
                if (   dealer_upcard == '2' && true_count >= 10
                    || dealer_upcard == '3' && true_count >= 8
                    || dealer_upcard == '4' && true_count >= 6
                    || dealer_upcard == '5' && true_count >= 5
                    || dealer_upcard == '6' && true_count >= 4
                ) {
                    return true;
                }
                return false;
            case '9':
                if (   dealer_upcard >= '2' && dealer_upcard <= '6'
                    || dealer_upcard == '7' && DAS  && true_count >= 3
                    || dealer_upcard == '7' && !DAS && true_count >= 7
                    || dealer_upcard >= '8' && dealer_upcard <= '9'
                    || dealer_upcard == 'A' && DAS  && true_count >= 2
                    || dealer_upcard == 'A' && !DAS && true_count >= 3
                ) {
                    return true;
                }
                return false;
            case '8':
                if (   dealer_upcard == 'T' && DAS  && true_count >= 8
                    || dealer_upcard == 'T' && !DAS && true_count >= 5
                ) {
                    return false;
                }
                return true;
            case '7':
                if (   dealer_upcard >= '2' && dealer_upcard <= '7'
                    || dealer_upcard == '8' && DAS && true_count >= 4
                ) {
                    return true;
                }
                return false;
            case '6':
                if (   dealer_upcard == '2' && DAS
                    || dealer_upcard == '2' && !DAS && true_count >= 1
                    || dealer_upcard >= '3' && dealer_upcard <= '6'
                ) {
                    return true;
                }
                return false;
            case '5':
                return false;
            case '4':
                if (   dealer_upcard == '3' && DAS && true_count >= 7
                    || dealer_upcard == '4' && DAS && true_count >= 3
                    || dealer_upcard == '5' && DAS
                    || dealer_upcard == '6' && DAS
                ) {
                    return true;
                }
                return false;
            case '3':
                if (   dealer_upcard == '2' && DAS
                    || dealer_upcard == '2' && !DAS && true_count >= 8
                    || dealer_upcard == '3' && DAS
                    || dealer_upcard == '3' && !DAS && true_count >= 3
                    || dealer_upcard >= '4' && dealer_upcard <= '7'
                    || dealer_upcard >= '8' && DAS  && true_count >= 5
                ) {
                    return true;
                }
                return false;
            case '2':
                if (   dealer_upcard == '2' && DAS
                    || dealer_upcard == '2' && !DAS && true_count >= 7
                    || dealer_upcard == '3' && DAS
                    || dealer_upcard == '3' && !DAS && true_count >= 3
                    || dealer_upcard >= '4' && dealer_upcard <= '7'
                    || dealer_upcard >= '8' && DAS  && true_count >= 5
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
        boolean can_double,
        int running_count,
        double true_count
    ) {
        switch (player_sum) {
            case -21:
                if (   dealer_upcard == '4' && true_count >= 10 && can_double
                    || dealer_upcard == '5' && true_count >= 8 && can_double
                    || dealer_upcard == '6' && true_count >= 7 && can_double
                ) {
                    return 'D';
                }
                return 'S';
            case -20:
                if (   dealer_upcard == '2' && true_count >= 10 && can_double
                    || dealer_upcard == '3' && true_count >= 8 && can_double
                    || dealer_upcard == '4' && true_count >= 6 && can_double
                    || dealer_upcard == '5' && true_count >= 5 && can_double
                    || dealer_upcard == '6' && true_count >= 4 && can_double
                ) {
                    return 'D';
                }
                return 'S';
            case -19:
                if (   dealer_upcard == '2' && true_count >= 8 && can_double
                    || dealer_upcard == '3' && true_count >= 5 && can_double
                    || dealer_upcard == '4' && true_count >= 3 && can_double
                    || dealer_upcard == '5' && true_count >= 1 && can_double
                    || dealer_upcard == '6' && running_count >= 0 && can_double
                ) {
                    return 'D';
                }
                return 'S';
            case -18:
                if (dealer_upcard == '2') {
                    if (running_count < 0) {
                        if (can_double) {
                            return 'D'; // D or H???
                        }
                        return 'H';
                    }
                    if (can_double) {
                        return 'D';
                    }
                    return 'S';
                }
                if (dealer_upcard >= '3' && dealer_upcard <= '6') {
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
                if (   dealer_upcard == '2' && true_count >= 1 && can_double
                    || dealer_upcard >= '3' && dealer_upcard <= '6' && can_double) {
                    return 'D';
                }
                return 'H';
            case -16:
                if (   dealer_upcard == '3' && true_count >= 4 && can_double
                    || dealer_upcard >= '4' && dealer_upcard <= '6' && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case -15:
                if (   dealer_upcard == '3' && true_count >= 7 && can_double
                    || dealer_upcard >= '4' && dealer_upcard <= '6' && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case -14:
                if (   dealer_upcard == '3' && true_count >= 7 && can_double
                    || dealer_upcard == '4' && true_count >= 2 && can_double
                    || dealer_upcard >= '5' && dealer_upcard <= '6' && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case -13:
                if (   dealer_upcard == '2' && true_count >= 12 && can_double
                    || dealer_upcard == '3' && true_count >= 7 && can_double
                    || dealer_upcard == '4' && true_count >= 3 && can_double
                    || dealer_upcard == '5' && running_count >= 0 && can_double
                    || dealer_upcard == '6' && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case -12:
                throw new IllegalArgumentException("Arrived at a soft 12 somehow");
            
            case 16:
                if (   dealer_upcard >= '2' && dealer_upcard <= '6'
                    || dealer_upcard == '7' && true_count >= 8
                    || dealer_upcard == '8' && true_count >= 7
                    || dealer_upcard == '9' && true_count >= 4
                    || dealer_upcard == 'T' && running_count > 0
                    || dealer_upcard == 'A' && true_count >= 3
                ) {
                    return 'S';
                }
                return 'H';
            case 15:
                if (   dealer_upcard >= '2' && dealer_upcard <= '6'
                    || dealer_upcard == '7' && true_count >= 11
                    || dealer_upcard == '8' && true_count >= 10
                    || dealer_upcard == '9' && true_count >= 8
                    || dealer_upcard == 'T' && true_count >= 4
                    || dealer_upcard == 'A' && true_count >= 5
                ) {
                    return 'S';
                }
                return 'H';
            case 14:
                if (   dealer_upcard >= '2' && dealer_upcard <= '6'
                    || dealer_upcard == 'A' && true_count >= 10
                ) {
                    return 'S';
                }
                return 'H';
            case 13:
                if (   dealer_upcard == '2' && true_count > -1
                    || dealer_upcard >= '3' && dealer_upcard <= '6'
                ) {
                    return 'S';
                }
                return 'H';
            case 12:
                if (   dealer_upcard == '2' && true_count >= 3
                    || dealer_upcard == '3' && true_count >= 2
                    || dealer_upcard == '4' && running_count >= 0
                    || dealer_upcard == '5' && true_count > -2
                    || dealer_upcard == '6'
                ) {
                    return 'S';
                }
                return 'H';
            case 11:
                if (   dealer_upcard >= '2' && dealer_upcard <= '9' && can_double
                    || dealer_upcard == 'T' && can_double
                    || dealer_upcard == 'A' && true_count > -1 && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case 10:
                if (   dealer_upcard >= '2' && dealer_upcard <= '8' && can_double
                    || dealer_upcard == '9' && true_count > -1 && can_double
                    || dealer_upcard == 'T' && true_count >= 4 && can_double
                    || dealer_upcard == 'A' && true_count >= 3 && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case 9:
                if (   dealer_upcard == '2' && true_count >= 1 && can_double
                    || dealer_upcard >= '3' && dealer_upcard <= '6' && can_double
                    || dealer_upcard == '7' && true_count >= 3 && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case 8:
                if (   dealer_upcard == '2' && true_count >= 15 && can_double
                    || dealer_upcard == '3' && true_count >= 9 && can_double
                    || dealer_upcard == '4' && true_count >= 6 && can_double
                    || dealer_upcard == '5' && true_count >= 4 && can_double
                    || dealer_upcard == '6' && true_count >= 2 && can_double
                ) {
                    return 'D';
                }
                return 'H';
            case 7:
                if (   dealer_upcard == '5' && true_count >= 10 && can_double
                    || dealer_upcard == '6' && true_count >= 9 && can_double
                ) {
                    return 'D';
                }
                return 'H';
            default:
                if (player_sum >= 17) {
                    return 'S';
                }
                if (player_sum >= 4 && player_sum <= 6) {
                    return 'H';
                }
                throw new IllegalArgumentException("player_sum out of bounds: " + player_sum);
        }
    }

    public static boolean willSurrender(
        int player_sum,
        char dealer_upcard,
        boolean is_pair,
        int running_count,
        double true_count
    ) {
        switch (player_sum) {
            case 17: 
                if (dealer_upcard == 'A') {
                    return true;
                }
                return false;
            case 16:
                if (!is_pair) {
                    if (   dealer_upcard == '8' && true_count >= 4
                        || dealer_upcard == '9' && true_count > -1
                        || dealer_upcard == 'T'
                        || dealer_upcard == 'A'
                    ) {
                        return true;
                    }
                    return false;
                }
                if (   dealer_upcard == 'T' && true_count >= 1
                    || dealer_upcard == 'A'
                ) {
                    return true;
                }
                return false;
            case 15:
                if (   dealer_upcard == '8' && true_count >= 7
                    || dealer_upcard == '9' && true_count >= 2
                    || dealer_upcard == 'T' && running_count >= 0
                    || dealer_upcard == 'A' && true_count >= -1
                ) {
                    return true;
                }
                return false;
            case 14:
                if (   dealer_upcard == '9' && true_count >= 6
                    || dealer_upcard == 'T' && true_count >= 4
                    || dealer_upcard == 'A' && true_count >= 4
                ) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public static boolean willInsure(double true_count) {
        if (   NUM_DECKS > 2  && true_count >= 3
            || NUM_DECKS == 2 && true_count >= 2.5
            || NUM_DECKS == 1 && true_count >= 1.5
        ) {
            return true;
        }
        return false;
    }
}
