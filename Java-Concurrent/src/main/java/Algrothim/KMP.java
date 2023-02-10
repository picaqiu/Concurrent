package Algrothim;

public class KMP {


    private static int[] getNext(String s) {
        int len = s.length();
        if (len == 1) {
            return new int[]{0};
        }
        int[] next = new int[len];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < len; i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }


    public static boolean match(String pattern, String target) {
        if (pattern == null || target == null || target.length() > pattern.length()) {
            return false;
        }
        int[] next = getNext(target);
        int j=0;
        for (int i=0; i<pattern.length(); i++){
            while (j>0 && pattern.charAt(i) != target.charAt(j)){
                j = next[j-1];
            }
            if (pattern.charAt(i) == target.charAt(j)){
                j++;
            }
            if (j == target.length()){
                return true;
            }
        }
        return false;
    }

    public static int getIndex(String pattern, String target) {
        if (pattern == null || target == null || target.length() > pattern.length()) {
            return -1;
        }
        int[] next = getNext(target);
        int j=0;
        for (int i=0; i<pattern.length(); i++){
            while (j>0 && pattern.charAt(i) != target.charAt(j)){
                j = next[j-1];
            }
            if (pattern.charAt(i) == target.charAt(j)){
                j++;
            }
            if (j == target.length()){
                return i-j+1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String pattern = "abcabcabd";
        String target = "abcabd";
        System.out.println(getIndex(pattern, target));
        System.out.println(pattern.substring(getIndex(pattern, target)));
    }
}
