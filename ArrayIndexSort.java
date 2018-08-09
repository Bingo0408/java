import java.util.Comparator;

public class ArrayIndexSort implements Comparator<Integer>{

    private final Double[] array;

    public ArrayIndexSort(Double[] array) {
        this.array = array;
    }

    public Integer[] createIndexArray() {
        Integer[] indexes = new Integer[array.length];
        for(int i=0; i<array.length; i++)
            indexes[i] = i;
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2) {
        return array[index1].compareTo(array[index2]);
    }
}

/*
        Integer[] x = {2,3,4,6,11,2,3,9};
        ArrayIndexSort comparator = new ArrayIndexSort(x);
        Integer[] indexes = comparator.createIndexArray();
        Arrays.sort(indexes, comparator);
        Arrays.stream(indexes).forEach(num -> System.out.println(num));

 */
