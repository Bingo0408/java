import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class DeepCloneAndConcatArrayUtil {

    /*
     * 2-d array deep clone
     * For 1-d array, just use .clone()
     */
    public static int[][] deepClone(int[][] oldArray) {
        int d1 = oldArray.length;
        int d2 = oldArray[0].length;
        int[][] newArray = new int[d1][d2];
        for(int i=0; i<d1; i++)
            System.arraycopy(oldArray[i], 0, newArray[i], 0, d2);
        return newArray;
    }

    /*
     * 2-d array deep clone
     * For 1-d array, just use .clone()
     */
    public static double[][] deepClone(double[][] oldArray) {
        int d1 = oldArray.length;
        int d2 = oldArray[0].length;
        double[][] newArray = new double[d1][d2];
        for(int i=0; i<d1; i++)
            System.arraycopy(oldArray[i], 0, newArray[i], 0, d2);
        return newArray;
    }

    /*
     * 3-d array deep clone
     * For 1-d array, just use .clone()
     */
    public static int[][][] deepClone(int[][][] oldArray) {
        int d1 = oldArray.length;
        int d2 = oldArray[0].length;
        int d3 = oldArray[0][0].length;
        int[][][] newArray = new int[d1][d2][d3];
        for(int i=0; i<d1; i++){
            for(int j=0; j<d2; j++)
                System.arraycopy(oldArray[i][j], 0, newArray[i][j], 0, d3);
        }
        return newArray;
    }

    /*
     * 3-d array deep clone
     * For 1-d array, just use .clone()
     */
    public static double[][][] deepClone(double[][][] oldArray) {
        int d1 = oldArray.length;
        int d2 = oldArray[0].length;
        int d3 = oldArray[0][0].length;
        double[][][] newArray = new double[d1][d2][d3];
        for(int i=0; i<d1; i++){
            for(int j=0; j<d2; j++)
                System.arraycopy(oldArray[i][j], 0, newArray[i][j], 0, d3);
        }
        return newArray;
    }

    /*
     * deep concat two 3-d array
     */
    public static int[][][] deepConcat(int[][][] a1, int[][][] a2) {
        assert(a1[0].length == a2[0].length);
        assert(a1[0][0].length == a2[0][0].length);
        int[][][] clone1 = DeepCloneAndConcatArrayUtil.deepClone(a1);
        int[][][] clone2 = DeepCloneAndConcatArrayUtil.deepClone(a2);
        int[][][] result = ArrayUtils.addAll(clone1, clone2);
        return result;
    }




    public static void main(String[] args) {

        // TEST-CASE for int[][] deepCopy
        System.out.println("TEST-CASE for int[][] deepCopy");
        int[][] a1 = new int[][]{{1,2,3}, {4,5,6}};
        int[][] b1 = DeepCloneAndConcatArrayUtil.deepClone(a1);
        a1[0][0] = 999999;
        for(int i=0; i<a1.length; i++) {
            System.out.println(Arrays.toString(a1[i]));
        }
        System.out.println(">>");
        for(int i=0; i<a1.length; i++) {
            System.out.println(Arrays.toString(b1[i]));
        }

        // TEST-CASE for int[][][] deepCopy(int[][][] oldArray)
        System.out.println("TEST-CASE for int[][][] deepCopy");
        int[][][] a2 = new int[][][]{{{1,2,3}, {4,5,6}},{{1,2,3}, {4,5,6}}};
        int[][][] b2 = DeepCloneAndConcatArrayUtil.deepClone(a2);
        a2[0][0][0] = 999999;
        for(int i=0; i<a2.length; i++) {
            for (int j=0; j<a2[0].length; j++)
                System.out.println(Arrays.toString(a2[i][j]));
        }
        System.out.println(">>");
        for(int i=0; i<a2.length; i++) {
            for (int j=0; j<a2[0].length; j++)
                System.out.println(Arrays.toString(b2[i][j]));
        }

        // TEST-CASE for int[][][] deepConcat(int[][][] a1, int[][][] a2)
        System.out.println("TEST-CASE for int[][][] deepConcat(int[][][] a1, int[][][] a2)");
        int[][][] a3 = new int[][][]{{{1,1,1}, {2,2,2}},{{3,3,3}, {4,4,4}}};
        int[][][] b3 = new int[][][]{{{5,5,5}, {6,6,6}},{{7,7,7}, {8,8,8}}};

        int[][][] c3wrong = ArrayUtils.addAll(a3, b3);
        int[][][] c3right = DeepCloneAndConcatArrayUtil.deepConcat(a3, b3);
        a3[0][0][0] = 1111111;
        b3[0][0][0] = 2222222;
        System.out.println("WrongCase Below");
        for(int i=0; i<c3wrong.length; i++) {
            for (int j=0; j<c3wrong[0].length; j++)
                System.out.println(Arrays.toString(c3wrong[i][j]));
        }
        System.out.println("RightCase Below");
        for(int i=0; i<c3wrong.length; i++) {
            for (int j=0; j<c3wrong[0].length; j++)
                System.out.println(Arrays.toString(c3right[i][j]));
        }

    }
}
