package Algrothim;

import java.util.*;

//将一个数组的所有元素向右移动若干单位，并把数组右侧溢出的元素填补
//        在数组左侧的空缺中，这种经操作称为数组的循环平移。
//
//        给你一个不小于 3 个元素的数组 a，已知 a 是从一个有序且不包含
//        重复元素的数组平移 k(k 大于等于 0 且小于数组长度)个单位而来；
//        请写一个函数，输入 int 类型数组 a，返回 k 的值。
//
//        例如，对于数组 a = {5, 1, 2, 3, 4}，它由有序数组
//        {1, 2, 3, 4, 5} 循环平移 1 个单位而来，因此 k = 1。
public class Solution {

    public static int move(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        int result = 0;
        while (left < right) {
            if (nums[left] > nums[right]) {
                left++;
                result++;
            } else {
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(move(new int[]{4, 5, 1, 2, 3}));
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> ueue2 = new PriorityQueue<>((a, b)-> b - a);

    }


    //取得最高位
    private int getFirstNum(int nums) {
        if (nums < 10) {
            return nums;
        }
        return getFirstNum(nums / 10);
    }
}
