package Algrothim.sorts;

import java.util.Arrays;

public class InsertSort {

    private static void ascSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            int key = nums[i];
            while (i - 1 >= 0 && nums[i - 1] > key) {
                swap(nums, i - 1, i);
                i--;
            }
        }

    }

    private static void descSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            int key = nums[i];
            while (i - 1 >= 0 && nums[i - 1] < key) {
                swap(nums, i - 1, i);
                i--;
            }
        }

    }

    public static void sort(int[] nums) {
        sort(nums, false);
    }

    public static void sort(int[] nums, boolean desc) {
        if (desc) {
            descSort(nums);
            return;
        }
        ascSort(nums);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 4, 6, 10, 1};
        System.out.println("before sorting: " + Arrays.toString(nums));
        sort(nums);
        System.out.println("after sorting: " + Arrays.toString(nums));
        sort(nums, true);
        System.out.println("desc sort : " + Arrays.toString(nums));

    }
}
