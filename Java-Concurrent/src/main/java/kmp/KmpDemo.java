package kmp;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class KmpDemo {

    private static int[] getNext(String matchStr) {
        int[] next = new int[matchStr.length()];
        int j = 0;
        next[0] = -1;
        next[1] = 0;
        for (int i = 2; i < matchStr.length(); i++) {
            while (j > 0 && matchStr.charAt(j) != matchStr.charAt(i - 1)) {
                j = next[j];
            }
            if (matchStr.charAt(j) == matchStr.charAt(i - 1)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    //aabaabaaf
    public static int[] getNext2(String s) {
//        int j = -1;
//        int[] next = new int[s.length()];
//        next[0] = j;
//        for (int i = 1; i < s.length(); i++){
//            while(j >= 0 && s.charAt(i) != s.charAt(j+1)){
//                j=next[j];
//            }
//
//            if(s.charAt(i) == s.charAt(j+1)){
//                j++;
//            }
//            next[i] = j;
//        }
//        return next;
        int j = 0;
        int[] next = new int[s.length()];
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(j) != s.charAt(i)) {
                j = next[j - 1];
            }
            if (s.charAt(j) == s.charAt(i)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    private static int[] getNext3(String s) {
        int j = 0;
        int[] next = new int[s.length()];
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(j) != s.charAt(i))
                j = next[j - 1];
            if (s.charAt(j) == s.charAt(i))
                j++;
            next[i] = j;
        }
        return next;
    }


    public static int[] getNextArray(String s) {
        char[] str2 = s.toCharArray();
        if (str2.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2; // 目前在哪个位置上求next数组的值
        int cn = 0; // 当前是哪个位置的值再和i-1位置的字符比较
        while (i < next.length) {
            if (str2[i - 1] == str2[cn]) { // 配成功的时候
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
//        System.out.println(Arrays.toString(getNext("aabaabaaf")));
//        System.out.println(Arrays.toString(getNext2("aabaabaaf")));
//        System.out.println(Arrays.toString(getNext3("aabaabaaf")));
//        System.out.println(Arrays.toString(getNextArray("aabaabaaf")));
        System.out.println(delete("AAABCCDDDCB"));
        System.out.println(delete("AABBBABBBA"));
    }

    private static String deleteStr(String s) {
        int len = s.length();
        int count = 1;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            Character c = s.charAt(i);
            if (stack.isEmpty()) {
                stack.push(c);
                continue;
            }
            if (stack.peek() == s.charAt(i)) {
                stack.push(c);
                count++;
                if (count == 2) {
                    if (stack.size() >= 3) {
                        Character var1 = stack.pop();
                        Character var2 = stack.pop();
                        Character var3 = stack.pop();
                        if (var3 == var2) {
                            count = 1;
                            continue;
                        } else {
                            stack.push(var3);
                            stack.push(var2);
                            stack.push(var1);
                        }
                    }
                }
                //计数达到3直接删除，如AAA这种
                else if (count == 3) {
                    stack.pop();
                    stack.pop();
                    stack.pop();
                    count = 1;
                }
                //需要对AABBBA这种情况做处理

            } else {
                count = 1;
                stack.push(c);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            stringBuilder.append(stack.pop());
        }
        return stringBuilder.reverse().toString();
    }

    private static String delete(String s){
        if(s.length() < 3){
            return  s;
        }
        int len = s.length();
        StringBuilder builder = new StringBuilder(s);
        for(int j=1; j<builder.length(); j++){
            if (j-1 >= 0 && j+1 < builder.length() && builder.charAt(j-1) == builder.charAt(j)  && builder.charAt(j+1) == builder.charAt(j-1)){
                int nextIndex = j-2 >= 0 ? j-3 : 0;
                builder.delete(j-1, j+2);
                j=nextIndex;
            }
        }
        return builder.toString();
    }
}
