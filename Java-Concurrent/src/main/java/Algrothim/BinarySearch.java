package Algrothim;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

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

    public static void main(String[] args) {

        //ExecutorService poolExecutor = Executors.newFixedThreadPool(4);
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now.getDayOfMonth());
//        System.out.println(now.getMonth().toString());
//        System.out.println(now.getMonth().name());
        Stack<Integer> stack = new Stack<>();
        //进栈
        stack.push(1);
        //出栈
        stack.pop();
        //获取栈顶元素
        stack.peek();

        Deque<Integer> deque = new ArrayDeque<>();
        //进栈
        deque.push(1);
        //出栈
        deque.pop();
        //获取栈顶元素
        deque.peek();

        //队列操作
        deque.addFirst(1);
        deque.addLast(1);
        deque.removeFirst();
        deque.removeLast();
        deque.peekFirst();
        deque.peekLast();

        StringBuffer s = new StringBuffer();

    }
}
