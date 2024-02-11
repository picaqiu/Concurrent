package thread;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintByOrder {
    private static AtomicInteger index = new AtomicInteger(0);
    private static final Object lock = new Object();

    static class ThreadA extends Thread {
        @Override
        public void run() {
            while (index.get() < 99) {
                synchronized (lock) {
                    if (index.get() % 3 == 0) {
                        System.out.println("A");
                        index.incrementAndGet();
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            while (index.get() < 99) {
                synchronized (lock) {
                    if (index.get() % 3 == 1) {
                        System.out.println("B");
                        index.incrementAndGet();
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            while (index.get() < 99) {
                synchronized (lock) {
                    if (index.get() % 3 == 2) {
                        System.out.println("C");
                        index.incrementAndGet();
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        ThreadC c = new ThreadC();
        a.start();
        b.start();
        c.start();
        a.join();
        b.join();
        c.join();
        System.out.println("主线程等待结束");
    }
}
