package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CreateThreadApp {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();

        MyRunnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(myRunnable);
        thread2.start();

        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        System.out.println(futureTask.get());

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(()->{
            System.out.println("由线程池创建线程");
        });
        executorService.shutdown();
    }
}
