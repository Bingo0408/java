import java.util.Arrays;

/**
 * This class is used to generate random result
 */
public class RandomUtil {

    /**
     * pickup $pickup_num possArr's index according to their possArr
     * possArr higher element will have higher possibility to be chosen
     * @param possArr : it's does matter whether it is a normalized poss array
     * @param pickupNum : decides how many elements you want to pickup from possArr array
     * @param duplicatePick : set whether duplicated indexes are permitted to be chosen
     * @return randomly picked array's index
     */
    public static int[] randomSelect(double[] possArr, int pickupNum, boolean duplicatePick) {

        // 1. normalize ratio
        double[] poss = normalizeRatio(possArr);

        // 2. [0.1, 0.2, 0.3, 0.4] => [0.1, 0.3, 0.6, 1.0]
        double[] cumulativePoss = new double[possArr.length];
        cumulativePoss[0] = poss[0];
        for(int i=1; i<possArr.length; i++)
            cumulativePoss[i] = cumulativePoss[i-1] + poss[i];

        // 3. initialize index array
        int[] indexes = new int[pickupNum];
        for(int i=0; i<pickupNum; i++)
            indexes[i] = -1;

        // 4. loop for pickupNum times
        for(int i=0; i<pickupNum;) {
            // random > [0, 1)
            double random = Math.random();
            int answer = 0;
            // 4.1 if random is in [0.1, 0.3, random, 0.6, 1.0]
            for(int index=0; index<poss.length; index++){
                if(random < cumulativePoss[index]) {
                    answer = index;
                    break;
                }
            }

            if (duplicatePick) {
                indexes[i] = answer;
                i++; // go to next round
            } else {
                int[] indexesCopy = indexes.clone();
                Arrays.sort(indexesCopy);
                // 4.2 if answer not exists, save it and go to next round
                if(Arrays.binarySearch(indexesCopy, answer) < 0) {
                    indexes[i] = answer;
                    i++; // go to next round
                }
            }
        }
        return indexes;
    }

    /**
     *
     * @param score
     * @return poss distribution array
     */
    public static double[] normalizeRatio(int[] score) {
        // 1. [1,2,3,4] => SUM = 10
        double sum = Arrays.stream(score).sum();
        // 2. [1,2,3,4] => [0.1, 0.2, 0.3, 0.4]
        double[] poss = new double[score.length];
        for(int i=0; i<score.length; i++) {
            poss[i] = score[i] / sum;
        }
        return poss;
    }

    /**
     *
     * @param score
     * @return poss distribution array
     */
    public static double[] normalizeRatio(double[] score) {
        // 1. [1,2,3,4] => SUM = 10
        double sum = Arrays.stream(score).sum();
        // 2. [1,2,3,4] => [0.1, 0.2, 0.3, 0.4]
        double[] poss = new double[score.length];
        for(int i=0; i<score.length; i++) {
            poss[i] = score[i] / sum;
        }
        return poss;
    }
}
