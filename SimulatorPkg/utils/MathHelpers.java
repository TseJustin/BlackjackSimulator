package SimulatorPkg.utils;

import java.util.*;

public class MathHelpers {
    
    private MathHelpers() {
        // restrict instantiation
    }

    public static double roundToHundredths(double d) {
        int places = 2;
        int mult = (int)Math.pow(10, places);
        return (double)Math.round(d*mult)/mult;
    }

    public static double getSD(ArrayList<Double> arr) {
        double sum = 0.0;
        for (Double d : arr) {
            sum += d;
        }
        double mean = sum / arr.size();
        
        double summation = 0.0;
        for (Double d : arr) {
            double diff = d - mean;
            summation += (diff*diff);
        }
        double standard_deviation = Math.sqrt(summation/arr.size());
        return standard_deviation;
    }

    public static double getN0(double ev, double sd) {
        double N0 = (sd/ev)*(sd/ev);
        return N0;
    }
}
