package ThreadPool;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsyncThreadPool {
    private final static int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(AVAILABLE_PROCESSOR,
            2 * AVAILABLE_PROCESSOR + 1, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());
    private static Lock lock = new ReentrantLock();
    private static Condition A = lock.newCondition();
    private static Condition B = lock.newCondition();

    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger(0);
        threadPoolExecutor.execute(() -> {
            try {
                for (int j = 0; j < 100; j++) {
                    lock.lock();
                    int value = counter.get();
                    if (value % 2 == 0) {
                        System.out.println("the A print: " + value);
                        counter.incrementAndGet();
                        B.signalAll();
                    } else {
                        A.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                for (int j = 0; j < 100; j++) {
                    lock.lock();
                    int value = counter.get();
                    if (value % 2 == 1) {
                        System.out.println("the B print: " + value);
                        counter.incrementAndGet();
                        A.signalAll();
                    } else {
                        B.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        try {
            Future<?> future = threadPoolExecutor.submit(() -> send(1));
            System.out.println("first message is: " + future.get());
            Future<?> future2 = threadPoolExecutor.submit(() -> send(2));
            System.out.println("second message is: " + future2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String send(int code) {
        if (code == 1) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }
}
