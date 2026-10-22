package SimulatorPkg;
// javac main.java to compile
// java main.java to run

import static SimulatorPkg.Configs.*;
import SimulatorPkg.utils.Table;
import SimulatorPkg.utils.Stats;

class Main {
    public static void main(String[] args) {
        Stats stats = new Stats();
        for (int iter = 0; iter < NUM_ITERS; iter++) {
            stats.startIteration();
            Table table = new Table();
            stats.setTable(table);
            for (int shoe = 0; shoe < NUM_SHOES; shoe++) {
                stats.startShoe();
                table.resetTable();
                while (table.deck.size() > 52*PEN) {
                    stats.startRound();
                    table.startRound();

                    table.setPlayerBets();
                    table.giveDealerCards();
                    table.givePlayerCards();
                    table.checkInsurance();
                    
                    boolean dealer_has_bj = table.checkDealerBJ();
                    if (!dealer_has_bj) {
                        table.payoutPlayerBJ();
                    } else {
                        table.flipDealerHoleCard();
                        table.finishRound(true);
                        stats.finishRound();
                        if (table.wongOut()) {
                            break;
                        }
                        continue;
                    }

                    table.playHands();
                    stats.checkForRuin();
                    table.makeDealerPlay();

                    table.finishRound(false);
                    stats.finishRound();
                    if (table.wongOut()) {
                        break;
                    }
                }
                stats.finishShoe();         
            }
            stats.finishIteration(iter);
        }
        stats.printStatistics();
    }
}