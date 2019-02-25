import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * @Author: Bin GU
 * @Date: 2019/2/25 14:32
 * @Version Initial Version
 */
public class RebalanceCalculator {

    /**
     * This func can auto balance by calc raw ratio and adjust ratio
     * @param raw:    [10,   20,   30,    40  ]
     * @param adjust: [0,    +10,  0,     0   ]
     * @return result:[8.75, 30.0, 26.25, 35.0]
     * i:  total ratio will not be changed
     * ii: illegal input will be corrected, e.g. if you adjust some element by -300%
     */
    private static double[] rebalancedByRatioAdjust(double[] raw, double[] adjust) {

        assert(raw.length == adjust.length);

        double rawTotal = Arrays.stream(raw).sum();

        double[] result = new double[raw.length];
        boolean[] toRebalanceBit = new boolean[raw.length];
        double totalWeight = 0l;
        double totalAdjust = 0l;

        // 1. Record total adjustment
        // 2. Mark object need to be re-balanced
        // 3. Calc total weight that need to be re-balanced
        for(int i=0; i<raw.length; i++) {
            if (adjust[i] != 0) {
                totalAdjust += adjust[i];
            } else {
                toRebalanceBit[i] = true;
                totalWeight += raw[i];
            }
        }

        // 1. IF it needs re-balance, re-balance it by its weight
        // 2. IF it not needs re-balance, directly add adjust to raw
        for(int i=0; i<raw.length; i++) {
            if (toRebalanceBit[i]) {
                result[i] = raw[i] - raw[i] * 1.0 / totalWeight * totalAdjust;
            } else {
                result[i] = raw[i] + adjust[i];
            }
        }

        // Negative value will be corrected
        toRebalanceBit = new boolean[raw.length];
        totalWeight = 0l;
        totalAdjust = 0l;
        for(int i=0; i<raw.length; i++) {
            if (result[i] < 0) {
                totalAdjust += result[i];
            } else {
                toRebalanceBit[i] = true;
                totalWeight += raw[i];
            }
        }
        for(int i=0; i<raw.length; i++) {
            if (toRebalanceBit[i]) {
                result[i] = result[i] + (raw[i] / totalWeight * totalAdjust);
            } else {
                result[i] = 0;
            }
        }

        // Format data pattern
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        for(int i=0; i<raw.length; i++) {
            result[i] = Double.valueOf(df.format(result[i]));
        }

        // To ensure total ratio is not changed
        double newTotal = Arrays.stream(result).sum();
        double balance = newTotal - rawTotal;
        if (balance != 0) {
            Random random = new Random();
            while (true) {
                int i = random.nextInt(raw.length);
                if(result[i] > Math.abs(balance)) {
                    result[i] = result[i] - balance;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * This func can auto balance by calc raw value and fixed value
     * @param raw    : [10, 20, 30, 40]
     * @param fixed  : [0,  0,  50, 0 ] -- 0 means it has no fixed setting
     * @return result: [7,  14, 50, 29]
     * i:  total quantity will not be changed
     * ii: illegal input will be corrected, e.g. if you adjust some quantity by -30000000
     */
    private static long[] rebalanceByFixedValue(long[] raw, long[] fixed) {

        assert(raw.length == fixed.length);

        long rawTotal = Arrays.stream(raw).sum();

        long[] result = new long[raw.length];
        boolean[] toRebalanceBit = new boolean[raw.length];
        double totalWeight = 0l;
        double totalAdjust = 0l;

        // 1. Record total adjustment
        // 2. Mark object need to be re-balanced
        // 3. Calc total weight that need to be re-balanced
        for(int i=0; i<raw.length; i++) {
            if (fixed[i] != 0) {
                totalAdjust += (fixed[i] - raw[i]);
            } else {
                toRebalanceBit[i] = true;
                totalWeight += raw[i];
            }
        }

        // 1. IF it needs re-balance, re-balance it by its weight
        // 2. IF it not needs re-balance, directly add adjust to raw
        for(int i=0; i<raw.length; i++) {
            if (toRebalanceBit[i]) {
                result[i] = raw[i] - Math.round(raw[i] / totalWeight * totalAdjust);
            } else {
                result[i] = fixed[i];
            }
        }

        // Negative value will be corrected
        toRebalanceBit = new boolean[raw.length];
        totalWeight = 0l;
        totalAdjust = 0l;
        for(int i=0; i<raw.length; i++) {
            if (result[i] < 0) {
                totalAdjust += result[i];
            } else {
                toRebalanceBit[i] = true;
                totalWeight += raw[i];
            }
        }
        for(int i=0; i<raw.length; i++) {
            if (toRebalanceBit[i]) {
                result[i] = result[i] + Math.round(raw[i] / totalWeight * totalAdjust);
            } else {
                result[i] = 0;
            }
        }

        // To ensure total quantity is not changed
        long newTotal = Arrays.stream(result).sum();
        long balance = newTotal - rawTotal;
        if (balance != 0) {
            Random random = new Random();
            while (true) {
                int i = random.nextInt(raw.length);
                if(result[i] > Math.abs(balance)) {
                    result[i] = result[i] - balance;
                    break;
                }
            }
        }

        return result;
    }


    public static void main(String[] args) {

        double[] raw = {7, 20, 30 ,40};
        double[] adjust = {0, -111, 0, 0};
        double[] result = rebalancedByRatioAdjust(raw, adjust);
        System.out.println(Arrays.stream(raw).sum());
        System.out.println(Arrays.stream(result).sum());
        System.out.println(Arrays.toString(result));


        long[] rawQ = {10, 20, 30 ,40};
        long[] fxiedQ = {0, 0, 11111, 0};
        long[] resultQ = rebalanceByFixedValue(rawQ, fxiedQ);
        System.out.println(Arrays.stream(rawQ).sum());
        System.out.println(Arrays.stream(resultQ).sum());
        System.out.println(Arrays.toString(resultQ));
    }
}
