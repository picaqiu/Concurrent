package Algrothim;

import java.util.LinkedHashMap;

public class Sqrt {
    private static double error = 1e-5;


    public static void main(String[] args) {
        System.out.println(sqrt(2d, 0));
    }

    public static double sqrt(double n, int m) {
        if (n < 0) {
            return -1;
        }
        double root = n;
        while (Math.abs(n - root * root) > error) {
            root = (n / root + root) / 2;
        }

        return root;
    }

    public static double sqrt(int m, double n){
        return  1.0d;
    }

    static <T, Alibaba, String> String get(String string, Alibaba alibaba){
        return  string;
    }
}
