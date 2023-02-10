package Algrothim;

import java.util.*;

//消灭3个连续相同的字母
public class FilterRepeat {
    public static String solution(String s) {
        if (s == null || "".equals(s) || s.length() < 3) {
            return s;
        }
        int index = 0;
        char[] data = s.toCharArray();
        //计数器
        int count = 1;
        for (int i = 1; i < data.length; i++) {
            if (data[index] == ' ') {
                index++;
                continue;
            }
            if (data[i] == ' ') {
                continue;
            }
            //以index所在字符为参照，相等计数器加1，i继续向后移动
            if (data[i] == data[index]) {
                count++;
                //当前字符与参照字符不等且计数器大于等于3
                if (count == 3) {
                    for (int j = index; j <= i; j++) {
                        data[j] = ' ';
                    }
                    index = 0;
                    i = 0;
                    count = 1;
                }
                continue;
            } else {
                index = i;
                count = 1;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (data[i] != ' ') {
                stringBuilder.append(data[i]);
            }
        }
        return stringBuilder.toString();
    }


    private static boolean isCanFilter(String s) {
        int index = 0, count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(index) == s.charAt(i)) {
                count++;
                if (count == 3) {
                    return true;
                }
                continue;
            }else {
                index = i;
                count = 1;
            }
        }
        return  false;
    }

    public static void main(String[] args) {
        //  System.out.println(solution("abbbccddddde"));
        System.out.println(solution("abbbccdddcdde"));
        LinkedList<List<Integer>> list  = new LinkedList<>();
        list.get(0).toArray(new Integer[2]);
    }

}
