import java.util.Arrays;
import java.util.Random;

public class GeneticAlgoUtil {

    /**
     * pickup $pickup_num fitness's index according to their fitness
     * fitness higher element will have higher possibility to be chosen
     * @param fitness
     * @param pickupNum : decides how many elements you want to pickup from fitness array
     */
    public static int[] select(double[] fitness, int pickupNum) {
        // 1. [1,2,3,4] => SUM = 10
        double sum = Arrays.stream(fitness).sum();

        // 2. [1,2,3,4] => [0.1, 0.2, 0.3, 0.4]
        double[] poss = new double[fitness.length];
        for(int i=0; i<fitness.length; i++) {
            poss[i] = fitness[i] / sum;
        }

        // 3. [0.1, 0.2, 0.3, 0.4] => [0.1, 0.3, 0.6, 1.0]
        double[] cumulativePossibility = new double[fitness.length];
        cumulativePossibility[0] = poss[0];
        for(int i=1; i<fitness.length; i++)
            cumulativePossibility[i] = cumulativePossibility[i-1] + poss[i];

        // 4. initialize index array
        int[] indexes = new int[pickupNum];
        for(int i=0; i<pickupNum; i++)
            indexes[i] = -1;

        // 5. loop for pickupNum times
        for(int i=0; i<pickupNum;) {
            // random > [0, 1)
            double random = Math.random();
            int answer = 0;
            // 5.1 if random is in [0.1, 0.3, random, 0.6, 1.0]
            //     selected index = 2
            for(int index=0; index<poss.length; index++){
                if(random < cumulativePossibility[index]) {
                    answer = index;
                    break;
                }
            }
            int[] indexesCopy = indexes.clone();
            Arrays.sort(indexesCopy);
            // 5.2 if answer not exists, save it and go to next round
            if(Arrays.binarySearch(indexesCopy, answer) < 0) {
                indexes[i] = answer;
                i++; // go to next round
            }
        }
        return indexes;
    }

    /**
     * two parents generates two new children
     * @param parent1: this is a 2d chromosome
     * @param parent2
     */
    public static int[][][] crossover(int[][] parent1, int[][] parent2) {
        int[][] p1 = DeepCloneAndConcatArrayUtil.clone(parent1);
        int[][] p2 = DeepCloneAndConcatArrayUtil.clone(parent2);

        int rows = p1.length;
        int cols = p1[0].length;

        Random random = new Random();
        int i = random.nextInt(rows);
        int j = random.nextInt(cols);
        // switch two matrix from (0, 0) to (i, j)
        for(int row=0; row<=i; row++){
            for(int col=0; col<=j; col++){
                int temp = p2[row][col];
                p2[row][col] = p1[row][col];
                p1[row][col] = temp;
            }
        }

        int[][][] result = new int[2][][];
        result[0] = p1;
        result[1] = p2;

        return result;
    }

    /**
     * for one particular chromsome, and reverse byte digit
     * e.g. 0 -> 1 or 1 -> 0
     * @param chromosome: this is a 2d chromosome
     * @param muationRounds: randomly mutate [0, muationRounds) genes in chromosome
     */
    public static void mutate(int[][] chromosome, int muationRounds) {
        Random random = new Random();
        int rounds = random.nextInt(muationRounds);
        int rows = chromosome.length;
        int cols = chromosome[0].length;

        for(int i=0; i<rounds; i++) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            int before = chromosome[row][col];
            // Do mutation
            if(before==0)
                chromosome[row][col] = 1;
            else
                chromosome[row][col] = 0;
        }

    }
}
