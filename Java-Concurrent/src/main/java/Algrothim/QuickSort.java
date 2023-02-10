package Algrothim;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    int add(int a, int b){
        return  a+b;
    }
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        process(array, 0, array.length - 1);
    }

    private static void process(int[] array, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalRegion = partition(array, L, R);
        process(array, L, equalRegion[0] - 1);
        process(array, equalRegion[1] + 1, R);
    }

    private static int[] partition(int[] array, int l, int r) {
        int lessLeft = l - 1;
        int index = l;
        int moreRight = r;
        while (index < moreRight) {
            if (array[r] > array[index]) {
                swap(array, ++lessLeft, index++);
            } else if (array[r] < array[index]) {
                swap(array, --moreRight, index);
            } else {
                index++;
            }
        }
        swap(array, r, moreRight);
        return new int[]{lessLeft + 1, moreRight};
    }


    private static int[] partition6(int[] array, int left, int right){
        int lessLeft = left-1;
        int moreRight = right;
        int index = left;
        while (index < moreRight){
            if (array[right] < array[index]){
                swap(array, index, --moreRight);
            }
            if(array[right] > array[index]){
                swap(array, index++, ++lessLeft);
            }else{
                index++;
            }
        }
        swap(array, right, moreRight);
        return new int[]{lessLeft+1, moreRight};
    }


    private static int[] partition3(int[] array, int left, int right) {
        int lessLeft = left - 1;
        int moreRight = right;
        int index = left;
        while (index < moreRight) {
            if (array[index] < array[right]) {
                swap(array, ++lessLeft, index);
            } else if (array[index] > array[right]) {
                swap(array, index, --moreRight);
            } else {
                index++;
            }
        }
        swap(array, right, moreRight);
        return new int[]{lessLeft + 1, moreRight};
    }

    private static int partition2(int[] array, int left, int right) {
        int privot = array[left];
        int i = left;
        for (int j = left + 1; j <= right; j++) {
            if (array[j] <= privot) {
                swap(array, ++i, j);
            }
        }
        swap(array, left, i);
        return i;
    }

    public static void main(String[] args) {
//        int[] array = new int[]{3, 2, 2, 6, 7, 1, 2, 5};
//        printArray(array);
//        //    sort(array);
//        process(array, 0, array.length - 1);
//        printArray(array);
//
//        List<String> l = new ArrayList<>();
//        l.toArray(new String[]{});
//        Integer[] i =new Integer[26];
//        System.out.println(i[0] == null);
        System.out.println("Hello, I am Optimus Prime.");
        System.out.println("Hello, I am Bumblebee.");
    }


    public void QuickSort(int nums[], int left, int right){
        if (left >= right){
            return;
        }
        int[] equalRegion = quickPartition(nums, left, right);
    }

    private int[] quickPartition(int[] nums, int left, int right) {
        int lessResionIndex = left-1;
        int index = left;
        int moreRightIndex = right;
        while (index < moreRightIndex){
            if (nums[right] > nums[index]){
                swap(nums, ++lessResionIndex, index);
            }else if(nums[right] < nums[index]){
                swap(nums, index, --moreRightIndex);
            }else{
                index++;
            }
        }
        swap(nums, right, moreRightIndex);
        return new int[]{lessResionIndex+1, moreRightIndex};
    }
}
