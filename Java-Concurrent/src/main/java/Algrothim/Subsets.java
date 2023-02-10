package Algrothim;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class Subsets {
    static  List<List<Integer>> result = new ArrayList<>();

    public static List<List<Integer>> subsets(int[] nums) {
        LinkedList<Integer> data = new LinkedList();
        Arrays.sort(nums);
        backtrack(nums, data, 0);
        return result;
    }

    public static void backtrack(int[] nums, LinkedList<Integer> data, int start){
        System.out.println(JSON.toJSONString(data));
        result.add(new LinkedList(data));

        for(int i=start;i<nums.length;i++){
            if(i>start && nums[i] == nums[i-1]){
                continue;
            }
            data.add(nums[i]);
            backtrack(nums, data, i+1);/**/
            data.removeLast();
        }
    }


}
