package Objects;

import UnsafeUtil.UnsafeHelper;


public class ObjectSize {
    private Object o;
    private int i;

    public ObjectSize() {
        this.o = o;
        i = 1;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        ObjectSize o =new ObjectSize();
        System.out.println(UnsafeHelper.unsafe.objectFieldOffset(o.getClass().getDeclaredField("o")));
        System.out.println(UnsafeHelper.unsafe.objectFieldOffset(o.getClass().getDeclaredField("i")));
    }
}
