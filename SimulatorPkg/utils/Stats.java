package SimulatorPkg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

import static SimulatorPkg.Configs.*;
import static SimulatorPkg.utils.HandHelpers.hasTenValue;
import static SimulatorPkg.utils.MathHelpers.*;
import static SimulatorPkg.utils.MoneyHelpers.*;

public class Stats {
    int total_ruins = 0;
    int total_ups = 0;
    int total_rounds = 0;
    double total_profit = 0.0;
    ArrayList<Double> hourly_profit = new ArrayList<>();
    double profit_this_hour = 0.0;

    DateTimeFormatter date_time_formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime start_time;

    // Iteration stats
    boolean has_ruined = false;

    // Shoe stats

    // Round stats
    double round_start_br;

    Table table;

    public Stats() {
        start_time = LocalDateTime.now();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void startIteration() {
        has_ruined = false;
    }

    public void startShoe() {
    }

    public void startRound() {
        round_start_br = table.bankroll;
    }

    public void finishRound() {
        total_rounds++;
        profit_this_hour += (table.bankroll - round_start_br);
        round_start_br = table.bankroll;
        if (total_rounds % ROUNDS_PER_HR == 0) {
            hourly_profit.add(profit_this_hour);
            profit_this_hour = 0.0;
        }
    }

    public void finishShoe() {
        int remaining_count = 0;
        for (Character card : table.deck) {
            if (card >= '2' && card <= '6') {
                remaining_count++;
            } else if (hasTenValue(card) || card == 'A') {
                remaining_count--;
            }
        }
        if (table.running_count + remaining_count != 0) {
            throw new IllegalStateException("Running_count: " + table.running_count + "\nRemaining count: " + remaining_count);
        }
    }

    public void finishIteration(int iter) {
        if (has_ruined) {
            total_ruins++;
        } else {
            if (table.bankroll > STARTING_BANKROLL) {
                total_ups++;
            }
        }
        total_profit += (table.bankroll - STARTING_BANKROLL);
        table = null;

        printTimeEstimation(iter);
    }

    public void printTimeEstimation(int iter) {
        if (iter + 1 == NUM_ITERS) {
            System.out.print("Progress: " + NUM_ITERS + "/" + NUM_ITERS + "\t");
            System.out.print(LocalDateTime.now().format(date_time_formatter));
            System.out.println();
        }
        else if (iter % (NUM_ITERS / NUM_PROGRESS_UDPDATES) == 0) {
            System.out.print("Progress: " + iter + "/" + NUM_ITERS + "\t");
            System.out.print(LocalDateTime.now().format(date_time_formatter));

            if (iter != 0) {
                LocalDateTime now = LocalDateTime.now();
                long seconds_since_start = start_time.until(now, ChronoUnit.SECONDS);
                long seconds_left = (NUM_ITERS - iter)*seconds_since_start/iter;

                LocalDateTime estimated_end = now.plusSeconds(seconds_left);
                long end_mins = seconds_left/60;
                long end_seconds = seconds_left%60;
                String end_str = end_mins + "m " + end_seconds + "s";
                System.out.print("\t Est. finish: in " + end_str + " (" + estimated_end.format(date_time_formatter) + ")");
            }
            System.out.println();
        }
    }

    public void checkForRuin() {
        has_ruined = has_ruined || isRuin(table.player_bets, table.ins_bets, table.bankroll);
    }

    public void printStatistics() {
        double rounds_per_shoe = (double)total_rounds / NUM_ITERS / NUM_SHOES;
        double profit_per_shoe = total_profit / NUM_SHOES / NUM_ITERS;
        double profit_per_hr = profit_per_shoe / rounds_per_shoe * ROUNDS_PER_HR;
        double risk_of_ruin = 100.0 * total_ruins / NUM_ITERS;
        double chance_of_profit = 100.0 * total_ups / NUM_ITERS;
        double standard_deviation = getSD(hourly_profit);
        double hours_til_N0 = getN0(profit_per_hr, standard_deviation);
        double simulated_hours = (double)total_rounds/ROUNDS_PER_HR;

        System.out.println("$"+roundToHundredths(profit_per_hr) + "\t|±$" + roundToHundredths(standard_deviation) + "\t|" + roundToHundredths(risk_of_ruin) + "%\t|" + roundToHundredths(hours_til_N0) + " Hours");
        System.out.println("EV\t|SD\t\t|ROR\t|Hours til N0");

        System.out.println("Chance of profit: " + roundToHundredths(chance_of_profit) + "%");
      

        // System.out.println("Profit per hr: " + roundToHundredths(profit_per_hr));
        // System.out.println("SD per hr: ±" + roundToHundredths(standard_deviation));
        // System.out.println("Risk of ruin: " + roundToHundredths(risk_of_ruin) + "%");
        // System.out.println("Chance of profit: " + roundToHundredths(chance_of_profit) + "%");
        // System.out.println("Hours 'til N0: " + roundToHundredths(hours_til_N0) + " Hours");
        System.out.println();
        System.out.println("Profit per shoe: $" + roundToHundredths(profit_per_shoe));
        System.out.println("Avg rounds/shoe: " + roundToHundredths(rounds_per_shoe));
        System.out.println("Simulated Hours: " + roundToHundredths(simulated_hours));
    }
}
