import org.junit.Test;

import java.util.Arrays;

public class RandomUtilTest {

    @Test
    public void testNormalizeRatio() {
        int[] score = new int[]{1,1,1,2};
        double[] poss = RandomUtil.normalizeRatio(score);
        assert(Arrays.toString(poss).equals("[0.2, 0.2, 0.2, 0.4]"));
    }

    @Test
    public void testRandomSelect() {
        int[] score = new int[]{1,1,1,20};
        int[] randomPick = RandomUtil.randomSelect(score, 3, true);
        System.out.println(Arrays.toString(randomPick));
        randomPick = RandomUtil.randomSelect(score, 3, false);
        System.out.println(Arrays.toString(randomPick));
    }

}
