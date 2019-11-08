package Atomic;

import UnsafeUtil.UnsafeHelper;
import sun.misc.Unsafe;

import java.io.Serializable;

public class MyAtomicIntegerArray implements Serializable {
    private static Unsafe unsafe = UnsafeHelper.unsafe;
    private static final int base = unsafe.arrayBaseOffset(int[].class);//数组第一个元素在内存中的偏移量
    private static final int scale;//表示数组中一个元素所占的空间
    private final int[] array;

    static {
        scale = unsafe.arrayIndexScale(int[].class);
    }

    private static long getOffset(int i) {
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

    public final int length() {
        return array.length;
    }

    public final int get(int i) {
        //unsafe.getInt()
        return unsafe.getIntVolatile(array, getOffset(i));
    }

    public final int getAndAdd(int i, int delta) {
        return unsafe.getAndAddInt(array, getOffset(i), delta);
    }

    public final int addAndGet(int i, int delta) {
        return unsafe.getAndAddInt(array, getOffset(i), delta) + delta;
    }

    public final boolean compareAndSet(int i, int expect, int update) {
        return compareAndSetRaw(getOffset(i), expect, update);
    }

    private boolean compareAndSetRaw(long offset, int expect, int update) {
        return unsafe.compareAndSwapInt(array, offset, expect, update);
    }

    public final int getAndSet(int i, int newValue) {
        return unsafe.getAndSetInt(array, getOffset(i), newValue);
    }

    public static void main(String[] args) {
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger(2);
    }
}
