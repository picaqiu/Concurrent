package ConcurrentUtils;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        // 创建一个值为2的计数器
        CountDownLatch countDownLatch = new CountDownLatch(2);
       // Thread mainThread = Thread.currentThread(); a

        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 counts down");
            //计数器减1
            countDownLatch.countDown();
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 counts down");
           //  mainThread.interrupt(); b
            //计数器减1
            countDownLatch.countDown();
        });

        thread1.start();
        thread2.start();
        try {
            /**
            * 调用await()方法之后，当前线程（即主线程）陷入阻塞
            * 直到：1.计数器的值为0
            *       2.其他线程调用了当前线程的interrupt()方法(即上面a，b部分)
            * */
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //当两个Thread 执行完之后(即计数器为0)，才会执行下面的语句
        System.out.println("Main thread finished");
    }
}
