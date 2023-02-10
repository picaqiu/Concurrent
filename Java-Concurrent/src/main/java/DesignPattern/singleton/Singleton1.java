package DesignPattern.singleton;

public class Singleton1 {
    private volatile static Singleton1 singleton1;

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (singleton1 == null) {
            synchronized (Singleton1.class) {
                if (singleton1 == null) {
                    return new Singleton1();
                }
            }
        }
        return singleton1;
    }
}
