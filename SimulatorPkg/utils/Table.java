package SimulatorPkg.utils;

import java.util.ArrayList;
import java.util.Collections;

import static SimulatorPkg.Configs.*;
import static SimulatorPkg.decisions.Insurance.*;
import static SimulatorPkg.decisions.PlayingDecision.*;
import static SimulatorPkg.decisions.Split.*;
import static SimulatorPkg.decisions.Surrender.*;

import static SimulatorPkg.utils.HandHelpers.*;
import static SimulatorPkg.utils.MathHelpers.*;
import static SimulatorPkg.utils.MoneyHelpers.*;

public class Table {
    public double bankroll;
    public int running_count = 0;
    public double true_count = 0;
    public double divisor = NUM_DECKS;
    public int discards = 0;
    public ArrayList<Character> deck = new ArrayList<>();

    public ArrayList<Character> dealer_hand;
    public ArrayList<ArrayList<Character>> player_hands;
    public ArrayList<Double> player_bets;
    public ArrayList<Double> ins_bets;
    public boolean let_dealer_play = false;

    public Table() {
        bankroll = STARTING_BANKROLL;
    }

    public void resetTable() {
        running_count = 0;
        true_count = 0;
        divisor = NUM_DECKS;
        discards = 0;
        shuffleDeck();
    }

    public void shuffleDeck() {
        deck.clear();
        if (CUSTOM_DECK.length == 0) {
            for (int i = 0; i < NUM_DECKS; i++) {
                char[] card_values = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
                for (char c : card_values) {
                    for (int j = 0; j < 4; j++) {
                        deck.add(c);
                    }
                }
            }
            Collections.shuffle(deck);
        } else {
            deck = deck_2;
        }
    }

    // Returns number of decks put in the discard tray, rounded to nearest whole/half/quarter
    // Est: 1=whole, 2=half, 4=quarter
    public void updateDivisorAndTrueCount() {
        double space = (double)(NUM_DECKS * 52 - discards);
        if (EST_MODE == 1) {
            if (ROUND_FOR_DECK_ESTIMATION) {
                divisor = Math.round(space/52);
            } else {
                divisor = Math.ceil(space/52);
            }
        }
        if (EST_MODE == 2) {
            if (ROUND_FOR_DECK_ESTIMATION) {
                divisor = Math.round(space/26)/2.0;
            } else {
                divisor = Math.ceil(space/26)/2.0;
            }
        }
        if (EST_MODE == 4) {
            if (ROUND_FOR_DECK_ESTIMATION) {
                divisor = Math.round(space/13)/4.0;
            } else {
                divisor = Math.ceil(space/13)/4.0;
            }
        }
        true_count = running_count/divisor;
    }

    // Puts a hand in the discard tray
    public void discardHand(ArrayList<Character> hand) {
        discards += hand.size();
        hand.clear();
        updateDivisorAndTrueCount();
    }

    // Adds a card to the hand
    public void addCard(
        ArrayList<Character> hand,
        boolean is_face_up
    ) {
        char card = deck.remove(0);
        if (is_face_up) {
            if (card >= '2' && card <= '6') {
                running_count++;
            } else if (hasTenValue(card) || card == 'A') {
                running_count--;
            }
        }
        hand.add(card);
        updateDivisorAndTrueCount();
    }
    
    public void startRound() {
        dealer_hand = new ArrayList<>();
        player_hands = new ArrayList<ArrayList<Character>>();
        player_bets = new ArrayList<>();
        ins_bets = new ArrayList<>();
        let_dealer_play = false;
    }

    public void setPlayerBets() {
        updateDivisorAndTrueCount();
        player_bets = getBet(true_count);
    }

    public void giveDealerCards() {
        if (dealer_hand.size() != 0) {
            throw new IllegalStateException("Dealer started round with " + dealer_hand.size() + "cards!");
        }
        addCard(dealer_hand, true);
        addCard(dealer_hand, false);
    }

    public void givePlayerCards() {
        for (int i = 0; i < player_bets.size(); i++) {
            player_hands.add(new ArrayList<>());
            addCard(player_hands.get(i), true);
            addCard(player_hands.get(i), true);
        }
        if (player_bets.size() != player_hands.size()) {
            throw new IllegalStateException("player_bets is size " + player_bets.size() + ". player_hands is size " + player_hands.size());
        }
    }

    public void checkInsurance() {
        if (dealer_hand.get(0) == 'A') {
            ins_bets = getInsuranceBets(player_bets, true_count);
        }
    }

    public boolean checkDealerBJ() {
        return getHandSum(dealer_hand) == -21;
    }

    public void payoutPlayerBJ() {
        for (int i = 0; i < player_hands.size(); i++) {
            int player_hand_sum = getHandSum(player_hands.get(i));

            if (player_hand_sum == -21) {
                bankroll += (player_bets.get(i)*1.5);
                discardHand(player_hands.get(i));
            }
        }
    }

    public void finishRound(boolean dealer_bj) {
        calculatePayouts(dealer_bj);
        discardHand(dealer_hand);
    }

    public void calculatePayouts(boolean dealer_bj) {
        printTable();
        int dealer_sum = Math.abs(getHandSum(dealer_hand));
        for (int i = 0; i < player_hands.size(); i++) {
            ArrayList<Character> p_hand = player_hands.get(i);
            double bet = player_bets.get(i);
            if (p_hand.size() > 0) {
                int player_sum = Math.abs(getHandSum(p_hand));
                if (player_sum > 21) {
                    bankroll -= bet;
                } else if (dealer_sum > 21 || player_sum > dealer_sum) {
                    bankroll += bet;
                } else if (dealer_sum > player_sum) {
                    bankroll -= bet;
                }
            }
            discardHand(p_hand);
        }

        for (Double ins_bet : ins_bets) {
            if (dealer_bj) {
                bankroll += 2*ins_bet;
            } else {
                bankroll -= ins_bet;
            }
        }
    }

    public boolean wongOut() {
        return isWongOut(running_count, true_count);
    }

    public void playHands() {
        int cur_hands_from_split = 1;
        for (int i = 0; i < player_hands.size(); i++) {
            boolean from_split = false;
            boolean is_two_cards = true;

            // Quick References
            ArrayList<Character> p_hand = player_hands.get(i);
            if (p_hand.size() == 0) {
                continue;
            }
            if (p_hand.size() == 1) {
                from_split = true;
                addCard(p_hand, true);
                if (p_hand.get(0) == 'A') {
                    let_dealer_play = true;
                    if (p_hand.get(1) != 'A'
                        || !RSA
                        || RSA && p_hand.get(1) == 'A' && cur_hands_from_split == MAX_HANDS_FROM_SPLIT) {
                        continue;
                    }
                }
            } else {
                cur_hands_from_split = 1;
            }

            if (p_hand.size() != 2) {
                throw new IllegalStateException("p_hand " + p_hand + " should only be 2 cards");
            }
            Character p0 = p_hand.get(0);
            Character p1 = p_hand.get(1);
            Character d0 = dealer_hand.get(0);
            double bet = player_bets.get(i);

            // Decide on Surrender
            if (LS && !from_split
                && decideToSurrender(
                    getHandSum(p_hand),
                    d0,
                    isPair(p0, p1),
                    running_count,
                    true_count)
            ) {
                bankroll -= (0.5*bet);
                player_bets.set(i, 0.0);
                discardHand(p_hand);
                continue;
            }

            // Decide on Split
            if (isPair(p0, p1)
                && cur_hands_from_split < MAX_HANDS_FROM_SPLIT
                && decideToSplit(p0, d0, true_count)
            ) {
                cur_hands_from_split++;
                ArrayList<Character> new_hand = new ArrayList<>();
                new_hand.add(p_hand.remove(1));
                player_hands.add(i + 1, new_hand);
                player_bets.add(i + 1, bet);
                i--;
                continue;
            }

            // Decide on Doubling/Hitting/Standing
            char decision = getPlayingDecision(
                getHandSum(p_hand),
                d0,
                from_split,
                is_two_cards,
                running_count,
                true_count);
            while (decision != 'S') {
                addCard(p_hand, true);
                is_two_cards = false;
                if (decision == 'H') {
                    decision = getPlayingDecision(
                                getHandSum(p_hand),
                                d0, 
                                from_split,
                                is_two_cards,
                                running_count,
                                true_count);
                } else {
                    if (decision != 'D') {
                        throw new IllegalStateException("Invalid Decision " + decision);
                    }
                    player_bets.set(i, bet*2);
                    decision = 'S';
                }
            }

            // Stand
            if (Math.abs(getHandSum(p_hand)) <= 21) {
                let_dealer_play = true;
            // Bust
            } else {
                discardHand(p_hand);
                bankroll -= player_bets.get(i);
                player_bets.set(i, 0.0);
            }
        }
    }

    public void flipDealerHoleCard() {
        char hole_card = dealer_hand.get(1);
        if (hole_card >= '2' && hole_card <= '6') {
            running_count++;
        } else if (hasTenValue(hole_card) || hole_card == 'A') {
            running_count--;
        }
        updateDivisorAndTrueCount();
    }

    public void makeDealerPlay() {
        flipDealerHoleCard();
        if (let_dealer_play) {
            int dealer_sum = getHandSum(dealer_hand);
            while (Math.abs(dealer_sum) <= 16
                   || dealer_sum == -17 && IS_H17
            ) {
                addCard(dealer_hand, true);
                dealer_sum = getHandSum(dealer_hand);
            }
        }
    }

    public void printDebug(String msg) {
        if (DEBUG_MODE) {
            System.out.println(msg);
        }
    }

    public void printTable() {
        if (DEBUG_MODE) {
            System.out.println(dealer_hand);
            System.out.println(player_hands);
            System.out.println(player_bets);
            System.out.println(ins_bets);

            System.out.println("Bankroll: " + bankroll);
            System.out.println("Running: " + running_count);
            System.out.println("True: " + true_count);
            System.out.println("Divisor: " + divisor);
            System.out.println("Discards: " + discards);
            System.out.println("Deck size: " + deck.size());
            System.out.println();
        }
    }
}
