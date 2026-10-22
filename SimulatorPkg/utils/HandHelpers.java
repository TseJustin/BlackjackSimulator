package SimulatorPkg.utils;

import java.util.ArrayList;

public class HandHelpers {

    // private static String tens = "TJQK";

    private HandHelpers() {
        // Prevent instantiation
    }

    public static boolean hasTenValue(char card) {
        String tens = "TJQK";
        return tens.indexOf(card) != -1;
    }

    public static char convertToTenValue(char card) {
        if (hasTenValue(card)) {
            return 'T';
        }
        return card;
    }

    public static boolean isPair(char card1, char card2) {
        card1 = convertToTenValue(card1);
        card2 = convertToTenValue(card2);
        return card1 == card2;
    }


    // Returns a hand's sum. If the hand is soft, return the soft value with a negative sign.
    public static int getHandSum(ArrayList<Character> hand) {
        int sum = 0;
        boolean has_ace = false;

        for (int i = 0; i < hand.size(); i++) {
            char card = hand.get(i);
            if (card >= '2' && card <= '9') {
                sum += (int)(card - '0');
            } else if (card == 'A') {
                sum += 1;
                has_ace = true;
            } else {
                sum += 10;
            }
        }
        if (has_ace && sum <= 11) {
            return -(sum + 10);
        }
        return sum;
    }
}
