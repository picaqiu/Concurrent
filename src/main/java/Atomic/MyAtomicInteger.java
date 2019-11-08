package Atomic;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

import static UnsafeUtil.UnsafeHelper.unsafe;

/**
 * 仿写AtomicInteger(Unsafe类的使用)
 * 其他的Atomic类均类似
 */
public class MyAtomicInteger extends Number implements Serializable {
    private volatile int value;
   // AtomicIntegerArray
    private static volatile long valueOffset;//记录value在内存中的偏移量

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    static {
        try {
            //初始化value在内存中的偏移量
            valueOffset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    public int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }

    public int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }

    public int getAndAddValue(int value) {
        return unsafe.getAndAddInt(this, valueOffset, value);
    }

    public int addValueAndGet(int value) {
        return unsafe.getAndAddInt(this, valueOffset, value) + value;
    }

    public int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        IntUnaryOperator intUnaryOperator = IntUnaryOperator.identity();
        intUnaryOperator.compose(intUnaryOperator);
        System.out.println(intUnaryOperator.applyAsInt(12));
        System.out.println( atomicInteger.getAndUpdate(intUnaryOperator));
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
}
