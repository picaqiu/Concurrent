package Algrothim;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

import static Algrothim.TestWeightedRandom.testWeightedRandom;

public class Meogo {
    public static void main(String[] args) {
        testWeightedRandom();
    }
}

class WeightedRandom {
    private int[] regions;
    private int total=0;

    public WeightedRandom(int[] input) {
        // show me your code
        int length = input.length;
        if (length == 0) {
            throw new RuntimeException("the input array can not be empty");
        }
        regions = new int[length+1];
        regions[0] = input[0];
        for (int i = 1; i < length; i++) {
            regions[i] = regions[i-1] + input[i];
        }
        total = Arrays.stream(input).sum();
    }

    public int next() {
        // show me your code
        int x = (int) (Math.random() * total) + 1;
        return binarySearch(x);
    }

    private int binarySearch(int x) {
        int low = 0, high = regions.length - 1;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (regions[mid] < x) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}

/* ----------------- 以下是测试用例 -----------------*/

class TestWeightedRandom {

    public static void testWeightedRandom() {
        int[] input = {4, 2, 1, 3, 3};
        WeightedRandom random = new WeightedRandom(input);
        int[] count = new int[input.length];
        for (int i = 0; i < 10000; i++) {
            int v = random.next();
           // System.out.println("v is :" + v);
            if (v < 0 || v >= input.length) {
                throw new RuntimeException("invalid random value: " + v);
            }
            count[v]++;
        }
        int sum = Arrays.stream(input).sum();
        for (int i = 0; i < input.length; i++) {
            double expectedWeight = (double) input[i] / (double) sum;
            double realWeight = (double) count[i] / 10000D;
            if (Math.abs(expectedWeight - realWeight) > 0.01) {
                System.out.println("the expectedWeight is : "+ expectedWeight + " the realWeight is : "+realWeight);
                throw new RuntimeException(
                        "invalid weight " + realWeight + " for index " + i + ", expected is " + expectedWeight
                );
            }
        }
    }



}