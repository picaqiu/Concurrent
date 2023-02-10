package ConcurrentUtils;


import java.util.Stack;
import java.util.concurrent.Semaphore;

/**
 * 三个线程打印1-100
 */
public class SemaphoreExample4 {
    private static int times;
    private static Semaphore semaphore1 = new Semaphore(0);
    private static Semaphore semaphore2 = new Semaphore(0);

    public static void init() {
        semaphore1.release();
    }

    public static void main(String[] args) {
        init();
        Thread thread1 =new Thread(()->{
            for(int i=1;i<=100;i++){
                    try {
                        semaphore1.acquire(1);
                        if (i % 2 == 1) {
                            System.out.println(i);
                        }
                        semaphore2.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        Thread thread2 =new Thread(()->{
            for(int i=1;i<=100;i++){
                try {
                    semaphore2.acquire(1);
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
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
