package Algrothim;

import java.util.HashSet;
import java.util.Set;

public class BinarySearch {

    public int search(int[] nums, int target){
        int left=0,right=nums.length-1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if (nums[mid] == target){
                return mid;
            }else if(nums[mid] > target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        String s = "";
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        return -1;
    }
}
