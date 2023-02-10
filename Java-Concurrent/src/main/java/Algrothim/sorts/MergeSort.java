package Algrothim.sorts;

import static Algrothim.CommonUtils.printArray;

public class MergeSort {
    public static void sort(int[] nums) {
        sort(nums, 0, nums.length-1);
    }

    private static void sort(int[] nums, int left, int right) {
        if (right <= left) {
            return;
        }
        int mid = left + (right - left) / 2;
        sort(nums, left, mid);
        sort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        int len = right - left + 1;
        int[] temp = new int[len];
        int index = 0;
        int l = left;
        int r = mid + 1;

        while (l <= mid && r <= right) {
            if (nums[l] > nums[r]) {
                temp[index++] = nums[r++];
            } else {
                temp[index++] = nums[l++];
            }
        }
        while (l <= mid) {
            temp[index++] = nums[l++];
        }
        while (r <= right) {
            temp[index++] = nums[r++];
        }
        for(int i=0;i<len;i++){
            nums[left+i]=temp[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 4, 6, 10, 1,1,2,3,3,5,45,111,321,344,3434,231233,313,54,53,98,34,214,677,777777,888888,2131213,243,56,65654,98765};
        System.out.println("------------before-------------");
        printArray(nums);
        sort(nums);
        System.out.println("------------after--------------");
        printArray(nums);
    }
}
