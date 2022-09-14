package UnsafeUtil;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public abstract class UnsafeHelper {
    public static Unsafe unsafe;

    /**
     * 由于Unsafe类为java rt包中的类，是由Bootstrap Classloader加载
     * 而我们自定义的类由App Classloader 加载，Unsafe类中构造方法为私有，只能通过getSafe()方法获取，
     * 此方法会判断是由哪种类加载器加载，如果不是Bootstrap类加载器加载则抛出异常
     * 注：java中采用了双亲委托策略，一是为了防止重复加载，二是安全考虑(防止核心类不会被随意替换)
     *
     **/
    static {
        // unsafe = Unsafe.getUnsafe(); 该方法中，会对加载自己的类加载器进行判断，不是Bootstrap加载器加载的就会抛出异常。
        // 所以我们只能利用反射的方式来获取Unsafe 的实例
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
