package Atomic;

import UnsafeUtil.UnsafeHelper;
import sun.misc.Unsafe;

import java.io.Serializable;

public class MyAtomicIntegerArray extends Number implements Serializable {
    private static Unsafe unsafe = UnsafeHelper.unsafe;
    private static final int base = unsafe.arrayBaseOffset(int[].class);//数组第一个元素在内存中的偏移量
    private static final int scale;//表示数组中一个元素所占的空间
    private final int[] array;

    static {
        scale = unsafe.arrayIndexScale(int[].class);
    }

    private static long byteOffset(int i) {
        //原生AtomicInteger中采用了((long) i << shift) + base;
        //其中shift为scale的2次幂
        return ((long) i * scale) + base;
    }

    public MyAtomicIntegerArray(int[] array) {
        this.array = array;
    }

    public MyAtomicIntegerArray(int length) {
        array = new int[length];
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }




    public static void main(String[] args) {
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger(2);
     //   int scale = unsafe.arrayIndexScale(int[].class);
       // int shift = 31 - Integer.numberOfLeadingZeros(scale);

        //System.out.println(scale);
        //System.out.println(shift);
    }
}
