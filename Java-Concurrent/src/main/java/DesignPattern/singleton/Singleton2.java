package DesignPattern.singleton;

public class Singleton2 {

    private Singleton2() {
    }

    public static Singleton2 getInstance(){
        return SingletonHolder.Instance;
    }

    private static class SingletonHolder{
        private static final Singleton2 Instance = new Singleton2();
    }
}
