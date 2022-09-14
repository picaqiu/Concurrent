package Atomic;

import UnsafeUtil.UnsafeHelper;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

import static UnsafeUtil.UnsafeHelper.unsafe;

/**
 * 仿写AtomicInteger(Unsafe类的使用)
 * 其他的Atomic类均类似
 */
public class MyAtomicInteger extends Number implements Serializable {
    // AtomicIntegerArray
    private static volatile long valueOffset;//记录value在内存中的偏移量

    static {
        try {
            //初始化value在内存中的偏移量
            valueOffset = UnsafeHelper.unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private volatile int value;

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        IntUnaryOperator intUnaryOperator = IntUnaryOperator.identity();
        intUnaryOperator.compose(intUnaryOperator);
        System.out.println(intUnaryOperator.applyAsInt(12));
        System.out.println(atomicInteger.getAndUpdate(intUnaryOperator));
        System.out.println(atomicInteger.get());

        MyAtomicInteger integer = new MyAtomicInteger(0);

        System.out.println("initial integer is : " + integer.get());
        System.out.println("compareAndSet : " + integer.compareAndSet(0, 1));
        System.out.println("After compareAndSet : " + integer.get());
        System.out.println("getAndIncrement : " + integer.getAndIncrement());
        System.out.println("After getAndIncrement : " + integer.get());
        System.out.println("incrementAndGet : " + integer.incrementAndGet());
        System.out.println("After incrementAndGet : " + integer.get());
        System.out.println("getAndSet : " + integer.getAndSet(10));
        System.out.println("After getAndSet : " + integer.get());
        System.out.println("getAndAddValue : " + integer.getAndAddValue(10));
        System.out.println("After getAndAddValue : " + integer.get());
        System.out.println("addValueAndGet : " + integer.addValueAndGet(10));
        System.out.println("After addValueAndGet : " + integer.get());
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    public int get() {
        return value;
    }

    public void set(int newValue) {
        value = newValue;
    }

    public boolean compareAndSet(int expect, int update) {
        return UnsafeHelper.unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    public int getAndIncrement() {
        return UnsafeHelper.unsafe.getAndAddInt(this, valueOffset, 1);
    }

    public int incrementAndGet() {
        return UnsafeHelper.unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }

    public int getAndAddValue(int value) {
        return UnsafeHelper.unsafe.getAndAddInt(this, valueOffset, value);
    }

    public int addValueAndGet(int value) {
        return UnsafeHelper.unsafe.getAndAddInt(this, valueOffset, value) + value;
    }

    public int getAndSet(int newValue) {
        return UnsafeHelper.unsafe.getAndSetInt(this, valueOffset, newValue);
    }
}
