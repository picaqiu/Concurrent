package ConcurrentUtils;


import java.util.concurrent.Semaphore;

/**
 * 使用信号量
 * 使得三个线程每次都按顺序打印出
 * First、Second、Third
 */
public class SemaphoreExample2 {
    private static Semaphore semaphore1 = new Semaphore(0);
     private static Semaphore semaphore2 = new Semaphore(0);

    public static void main(String[] args) {
        Thread firstThread = new Thread(() -> {
            //first thread print First
            System.out.println("First");
            //semaphore1's permit changed to 1
            semaphore1.release();
        });

        Thread secondThread = new Thread(() -> {
            try {
                //if the semaphore1's permit is not one,current thread will be put blocked
                semaphore1.acquire(1);
                //second thread print Second
                System.out.println("Second");
                //semaphore2's permit changed to 1
                semaphore2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thirdThread = new Thread(() -> {
            try {
                semaphore2.acquire(1);
                //third thread print Third
                System.out.println("Third");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        firstThread.start();
        secondThread.start();
        thirdThread.start();
    }
}
