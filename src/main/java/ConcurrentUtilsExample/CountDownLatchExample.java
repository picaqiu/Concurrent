package ConcurrentUtilsExample;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        CountDownLatch c = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 counts down");
            c.countDown();
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("thread1 counts down");
            c.countDown();
        });

        thread1.start();
        thread2.start();
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //当两个Thread 执行完之后，才会执行下面的语句
        System.out.println("Main thread finished");
    }
}
