package ConcurrentUtils;

import java.util.concurrent.Semaphore;

/**
 * print the 'foo' 'bar' alternately
 */
public class SemaphoreExample3 {
    private static int times;
    private static Semaphore semaphore1 = new Semaphore(0);
    private static Semaphore semaphore2 = new Semaphore(0);

    public static void setTimes(int times) {
        SemaphoreExample3.times = times;
    }

    public static void init() {
        semaphore1.release();
    }

    public static void main(String[] args) {
        //input the time 10
        setTimes(10);
        // init the semaphore 1
        init();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                try {
                    semaphore1.acquire(1);
                    System.out.println("foo" + (i + 1));
                    semaphore2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                try {
                    semaphore2.acquire(1);
                    System.out.println("bar" + (i + 1));
                    semaphore1.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
