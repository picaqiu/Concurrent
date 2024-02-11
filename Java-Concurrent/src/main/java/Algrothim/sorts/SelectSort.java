package Algrothim.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Algrothim.CommonUtils.printArray;
import static Algrothim.CommonUtils.swap;

public class SelectSort {
    public static void sort(int[] nums){
        int len = nums.length;
        if (len < 2){
            return;
        }
        int index = 0;
        while(index < len-1){
            int minIndex = index;
            for(int i=index+1;i<len;i++){
                if (nums[i] < nums[minIndex]){
                    minIndex = i;
                }
            }
            swap(nums, minIndex, index++);
        }
    }

    public static void main(String[] args) {
//        int[] nums = new int[]{5, 4, 6, 10, 1,1,2,3,3,5,45,111,321,344,3434,231233,313,54,53,98,34,214,677,777777,888888,2131213,243,56,65654,98765};
//        System.out.println("------------before-------------");
//        printArray(nums);
//        sort(nums);
//        System.out.println("------------after--------------");
//        printArray(nums);

        List<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("N/A");
        list.add("3");
        list.add("2");
        get(list);
        System.out.println(Arrays.toString(list.toArray()));
    }

    private static void get(List<String> list ){
        if (list.isEmpty()){
            return;
        }
        for(int i=0;i< list.size();i++){
            if (list.get(i).equals("N/A")){
                list.remove(i);
                System.out.println("after remove is : " + list.get(i));
            }
        }
    }


}
