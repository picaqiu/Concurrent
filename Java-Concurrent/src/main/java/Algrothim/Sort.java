package Algrothim;

import io.netty.buffer.ByteBufInputStream;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Sort {

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


    /**
     * 选择排序
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                minIndex = array[j] < array[minIndex] ? j : minIndex;
            }
            swap(array, i, minIndex);
        }
    }


    public static void bubbleSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int length = array.length;
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (array[j] < array[j - 1]) {
                    swap(array, j - 1, j);
                }
            }
        }
    }

    public static void swapSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int length = array.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (array[i] > array[j]) {
                    swap(array, i, j);
                }
            }
        }
    }

    public static void insertSelect(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int length = array.length;
        for (int i = 1; i < length; i++) {
            int index = i;
            while (index - 1 >= 0 && array[index - 1] > array[index]) {
                swap(array, index - 1, index);
                index--;
            }
        }
    }

    public static void mergeSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        process(array, 0, array.length - 1);
    }

    private static void process(int[] array, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + (right - left) / 2;
        process(array, left, mid);
        process(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int length = right - left + 1;
        int[] temp = new int[length+1];
        int l = left;
        int r = mid + 1;
        int index = 0;
        while (l <= mid && r <= right) {
            if (array[l] < array[r]) {
                temp[index++] = array[l++];
            } else {
                temp[index++] = array[r++];
            }
        }
        while (l <= mid) {
            temp[index++] = array[l++];
        }
        while (r <= right) {
            temp[index++] = array[r++];
        }

        for (int j = 0; j < length; j++) {
            array[left + j] = temp[j];
        }
    }

    public static void main(String[] args) {
        int[] array = {3, 3, 2, 5, 1, 2, 3, 8, 9};
        printArray(array);
       // mergeSort(array);
        quicksort(array, 0, array.length-1);
        printArray(array);
    }

    public static void quicksort(int[] nums, int l, int r){
        if (l >= r){
            return;
        }
        int[] equalPart = partition(nums, l, r);
        quicksort(nums, l, equalPart[0]-1);
        quicksort(nums, equalPart[1]+1, r);
    }

    private static int[] partition(int[] nums, int l, int r) {
        //小于区
        int lessLeft = l-1;
        int index = l;
        int moreRight = r;
        while (index < moreRight){
            if (nums[r] > nums[index]){
                swap(nums, ++lessLeft, index++);
            }else if (nums[r] < nums[index]){
                swap(nums, index, --moreRight);
            }else {
                index++;
            }
        }
        swap(nums, r, index);

        return  new int[]{lessLeft+1, index};
    }

}
