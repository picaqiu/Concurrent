package ConcurrentUtilsExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(()->{
            System.out.println(Thread.currentThread().getName() + " over");
            semaphore.release();
        });

        executorService.submit(()->{
            System.out.println(Thread.currentThread().getName() + " over");
            semaphore.release();
        });

        //等待子线程执行完毕，返回
        semaphore.acquire(2);
        System.out.println("all child threads over");
        executorService.shutdown();
    }
}
