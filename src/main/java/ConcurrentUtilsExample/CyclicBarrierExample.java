package ConcurrentUtilsExample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("thread1 starts to await");
                cyclicBarrier.await();
                //当到达屏障数为2时，以下语句才会执行
                System.out.println("thread1 finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("thread2 starts to await");
                cyclicBarrier.await();
                //当到达屏障数为2时，以下语句才会执行
                System.out.println("thread2 finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        System.out.println("main thread finish");
    }
}
