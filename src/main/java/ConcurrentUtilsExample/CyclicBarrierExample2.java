package ConcurrentUtilsExample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample2 {
    //创建一个CyclicBarrier实例，添加一个所有子线程全部到达后执行的任务
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        System.out.println(Thread.currentThread() + "reached the barrier");
    });

    public static void main(String[] args) {
        //创建一个线程数为2的固定线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //将线程A添加到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " taskA");
                System.out.println(Thread.currentThread() + " enters in barrier");
                //当前线程阻塞，直到有两个线程达到屏障
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        //将线程B添加到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + " taskB");
                System.out.println(Thread.currentThread() + " enters in barrier");
                //当前线程阻塞，直到有两个线程达到屏障
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + " out barrier");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
